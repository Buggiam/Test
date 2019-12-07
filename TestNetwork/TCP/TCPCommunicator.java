package TCP;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import TCP.messaging.*;

public abstract class TCPCommunicator {

    protected InetAddress address;
    protected int port;

    private DatagramSocket socket;
    private byte[] buffer;

    private HashMap<Integer, TCPMessage> waitingMessages;
    private HashSet<Integer> approvedKeys;

    private HashSet<Integer> pinged;

    private boolean print = true;
    private String lastPrint = "";

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getFullAddress() {
        return String.format("%s:%d", address.getHostAddress(), port);
    }

    public void setPrintDetails(boolean print) {
        this.print = print;
    }

    protected TCPCommunicator(int port) {
        try {
            waitingMessages = new HashMap<>();
            approvedKeys = new HashSet<>();
            pinged = new HashSet<>();

            address = InetAddress.getLocalHost();
            this.port = port;
            socket = new DatagramSocket(port);

            System.out.printf("TCPCommunicator initiated at %s.%n", getFullAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tells the communicator to listen for socket input for a given amount of time.
     * If 0 is given, it listens forever, calling onListenTimeout() once every
     * second. If the method was called with a timelimit, it is assumed that either
     * a handshake-confirmation or ping-response is expected. If one of those is
     * received, the listening is cut short and assumed to have fulfilled its
     * purpose.
     * 
     * @param listenForMs time to listen.
     */
    protected void listen(int listenForMs) {
        while (true) {
            if (listenForMs != 0)
                setTimeout(listenForMs);
            else
                setTimeout(1000);

            print(String.format("Listening at socket %s...", getFullAddress()));
            buffer = new byte[TCPMessage.BYTE_SIZE];

            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(receivePacket);
            } catch (IOException e) {
                if (listenForMs == 0) {
                    onListenTimeout();
                    continue;
                } else {
                    break;
                }
            }

            boolean finishedHandshake = processMessage(receivePacket.getData());
            if (listenForMs != 0 && finishedHandshake)
                break;
        }
    }

    /**
     * Tells the communicator to listen for socket input forever, calling
     * onListenTimeout() once every second.
     */
    protected void listen() {
        listen(0);
    }

    /**
     * Pings another TCPCommunicator, expecting a response within 100ms.
     * 
     * @param toAddress InetAddress of the targeted socket.
     * @param toPort    Port of the targeted socket.
     * @return true if a response was received. Otherwise false.
     */
    protected boolean ping(InetAddress toAddress, int toPort) {
        PingMessage message = new PingMessage();

        message.setSender(address, port);
        message.setReceiver(toAddress, toPort);

        pinged.add(message.getHandshakeKey());

        if (toAddress.equals(address) && toPort == port) {
            // Self ping
            pinged.remove(message.getHandshakeKey());
        } else {
            boolean sent = sendMessage(message, toAddress, toPort);

            if (sent) {
                listen(100);
            }
        }

        boolean success = !pinged.contains(message.getHandshakeKey());
        pinged.clear();

        return success;
    }

    /**
     * Sends a TCP message to a target socket. The process starts by blindly sending
     * the message to the target. If a response is received from the target, the
     * message is sent again and it is assumed that it was well-received.
     * 
     * @param message   to be sent to the target.
     * @param toAddress InetAddress of the targeted socket.
     * @param toPort    Port of the targeted socket.
     * @return true if the handshake was answered and the message sent afterwards.
     */
    protected boolean sendTCPMessage(TCPMessage message, InetAddress toAddress, int toPort) {
        message.setSender(address, port);
        message.setReceiver(toAddress, toPort);
        message.generateKey();

        if (toAddress.equals(address) && toPort == port) {
            // Message sent to self is transfered internally.
            print(String.format("Took in %s.", message.toString()));
            takeInMessage(message);
            return true;
        }

        boolean sent = sendMessage(message, toAddress, toPort);

        if (!sent)
            return false;

        print(String.format("Sent handshake initialization to %s:%d for %s.",
                message.getReceiverAddress().getHostAddress(), message.getReceiverPort(),
                message.getClass().getName()));

        waitingMessages.put(message.getHandshakeKey(), message);

        listen(100);

        return !waitingMessages.containsKey(message.getHandshakeKey());
    }

    // Called when a handshake-approved message is received. Override in subclasses.
    protected abstract void takeInMessage(TCPMessage message);

    protected abstract void onListenTimeout();

    protected void setTimeout(int ms) {
        try {
            socket.setSoTimeout(ms);
        } catch (SocketException e) {
            System.out.printf("Failed to set socket timeout %d for %s.%n", ms, getFullAddress());
            e.printStackTrace();
        }
    }

    protected void print(String toPrint) {
        if (print && !lastPrint.equals(toPrint)) {
            System.out.println(toPrint);
            lastPrint = toPrint;
        }
    }

    private boolean sendMessage(TCPMessage message, InetAddress toAddress, int toPort) {
        byte[] messageBytes = message.serialize();
        DatagramPacket datagramPacket = new DatagramPacket(messageBytes, messageBytes.length, toAddress, toPort);

        try {
            socket.send(datagramPacket);
            return true;
        } catch (IOException e) {
            System.out.printf("Exception thrown when sending %s to %s:%d.%n", message, toAddress, toPort);
            return false;
        }
    }

    // Takes in and treats a given byte[] as a message.
    // If parsing of the bytes fails, nothing is done.
    // Considers all messages as a part of a handshake.
    private boolean processMessage(byte[] messageBytes) {
        TCPMessage message = TCPMessage.deserialize(messageBytes);

        // deserialize returns null if the given byte array couldn't be deserialized.
        if (message == null)
            return false;

        if (message instanceof PingMessage) {
            PingMessage pingMessage = (PingMessage) message;

            if (pingMessage.getSenderAddress().equals(address) && pingMessage.getSenderPort() == port) {
                // The print was sent from here and was a success.
                pinged.remove(pingMessage.getHandshakeKey());
                return true;
            } else {
                // The ping was sent to this node. Reply.
                sendMessage(pingMessage, message.getSenderAddress(), message.getSenderPort());
                return false;
            }
        }

        if (waitingMessages.containsKey(message.getHandshakeKey())) {
            // Handshake reply received for waiting message.
            TCPMessage waitingMessage = waitingMessages.get(message.getHandshakeKey());
            waitingMessages.remove(message.getHandshakeKey());

            print(String.format("Handshake reply received for %s. Sending to %s:%d.",
                    waitingMessage.getClass().getName(), waitingMessage.getReceiverAddress().getHostAddress(),
                    waitingMessage.getReceiverPort()));
            sendMessage(waitingMessage, waitingMessage.getReceiverAddress(), waitingMessage.getReceiverPort());

            return true;
        } else if (approvedKeys.contains(message.getHandshakeKey())) {
            // Approved message received
            approvedKeys.remove(message.getHandshakeKey());

            print(String.format("Took in %s.", message.toString()));
            takeInMessage(message);

            return false;
        } else {
            // Handshake initialization received.
            // Now, respond to the handshake and await the approved message.
            approvedKeys.add(message.getHandshakeKey());

            print(String.format("Handshake from %s:%d for %s.", message.getSenderAddress().getHostAddress(),
                    message.getSenderPort(), message.getClass().getName()));
            sendMessage(message, message.getSenderAddress(), message.getSenderPort());

            return false;
        }
    }
}
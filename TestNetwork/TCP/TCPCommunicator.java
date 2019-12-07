package TCP;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import TCP.messaging.*;

public abstract class TCPCommunicator {

    protected InetAddress address;
    protected int port;

    private DatagramSocket socket;
    private byte[] buffer;

    private HashSet<Integer> pingedKeys;
    private HashSet<Integer> acceptedKeys;

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
            pingedKeys = new HashSet<>();
            acceptedKeys = new HashSet<>();

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
     * If 0 is given, it listens forever, calling onListenTimeout() twice every
     * second. If the method was called with a timelimit, it is assumed that a
     * ping-response is expected. If one is received, the listening is cut short and
     * assumed to have fulfilled its purpose.
     * 
     * @param listenForMs time to listen.
     */
    protected void listen(int listenForMs) {
        while (true) {
            if (listenForMs != 0)
                setTimeout(listenForMs);
            else
                setTimeout(500);

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
     * onListenTimeout() twice every second.
     */
    protected void listen() {
        listen(0);
    }

    /**
     * Pings another TCPCommunicator, expecting a response within 100ms. If a key
     * (not 0) is provided, the PingMessage is configured as a handshake for an
     * upcoming message.
     * 
     * @param toAddress InetAddress of the targeted socket.
     * @param toPort    Port of the targeted socket.
     * @param key       Key of message to be accepted with ping.
     * @return true if a response was received. Otherwise false.
     */
    protected boolean ping(InetAddress toAddress, int toPort, int key) {
        PingMessage message = new PingMessage();

        message.setSender(address, port);
        message.setReceiver(toAddress, toPort);
        if (key != 0) {
            message.setHandshakeKey(key);
            message.setIsHandshake(true);
        }

        if (toAddress.equals(address) && toPort == port) {
            // Self ping
            return true;
        }

        boolean sent = sendMessage(message, toAddress, toPort);

        if (!sent)
            return false;

        pingedKeys.add(message.getHandshakeKey());
        listen(100);
        boolean success = !pingedKeys.contains(message.getHandshakeKey());

        pingedKeys.clear();
        return success;
    }

    /**
     * Pings another TCPCommunicator, expecting a response within 100ms.
     * 
     * @param toAddress InetAddress of the targeted socket.
     * @param toPort    Port of the targeted socket.
     * @return true if a response was received. Otherwise false.
     */
    protected boolean ping(InetAddress toAddress, int toPort) {
        return ping(toAddress, toPort, 0);
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

        if (!ping(toAddress, toPort, message.getHandshakeKey())) {
            print(String.format("Handshake with %s:%d for %s failed.", message.getReceiverAddress().getHostAddress(),
                    message.getReceiverPort(), message.getClass().getName()));
            return false;
        }

        print(String.format("Handshake reply received for %s. Sending to %s:%d.", message.getClass().getName(),
                message.getReceiverAddress().getHostAddress(), message.getReceiverPort()));

        return sendMessage(message, toAddress, toPort);
    }

    /**
     * Called when a handshake-approved message is received.
     * 
     * @param message the approved TCPMessage to take in.
     */
    protected abstract void takeInMessage(TCPMessage message);

    /**
     * Called twice every second between listening intervals allowing periodic
     * activities.
     */
    protected abstract void onListenTimeout();

    protected void print(String toPrint) {
        if (print && !lastPrint.equals(toPrint)) {
            System.out.println(toPrint);
            lastPrint = toPrint;
        }
    }

    /**
     * Sets socket timeout.
     * 
     * @param ms
     */
    private void setTimeout(int ms) {
        try {
            socket.setSoTimeout(ms);
        } catch (SocketException e) {
            System.out.printf("Failed to set socket timeout %d for %s.%n", ms, getFullAddress());
            e.printStackTrace();
        }
    }

    /**
     * Sends a TCPMessage to a given socket.
     * 
     * @param message   to be sent to the socket.
     * @param toAddress InetAddress of targeted socket.
     * @param toPort    Port of the targeted socket.
     * @return
     */
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

    /**
     * Takes in and treats a given byte[] as a TCPMessage. If parsing of the bytes
     * fails, nothing is done.
     * 
     * @param messageBytes to be parsed into a TCPMessage.
     * @return true if the given message is a response to a ping.
     */
    private boolean processMessage(byte[] messageBytes) {
        TCPMessage message = TCPMessage.deserialize(messageBytes);

        // Deserialize returns null if the given byte array couldn't be deserialized.
        if (message == null)
            return false;

        if (message instanceof PingMessage) {
            PingMessage pingMessage = (PingMessage) message;
            if (pingMessage.getSenderAddress().equals(address) && pingMessage.getSenderPort() == port) {
                // The ping was sent from here and was a success.
                pingedKeys.remove(pingMessage.getHandshakeKey());
                return true;
            } else {
                // The ping was sent to this node. Reply.
                if (pingMessage.isHandshake())
                    acceptedKeys.add(pingMessage.getHandshakeKey());
                sendMessage(message, pingMessage.getSenderAddress(), pingMessage.getSenderPort());
                return false;
            }
        }

        if (acceptedKeys.contains(message.getHandshakeKey())) {
            // Approved message received
            acceptedKeys.remove(message.getHandshakeKey());

            print(String.format("Took in %s.", message.toString()));
            takeInMessage(message);
        }

        return false;
    }
}
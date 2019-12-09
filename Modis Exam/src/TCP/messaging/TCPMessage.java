package TCP.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

/**
 * Representation of a transfered message sent from one socket to another. Can
 * be serialized into a byte-array to be sent and then deserialized on the
 * receiving side.
 */
public abstract class TCPMessage implements Serializable {

    private static final long serialVersionUID = 5681145852460707682L;
    private static Random RND;
    public static int BYTE_SIZE = 5000;

    private InetAddress senderAddress;
    private int senderPort;
    private InetAddress receiverAddress;
    private int receiverPort;

    private int handshakeKey;

    /**
     * Serializes the object into a byte-array.
     * 
     * @return object as a byte-array.
     */
    public byte[] serialize() {
        byte[] messageBytes = null;

        try {
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteOutputStream);
            outputStream.writeObject(this);
            outputStream.flush();
            messageBytes = byteOutputStream.toByteArray();
        } catch (IOException e) {
            return null;
        }

        return messageBytes;
    }

    public void generateHandshakeKey() {
        handshakeKey = randomKey();
    }

    public void setSender(InetAddress address, int port) {
        senderAddress = address;
        senderPort = port;
    }

    public void setReceiver(InetAddress address, int port) {
        receiverAddress = address;
        receiverPort = port;
    }

    public void setHandshakeKey(int key) {
        handshakeKey = key;
    }

    public InetAddress getSenderAddress() {
        return senderAddress;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public InetAddress getReceiverAddress() {
        return receiverAddress;
    }

    public int getReceiverPort() {
        return receiverPort;
    }

    public int getHandshakeKey() {
        return handshakeKey;
    }

    /**
     * Attempts to parse a byte-array into a TCPMessage object.
     * 
     * @param messageBytes byte-array (supposedly) representing a TCPMessage.
     * @return the derived TCPMessage or null if the deserialization fails.
     */
    public static TCPMessage deserialize(byte[] messageBytes) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(messageBytes));
            TCPMessage message = (TCPMessage) inputStream.readObject();
            inputStream.close();
            return message;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static int randomKey() {
        if (RND == null)
            RND = new Random();
        return RND.nextInt();
    }
}
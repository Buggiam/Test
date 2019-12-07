package TCP.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

public abstract class TCPMessage implements Serializable {

    private static final long serialVersionUID = 5681145852460707682L;

    public static int BYTE_SIZE = 5000;

    private InetAddress senderAddress;
    private int senderPort;
    private InetAddress receiverAddress;
    private int receiverPort;

    private int handshakeKey;

    private static Random RND;

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

    public void generateKey() {
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

    public void setHandshakeKey(int key) {
        handshakeKey = key;
    }

    public int getHandshakeKey() {
        return handshakeKey;
    }    

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

    private int randomKey() {
        if (RND == null) RND = new Random();
        return RND.nextInt();
    }
}
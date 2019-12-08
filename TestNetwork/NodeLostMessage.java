import java.net.InetAddress;

import TCP.messaging.TCPMessage;

public class NodeLostMessage extends TCPMessage {

    private static final long serialVersionUID = 8857228273125300742L;

    private InetAddress lonelyAddress;
    private int lonelyPort;
    private InetAddress lostAddress;
    private int lostPort;
    private InetAddress lostNextAddress;
    private int lostNextPort;
    private InetAddress lostNextNextAddress;
    private int lostNextNextPort;

    public NodeLostMessage(InetAddress lonelyAddress, int lonelyPort, InetAddress lostAddress, int lostPort,
            InetAddress lostNextAddress, int lostNextPort) {
        super();
        this.lonelyAddress = lonelyAddress;
        this.lonelyPort = lonelyPort;
        this.lostAddress = lostAddress;
        this.lostPort = lostPort;
        this.lostNextAddress = lostNextAddress;
        this.lostNextPort = lostNextPort;
    }

    public void setLostNextNext(InetAddress address, int port) {
        lostNextNextAddress = address;
        lostNextNextPort = port;
    }

    public InetAddress getLonelyAddress() {
        return lonelyAddress;
    }

    public int getLonelyPort() {
        return lonelyPort;
    }

    public InetAddress getLostAddress() {
        return lostAddress;
    }

    public int getLostPort() {
        return lostPort;
    }

    public InetAddress getLostNextAddress() {
        return lostNextAddress;
    }

    public int getLostNextPort() {
        return lostNextPort;
    }

    public InetAddress getLostNextNextAddress() {
        return lostNextNextAddress;
    }

    public int getLostNextNextPort() {
        return lostNextNextPort;
    }

    @Override
    public String toString() {
        return String.format("NodeLostMessage[%s:%d]", lostAddress.getHostAddress(), lostPort);
    }
}
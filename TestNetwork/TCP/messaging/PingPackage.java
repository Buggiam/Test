package TCP.messaging;

import java.net.InetAddress;

public class PingPackage {

    private PingMessage message;
    private InetAddress toAddress;
    private int toPort;

    public PingPackage(PingMessage message, InetAddress toAddress, int toPort) {
        this.message = message;
        this.toAddress = toAddress;
        this.toPort = toPort;
    }

    public PingMessage getMessage() {
        return message;
    }

    public InetAddress getToAddress() {
        return toAddress;
    }

    public int getToPort() {
        return toPort;
    }
}
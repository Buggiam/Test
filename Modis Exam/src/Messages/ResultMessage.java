package Messages;

import java.net.InetAddress;

import TCP.messaging.TCPMessage;

public class ResultMessage extends TCPMessage {

    private static final long serialVersionUID = -9182126501366197265L;

    private InetAddress clientAddress;
    private int clientPort;
    private String result = "Null";

    public ResultMessage(InetAddress clientAddress, int clientPort) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("ResultMessage[%s]", result);
    }
}
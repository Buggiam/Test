package Messages;

import java.net.InetAddress;

import TCP.messaging.TCPMessage;

public class BidMessage extends TCPMessage {

    private static final long serialVersionUID = 1L;

    private int amount;

    private InetAddress clientAddress;
    private int clientPort;

    private InetAddress entrySocketAddress;
    private int entrySocketPort;

    private String outcome = "InProgress";

    public BidMessage(int amount, InetAddress clientAddress, int clientPort) {
        this.amount = amount;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public void setEntryNode(InetAddress address, int port) {
        this.entrySocketAddress = address;
        this.entrySocketPort = port;
    }

    public int getAmount() {
        return amount;
    }

    public boolean hasBeenReceivedByNode() {
        return entrySocketAddress != null;
    }

    public boolean matchesEntrySocket(InetAddress address, int port) {
        return hasBeenReceivedByNode() && entrySocketAddress.equals(address) && entrySocketPort == port;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return String.format("BidMessage[%d, %s]", amount, outcome);
    }
}
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import TCP.TCPNode;
import TCP.messaging.TCPMessage;

public class RingNode extends TCPNode {

    public RingNode(int port, InetAddress neighborAddress, int neighborPort) {
        super(port);

        printNeighbors();

        if (neighborAddress != null) {
            introduceToNode(neighborAddress, neighborPort);
        }

        listen();
    }

    public RingNode(int port) {
        this(port, null, 0);
    }

    @Override
    protected void takeInMessage(TCPMessage message) {
        if (message instanceof RingIntroduction) {
            RingIntroduction intro = (RingIntroduction) message;
            processIntroduction(intro);
        } else if (message instanceof NodeLostMessage) {
            NodeLostMessage lostMessage = (NodeLostMessage) message;
            processNodeLost(lostMessage);
        }

        listen();
    }

    @Override
    protected TCPMessage getIntroduction() {
        return new RingIntroduction(address, port);
    }

    @Override
    protected String[] getNextNeighbors() {
        return new String[] { "next" }; 
    }

    @Override
    protected void onNeighborDie(String neighborName) {
        System.out.println("Neighbor lost: " + neighborName);
        
        if (!hasNeighbor("nextnext")) {
            // No network to restore, since this is the only node left.
            return;
        }

        System.out.println("Restoring network...");
        Node lostNode = getNeighbor("next");
        Node lostNodeNext = getNeighbor("nextnext");
        NodeLostMessage lostMessage = new NodeLostMessage(address, port, lostNode.address, lostNode.port,
                lostNodeNext.address, lostNodeNext.port);

        sendTCPMessage(lostMessage, getNeighbor("nextnext").address, getNeighbor("nextnext").port);

        listen();
    }

    private void processIntroduction(RingIntroduction intro) {
        switch (intro.getState()) {
        case Insertion:
            // This is the initial entry point of the sender.
            // The sender must replace this node's 'nextnext'.
            setNeighbor("nextnext", intro.getIntroducedAddress(), intro.getIntroducedPort());
            intro.progressState();
            sendTCPMessage(intro, getNeighbor("next").address, getNeighbor("next").port);
            break;
        case Replacement:
            // The introduced node will be put in front of this node in the circle.
            Node prevNext = getNeighbor("next");
            Node prevNextNext = getNeighbor("nextnext");

            if (prevNextNext.address.equals(intro.getIntroducedAddress())
                    && prevNextNext.port == intro.getIntroducedPort()) {
                // The network will only consist of two nodes after this node is added.
                // Make nextnext point to self and the same for the new node.
                removeNeighbor("nextnext");
                intro.setNext(address, port);
                intro.setNextNext(intro.getIntroducedAddress(), intro.getIntroducedPort());
            } else {
                intro.setNext(prevNext.address, prevNext.port);
                intro.setNextNext(prevNextNext.address, prevNextNext.port);
                setNeighbor("nextnext", prevNext.address, prevNext.port);
            }

            setNeighbor("next", intro.getIntroducedAddress(), intro.getIntroducedPort());
            intro.progressState();
            sendTCPMessage(intro, intro.getIntroducedAddress(), intro.getIntroducedPort());
            break;
        case Transfer:
            setNeighbor("next", intro.getNextAddress(), intro.getNextPort());
            setNeighbor("nextnext", intro.getNextNextAddress(), intro.getNextNextPort());
            break;
        }

        printNeighbors();
    }

    private void processNodeLost(NodeLostMessage lost) {
        Node next = getNeighbor("next");
        Node nextNext = getNeighbor("nextnext");

        if (lost.getLonelyAddress().equals(address) && lost.getLonelyPort() == port) {
            // The message has come all the way around the network. Now close the gap.
            setNeighbor("next", lost.getLostNextAddress(), lost.getLostNextPort());
            setNeighbor("nextnext", lost.getLostNextNextAddress(), lost.getLostNextNextPort());

            printNeighbors();
            return;
        }

        if (lost.getLostNextAddress().equals(address) && lost.getLostNextPort() == port) {
            // This is the node after the lost node.
            // The lonely node needs to know the node after this one.
            lost.setLostNextNext(next.address, next.port);
        } 
        
        if (lost.getLostAddress().equals(nextNext.address) && lost.getLostPort() == nextNext.port) {
            // nextNext is the one that is lost.
            // replace with the one after the lost node
            setNeighbor("nextnext", lost.getLostNextAddress(), lost.getLostNextPort());
        }

        printNeighbors();

        // Now forward the message around the network.
        sendTCPMessage(lost, next.address, next.port);
    }

    private void printNeighbors() {
        Node next = getNeighbor("next");
        Node nextNext = getNeighbor("nextnext");

        System.out.println("Next: " + next);
        System.out.println("NextNext: " + nextNext);
    }

    private static String START_REGEX_S = "^ *(\\d{4,5}) *((\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}|localhost):\\d{4,5})? *$";
    private static Pattern START_REGEX = Pattern.compile(START_REGEX_S);

    public static void main(String[] args) throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);

        Matcher m;

        do {
            System.out.println("{port} [neighbor-address:neighbor-port]");
            String input = scanner.nextLine();
            m = START_REGEX.matcher(input);
        } while (!m.find());

        int port = Integer.parseInt(m.group(1));

        if (m.group(2) != null) {
            String[] otherNode = m.group(2).split(":");
            InetAddress otherNodeAddress = InetAddress.getByName(otherNode[0]);
            int otherNodePort = Integer.parseInt(otherNode[1]);
            new RingNode(port, otherNodeAddress, otherNodePort);
        } else {
            new RingNode(port);
        }

        scanner.close();
    }
}
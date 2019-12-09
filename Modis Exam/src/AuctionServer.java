import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Messages.BidMessage;
import Messages.NodeLostMessage;
import Messages.ResultMessage;
import Messages.RingIntroduction;
import TCP.TCPNode;
import TCP.messaging.TCPMessage;

public class AuctionServer extends TCPNode {

    private long auctionEndTime;
    private int highestBid = -1;   
    
    private static long AUCTION_DURATION = 60000;
    
    public AuctionServer(int port, InetAddress neighborAddress, int neighborPort) {
        super(port);

        printNeighbors();

        if (neighborAddress != null) {
            introduceToNode(neighborAddress, neighborPort);
        } else {
            // This is the first node in the network. Start the auction.
            auctionEndTime = System.currentTimeMillis()  + AUCTION_DURATION;
            System.out.println("New auction ending in " + (AUCTION_DURATION / 1000) + " seconds.");
        }

        listen();
    }

    public AuctionServer(int port) {
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
        } else if (message instanceof BidMessage) {
            BidMessage BidMessage = (BidMessage) message;
            processBid(BidMessage);
        } else if (message instanceof ResultMessage) {
            ResultMessage resultMessage = (ResultMessage) message;
            processResult(resultMessage);
        }
    }

    @Override
    protected TCPMessage getIntroduction() {
        return new RingIntroduction(address, port);
    }

    @Override
    protected String getNextNeighbor() {
        return "next";
    }

    @Override
    protected void onNeighborDie(String neighborName) {
        print("Neighbor lost: " + neighborName);

        if (hasNeighbor("nextnext")) {
            System.out.println("Restoring network...");
            Node lostNode = getNeighbor("next");
            Node lostNodeNext = getNeighbor("nextnext");
            NodeLostMessage lostMessage = new NodeLostMessage(address, port, lostNode.address, lostNode.port,
                    lostNodeNext.address, lostNodeNext.port);

            sendTCPMessage(lostMessage, lostNodeNext.address, lostNodeNext.port);
        }

        removeNeighbor("next");
        printNeighbors();
    }

    private void processBid(BidMessage bid) {
        if (bid.matchesEntrySocket(address, port)) {
            // The bid has come all the way accross the network and has fulfilled it's purpose.
            // Now the client needs a response.
            sendTCPMessage(bid, bid.getClientAddress(), bid.getClientPort());
            return;
        }

        if (!bid.hasBeenReceivedByNode()) {
            bid.setEntryNode(address, port);
        }

        if (auctionEndTime < System.currentTimeMillis()) {
            bid.setOutcome("Exception: Time auction has expired.");
        } else if (bid.getAmount() > highestBid) {
            highestBid = bid.getAmount();
            System.out.println("Received bid of " + bid.getAmount());
            bid.setOutcome("Success: Bid placed.");
        } else {
            bid.setOutcome("Fail: Bid too small.");
        }

        // Forward the bid around the network
        sendTCPMessage(bid, getNeighbor("next").address, getNeighbor("next").port);
    }

    private void processResult(ResultMessage result) {
        if (auctionEndTime == 0) {
            result.setResult("No auction started.");
        } else if (auctionEndTime < System.currentTimeMillis()) {
            result.setResult("Result: " + highestBid);
        } else {
            result.setResult("Current highest bid: " + highestBid);
        }

        sendTCPMessage(result, result.getClientAddress(), result.getClientPort());
    }

    private void processIntroduction(RingIntroduction intro) {
        switch (intro.getState()) {
        case Insertion:
            // This is the initial entry point of the sender.
            // The sender must replace this node's 'nextnext'.
            setNeighbor("nextnext", intro.getIntroducedAddress(), intro.getIntroducedPort());

            intro.setAuctionTransfer(auctionEndTime, highestBid);

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

            checkNeighbor();
            break;
        case Transfer:
            setNeighbor("next", intro.getNextAddress(), intro.getNextPort());
            setNeighbor("nextnext", intro.getNextNextAddress(), intro.getNextNextPort());

            auctionEndTime = intro.getAuctionEndTime();
            highestBid = intro.getHighestBid();

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

        print("> " + next + " > " + nextNext);
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
            new AuctionServer(port, otherNodeAddress, otherNodePort);
        } else {
            new AuctionServer(port);
        }

        scanner.close();
    }
}
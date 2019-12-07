package PairNetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import TCP.TCPNode;
import TCP.messaging.TCPMessage;

public class PairNode extends TCPNode {

    private static String START_REGEX_S = "^ *(\\d{4,5}) *((\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}|localhost):\\d{4,5})? *$";
    private static Pattern START_REGEX = Pattern.compile(START_REGEX_S);

    public PairNode(int port, InetAddress neighborAddress, int neighborPort) {
        super(port);

        if (neighborAddress != null) {
            introduceToNode(neighborAddress, neighborPort);
            setNeighbor("next", neighborAddress, neighborPort);
            checkNeighbor();
        }

        listen();
    }

    public PairNode(int port) {
        this(port, null, 0);
    }

    @Override
    protected void takeInMessage(TCPMessage message) {
        if (message instanceof PairIntroduction) {
            PairIntroduction introduction = (PairIntroduction) message;
            setNeighbor("next", introduction.getSenderAddress(), introduction.getSenderPort());

            System.out.println("Was introduced to neighbor.");
            listen();
        }
    }

    @Override
    protected TCPMessage getIntroduction() {
        return new PairIntroduction();
    }

    @Override
    protected String getNextNeighbor() {
        return "next";
    }

    @Override
    protected void onNeighborDie(String neighborName) {
        System.out.println("Neighbor lost: " + neighborName);
    }

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
            new PairNode(port, otherNodeAddress, otherNodePort);
        } else {
            new PairNode(port);
        }

        scanner.close();
    }
}
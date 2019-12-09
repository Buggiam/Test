import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import Messages.BidMessage;
import Messages.ResultMessage;
import TCP.TCPCommunicator;
import TCP.messaging.TCPMessage;

public class AuctionClient extends TCPCommunicator {

    private static Scanner scn;

    public AuctionClient(int port) {
        super(port);
        System.out.println("Bid on the auction: \"bid {amount} {node-address:node-port}\"");
        System.out.println("Get current result: \"result {node-address:node-port}\"");
        requestUserInput();
    }

    @Override
    protected void takeInMessage(TCPMessage message) {}

    @Override
    protected void onListenTimeout() {}

    private void requestUserInput() {
        while (true) {
            String input = scn.nextLine();

            InetAddress nodeAddress;
            int nodePort;

            if (input.matches("^bid +(\\d+) +(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost):\\d{4,5} *$")) {
                String[] bid = input.split(" +");
                int amount = Integer.parseInt(bid[1]);
                String[] node = bid[2].split(":");
                try {
                    nodeAddress = InetAddress.getByName(node[0]);
                } catch (UnknownHostException e) {
                    System.out.println("Invalid node address.");
                    continue;
                }
                nodePort = Integer.parseInt(node[1]);

                placeBid(amount, nodeAddress, nodePort);
            } else if (input.matches("^result +(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost):\\d{4,5} *$")) {
                String[] bid = input.split(" +");
                String[] node = bid[1].split(":");
                try {
                    nodeAddress = InetAddress.getByName(node[0]);
                } catch (UnknownHostException e) {
                    System.out.println("Invalid node address.");
                    continue;
                }
                nodePort = Integer.parseInt(node[1]);

                getResult(nodeAddress, nodePort);
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private void placeBid(int amount, InetAddress nodeAddress, int nodePort) {
        System.out.println("Placing bid of " + amount + "...");
        BidMessage bid = new BidMessage(amount, address, port);

        boolean sent = sendTCPMessage(bid, nodeAddress, nodePort);
        if (!sent) {
            System.out.println("Bid failed. No handshake-response from node.");
            return;
        }

        System.out.println("Bid sent. Awaiting confirmation...");
        listen(1000);
    }

    private void getResult(InetAddress nodeAddress, int nodePort) {
        System.out.println("Gettting result...");
        ResultMessage result = new ResultMessage(address, port);

        boolean sent = sendTCPMessage(result, nodeAddress, nodePort);
        if (!sent) {
            System.out.println("Request failed. No handshake-response from node.");
            return;
        }

        System.out.println("Request sent. Awaiting response...");
        listen(1000);
    }

    public static void main(String[] args) throws UnknownHostException {
        scn = new Scanner(System.in);
        String input;

        do {
            System.out.println("Enter port address:");
            input = scn.nextLine();
        } while (!input.matches("^\\d{4,5}$"));

        int port = Integer.parseInt(input);

        new AuctionClient(port);
    }
}
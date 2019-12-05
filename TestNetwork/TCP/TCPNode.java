package TCP;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import TCP.messaging.*;

public abstract class TCPNode extends TCPCommunicator {

    private HashMap<String, Node> neighbors;

    protected TCPNode(int port) {
        super(port);
        neighbors = new HashMap<>();
    }

    protected void introduceToNode(InetAddress nodeAddress, int nodePort) {
        TCPMessage introduction = getIntroduction();
        if (introduction != null) {
            sendTCPMessage(introduction, nodeAddress, nodePort);
        } else {
            print(String.format("No introduction implemented for %s.", this.getClass().getName()));
        }
    }

    protected abstract TCPMessage getIntroduction();

    protected abstract String[] getNextNeighbors();

    protected abstract void onNeighborDie(String neighborName);

    protected void checkNeighbors() {
        System.out.println("CHECKING NEIGHBORS...");

        String[] neighborNames = getNextNeighbors();

        ArrayList<PingPackage> pings = new ArrayList<>();

        for (String neighborName : neighborNames) {
            Node neighbor = getNeighbor(neighborName);

            if (neighbor.address.equals(address) && neighbor.port == port) {
                System.out.println("SELF PING");
                continue;
            }

            PingMessage pingMessage = new PingMessage();
            PingPackage pingPackage = new PingPackage(pingMessage, neighbor.address, neighbor.port);
            pings.add(pingPackage);
        }
        
        if (!pings.isEmpty()) {
            boolean[] success = ping(pings);

            for (int i = 0; i < neighborNames.length; i++) {
                String neighborName = neighborNames[i];

                if (!success[i]) {
                    onNeighborDie(neighborName);
                    removeNeighbor(neighborName);
                }
            }
        }

        // To go back to unlimited listening after pinging neighbors, this is required.
        listen();
    }

    @Override
    protected void onPingAnswered() {
        checkNeighbors();
    }

    protected void setNeighbor(String name, InetAddress address, int port) {
        Node neighborNode = new Node(address, port);
        neighbors.put(name, neighborNode);
    }

    protected Node getNeighbor(String name) {
        if (!neighbors.containsKey(name))
            setNeighbor(name, address, port);

        return neighbors.get(name);
    }

    protected boolean hasNeighbor(String name) {
        Node neighbor = getNeighbor(name);

        if (neighbor.address.equals(address) && neighbor.port == port) return false;
        return true;
    }

    protected void removeNeighbor(String name) {
        setNeighbor(name, address, port);
    }

    protected class Node {
        public InetAddress address;
        public int port;

        private Node(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }

        public Node clone() {
            try {
                return new Node(InetAddress.getByName(address.getHostAddress()), port);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(0);
                return null;
            }
        }

        public String toString() {
            return String.format("%s:%d", address.getHostAddress(), port);
        }
    }
}
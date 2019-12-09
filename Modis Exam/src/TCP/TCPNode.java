package TCP;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import TCP.messaging.*;

/**
 * Represents a node in a network, which has neighbors that they periodically
 * ping to ensure their continued status.
 */
public abstract class TCPNode extends TCPCommunicator {

    private HashMap<String, Node> neighbors;

    protected TCPNode(int port) {
        super(port);
        neighbors = new HashMap<>();
    }

    /**
     * Called to initiate the initial introduction for an existing node in the
     * network. getIntroduction() dictates the TCPMessage that is sent to that
     * existing node.
     * 
     * @param nodeAddress InetAddress pointing to the existing node.
     * @param nodePort    Port pointing to the existing node.
     */
    protected void introduceToNode(InetAddress nodeAddress, int nodePort) {
        TCPMessage introduction = getIntroduction();
        if (introduction != null) {
            sendTCPMessage(introduction, nodeAddress, nodePort);
        } else {
            print(String.format("No introduction implemented for %s.", this.getClass().getName()));
        }
    }

    /**
     * @return the TCPMessage that is used as an introduction for the added network
     *         node.
     */
    protected abstract TCPMessage getIntroduction();

    /**
     * @return name of the next node in the network. The node that is monitored by
     *         this node.
     */
    protected abstract String getNextNeighbor();

    /**
     * Called when a monitored node is unresponsive.
     * 
     * @param neighborName name of the lost node.
     */
    protected abstract void onNeighborDie(String neighborName);

    /**
     * Monitors the next node in the network.
     */
    protected void checkNeighbor() {
        String neighborName = getNextNeighbor();
        Node neighbor = getNeighbor(neighborName);
        boolean success = ping(neighbor.address, neighbor.port);

        if (!success)
            onNeighborDie(neighborName);
    }

    @Override
    protected void onListenTimeout() {
        checkNeighbor();
    }

    /**
     * Sets the socket of a neighbor-node represented by the given name.
     * 
     * @param name    representation of the neighbor-relation.
     * @param address
     * @param port
     */
    protected void setNeighbor(String name, InetAddress address, int port) {
        Node neighborNode = new Node(address, port);
        neighbors.put(name, neighborNode);
    }

    /**
     * Gets a neighbor-Node by name. Neighbor relations point to the node itself by
     * default.
     * 
     * @param name name of the searched neighbor.
     * @return neighbor encapsuped in a Node object.
     */
    protected Node getNeighbor(String name) {
        if (!neighbors.containsKey(name))
            setNeighbor(name, address, port);

        return neighbors.get(name);
    }

    /**
     * @param name of the searched neighbor.
     * @return whether a node is mapped to the neighbor-relation.
     */
    protected boolean hasNeighbor(String name) {
        Node neighbor = getNeighbor(name);

        if (neighbor.address.equals(address) && neighbor.port == port)
            return false;
        return true;
    }

    /**
     * Removes a neighbor relation.
     * @param name of the relation.
     */
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
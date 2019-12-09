package Messages;

import java.net.InetAddress;

import TCP.messaging.TCPMessage;

public class RingIntroduction extends TCPMessage {

    private static final long serialVersionUID = -1040610448276932993L;

    private State state = State.Insertion;

    private InetAddress introducedAddress;
    private int introducedPort;
    
    private InetAddress nextAddress;
    private int nextPort;
    private InetAddress nextNextAddress;
    private int nextNextPort;
    
    public RingIntroduction(InetAddress introducedAddress, int introducedPort) {
        super();
        this.introducedAddress = introducedAddress;
        this.introducedPort = introducedPort;
    } 

    public void setNext(InetAddress address, int port) {
        nextAddress = address;
        nextPort = port;
    }

    public void setNextNext(InetAddress address, int port) {
        nextNextAddress = address;
        nextNextPort = port;
    }

    public InetAddress getIntroducedAddress() {
        return introducedAddress;
    }

    public int getIntroducedPort() {
        return introducedPort;
    }

    public InetAddress getNextAddress() {
        return nextAddress;
    }

    public int getNextPort() {
        return nextPort;
    }

    public InetAddress getNextNextAddress() {
        return nextNextAddress;
    }

    public int getNextNextPort() {
        return nextNextPort;
    }

    public State getState() {
        return state;
    }

    public void progressState() {
        switch (state) {
            case Insertion: 
                state = State.Replacement;
                break;
            case Replacement: 
                state = State.Transfer;
                break;
            case Transfer:
                break;    
        }
    }

    @Override
    public String toString() {
        return "RingIntroduction[" + state + "]";
    }

    public enum State {
        Insertion, Replacement, Transfer;
    }
}
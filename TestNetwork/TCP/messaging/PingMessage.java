package TCP.messaging;

public class PingMessage extends TCPMessage {

    private static final long serialVersionUID = 6581906973137587162L;

    private boolean isHandshake = false;
    
    public PingMessage() {
        super();
    }

    public void setIsHandshake(boolean isHandshake) {
        this.isHandshake = isHandshake;
    }

    public boolean isHandshake() {
        return isHandshake;
    }
}
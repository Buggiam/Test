package TCP.messaging;

/**
 * An empty message that is used to prepare a TCPCommunicator for a message to
 * be sent or simply to "ping" or monitor their status.
 */
public class PingMessage extends TCPMessage {

    private static final long serialVersionUID = 6581906973137587162L;

    private boolean isHandshake = false;

    public PingMessage() {
        super();
    }

    /**
     * 
     * @param isHandshake whether the PingMessage represents the handshake of a
     *                    TCPMessage-transfer.
     */
    public void setIsHandshake(boolean isHandshake) {
        this.isHandshake = isHandshake;
    }

    /**
     * 
     * @return whether the PingMessage represents the handshake of a
     *         TCPMessage-transfer.
     */
    public boolean isHandshake() {
        return isHandshake;
    }
}
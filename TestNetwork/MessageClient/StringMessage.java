package MessageClient;

import TCP.messaging.TCPMessage;

public class StringMessage extends TCPMessage {

    private static final long serialVersionUID = -745347922761313843L;

    private String message;

    public StringMessage(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
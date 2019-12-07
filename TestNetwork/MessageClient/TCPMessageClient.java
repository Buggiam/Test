package MessageClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import TCP.TCPCommunicator;
import TCP.messaging.TCPMessage;

public class TCPMessageClient extends TCPCommunicator {

    private Scanner scn;

    private static String SEND_MESSAGE_REGEX_S = "^ *(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost) *: *(\\d{4,5}) +(.+[^ ]) *$";
    private static Pattern SEND_MESSAGE_REGEX = Pattern.compile(SEND_MESSAGE_REGEX_S);

    public TCPMessageClient(int port) {
        super(port);
        setPrintDetails(true);

        scn = new Scanner(System.in);

        while (true) {
            listenToInput();
        }
    }

    @Override
    protected void takeInMessage(TCPMessage message) {
        if (message instanceof StringMessage) {
            StringMessage stringMessage = (StringMessage) message;
            System.out.println("Received message: " + stringMessage.getMessage());
        } else {
            System.out.println("Message of unknown type received.");
        }
    }

    @Override
    protected void onListenTimeout() {
        // TODO Auto-generated method stub

    }

    private void listenToInput() {
        System.out.println("\n{address}:{port} {message}  |  listen");
        String input = scn.nextLine();

        if (input.trim().equals("listen")) {
            listen();
        } else {
            Matcher m = SEND_MESSAGE_REGEX.matcher(input);

            if (m.find()) {
                try {
                    InetAddress sendToAddress = InetAddress.getByName(m.group(1));
                    int sendToPort = Integer.parseInt(m.group(2));
                    String content = m.group(3);

                    TCPMessage message = new StringMessage(content);
                    sendTCPMessage(message, sendToAddress, sendToPort);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int port = 0;

        do {
            System.out.print("Port: ");
            String input = scanner.nextLine();

            try {
                port = Integer.parseInt(input);
            } catch (Exception e) {
                continue;
            }
        } while (port == 0);

        new TCPMessageClient(port);
        scanner.close();
    }
}
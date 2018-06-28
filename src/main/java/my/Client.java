package my;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Client {

    public static void main(String[] args) throws Exception {
//        WebSocketImpl.DEBUG = true;
        run("ws://localhost:8887");
    }

    private static void run(String url) throws URISyntaxException, InterruptedException {
        WebSocketClient client = new WebSocketClient(new URI(url), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("onOpen: " + getURI());
            }

            @Override
            public void onMessage(String message) {
                System.out.println("onMessage: " + message);
                this.send("Hello");
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("onError");
                ex.printStackTrace();
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println(String.format("onClose(code: %s, reason: %s, remote: %s)", code, reason, remote));
            }
        };
        client.connect();

        System.out.println("Will close in 3 seconds");
        Thread.sleep(3000L);
        client.close();
    }

}
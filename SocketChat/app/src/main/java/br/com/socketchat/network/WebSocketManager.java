package br.com.socketchat.network;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

public class WebSocketManager {

    private WebSocket webSocket;
    private final String SERVER_PATH = "ws://192.168.0.111:3000";

    public interface WebSocketListener {
        void onMessageReceived(JSONObject jsonObject);
        void OnConnectionOpened();
        void OnConnectionClosed();
    }

    private WebSocketListener webSocketListener;

    public WebSocketManager(WebSocketListener webSocketListener) {
        this.webSocketListener = webSocketListener;
        initiateSocketConnection();
    }

    public void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SERVER_PATH)
                .build();

        webSocket = client.newWebSocket(request, new okhttp3.WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                if (webSocketListener != null)
                    webSocketListener.OnConnectionOpened();
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                try {
                    JSONObject jsonObject = new JSONObject(text);

                    if (webSocketListener != null)
                        webSocketListener.onMessageReceived(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                if (webSocketListener != null)
                    webSocketListener.OnConnectionClosed();
            }
        });
    }

    public void sendMessage(JSONObject jsonObject) {
        webSocket.send(jsonObject.toString());
    }

}

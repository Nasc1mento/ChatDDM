package br.com.socketchat.network;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

public class WebSocketManager {

    private static final String SERVER_PATH = "ws://192.168.0.111:3000";
    private final IWebSocketListener webSocketListener;
    private WebSocket webSocket;

    public interface IWebSocketListener {
        void onMessageReceived(JSONObject jsonObject);
        void onConnectionOpened();
        void onConnectionClosed();
        void onError(String message);
    }

    public WebSocketManager(IWebSocketListener webSocketListener) {
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
                    webSocketListener.onConnectionOpened();
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
                    webSocketListener.onConnectionClosed();
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, Response response) {
                if (webSocketListener != null)
                    webSocketListener.onError("Connection Error:" + t.getMessage());
            }
        });
    }

    public void sendMessage(@NonNull JSONObject jsonObject) {
        webSocket.send(jsonObject.toString());
    }

    public void closeConnection() {
        webSocket.close(1000, null);
    }
}

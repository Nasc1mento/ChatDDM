package br.com.socketchat.repository;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.socketchat.model.Message;
import br.com.socketchat.network.api.ApiService;
import br.com.socketchat.network.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {

    public interface MessageRepositoryCallback <T>{
        void onSuccess(T t);
        void onError(String message);
    }

    private ApiService apiService;


    public MessageRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void post(Message message, final MessageRepositoryCallback<Message> callback) {
        Call<Message> call = apiService.post(message);
        call.enqueue(new Callback<Message>(){

            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to post message");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getMessageHistory(final MessageRepositoryCallback<List<Message>> callback) {
        Call<List<Message>> call = apiService.get();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load message history");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }


}

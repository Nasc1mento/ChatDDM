package br.com.socketchat.network.api;

import java.util.List;

import br.com.socketchat.model.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("messages")
    Call<List<Message>> get();

    @POST("messages")
    Call<Message> post(@Body Message message);
}

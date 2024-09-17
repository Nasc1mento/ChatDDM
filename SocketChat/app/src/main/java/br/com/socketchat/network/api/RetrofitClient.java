package br.com.socketchat.network.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.0.111:3001/api/";
    private static Retrofit retrofitClient;

    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BASE_URL).build();
        }
        return retrofitClient;
    }
}

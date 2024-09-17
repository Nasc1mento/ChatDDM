package br.com.socketchat.model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("name")
    private String name;
    @SerializedName("message")
    private String message;


    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

package com.example.demo.util;

public class JsonApiObject {
    private String message;
    private int status;
    private Object data;

    public JsonApiObject() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getGames() {
        return data;
    }

    public void setGames(Object games) {
        this.data = games;
    }
}

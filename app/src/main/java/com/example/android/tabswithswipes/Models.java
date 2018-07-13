package com.example.android.tabswithswipes;

public class Models {

    private String email_adr;
    private String username;

    public Models(String email_adr, String username){
        this.email_adr = email_adr;
        this.username = username;
    }

    public String getEmail_adr() {
        return email_adr;
    }

    public Models(){

    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Model{" +
                "email_adr=" + email_adr+" ," + "username" +username+" }";

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail_adr(String email_adr){
        this.email_adr = email_adr;
    }
}
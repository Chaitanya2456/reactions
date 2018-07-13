package com.example.android.tabswithswipes;

 public class Friends {

    String profile_photo;
    String username;
    String name;

    public Friends(String profile_photo, String username, String name) {
        this.profile_photo = profile_photo;
        this.username = username;
        this.name = name;
    }

    public Friends() {

    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

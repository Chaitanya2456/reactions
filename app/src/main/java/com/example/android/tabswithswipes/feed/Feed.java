package com.example.android.tabswithswipes.feed;

public class Feed {
    private String name, location, imageUrl, description, timeStamp;

    public Feed(String name, String location, String imageUrl, String description, String timeStamp){
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.timeStamp = timeStamp;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getDescription(){
        return description;
    }
    public  void setDescription(String description){
        this.description = description;
    }
    public String getTimeStamp(){
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
}

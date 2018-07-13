package com.example.android.tabswithswipes;

class Post {

    String uploadtext;
    String uploadUri;
    String userID;

    public Post(String uploadtext, String uploadUri, String userID) {
        this.uploadtext = uploadtext;
        this.uploadUri = uploadUri;
        this.userID = userID;
    }


    public String getUploadtext() {
        return uploadtext;
    }

    public void setUploadtext(String uploadtext) {
        this.uploadtext = uploadtext;
    }

    public String getUploadUri() {
        return uploadUri;
    }

    public void setUploadUri(String uploadUri) {
        this.uploadUri = uploadUri;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
package com.example.isho.parkour;

/**
 * Created by isho on 2/15/16.
 */
public class Comment {
    String body;
    User creator;
    int rating;
    public Comment(String b, User cr){
        body=b;
        creator=cr;
        rating=0;
    }
    public void addrate(){
        rating++;
    }
    public String getBody(){
        return body;
    }
    public  User getCreator() {
        return creator;
    }
}

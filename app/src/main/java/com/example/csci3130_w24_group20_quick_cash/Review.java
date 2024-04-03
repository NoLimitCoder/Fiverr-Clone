package com.example.csci3130_w24_group20_quick_cash;

import java.util.UUID;

public class Review {
    private String reviewID;
    private String userID;
    private String description;
    private double stars;
    private String datePosted;

    public Review(){

    }

    public Review(String Name, String UID, String description, double stars){
        this.reviewID = generateJobID();
        this.userID = UID;
        this.description = description;
        this.stars = stars;
        this.datePosted = getDatePosted();
    }
    public String getDatePosted(){
        return datePosted;
    }

    private String generateJobID(){
        return UUID.randomUUID().toString();
    }
    public String getreviewID(){
        return this.reviewID;
    }
}

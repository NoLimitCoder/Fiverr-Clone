package com.example.csci3130_w24_group20_quick_cash;

import java.util.UUID;

public class Review {
    private String reviewID;
    private String userID;
    private double stars;
    private String name;


    public Review(){

    }

    public Review(String UID, double stars){
        this.reviewID = generateReviewID();
        this.userID = UID;
        this.stars = stars;
    }

    private String generateReviewID(){
        return UUID.randomUUID().toString();
    }


    public String getReviewID(){
        return this.reviewID;
    }
}

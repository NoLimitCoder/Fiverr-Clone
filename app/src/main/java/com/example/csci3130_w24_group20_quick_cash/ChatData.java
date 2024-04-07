package com.example.csci3130_w24_group20_quick_cash;

import java.io.Serializable;
import java.util.UUID;

public class ChatData implements Serializable {
    private String chatID;
    private String jobID;
    private String employerUID;
    private String applicantUID;

    private String applicantName;

    private String employerName;

    private String jobTitle;

    public ChatData() {
        //Required Empty Constructor
    }

    /**
     * Constructs a new ChatData object with the provided details.
     *
     * @param jobID         The ID of the job associated with the chat.
     * @param employerUID   The UID of the employer in the chat.
     * @param applicantUID  The UID of the applicant in the chat.
     * @param applicantName The name of the applicant in the chat.
     * @param employerName  The name of the employer in the chat.
     * @param jobTitle      The title of the job associated with the chat.
     */

    public ChatData(String jobID, String employerUID, String applicantUID, String applicantName, String employerName,
                    String jobTitle) {
        this.chatID = generateChatId();
        this.jobID = jobID;
        this.employerUID = employerUID;
        this.applicantUID = applicantUID;
        this.applicantName = applicantName;
        this.employerName = employerName;
        this.jobTitle = jobTitle;
    }

    private String generateChatId() {
        return UUID.randomUUID().toString();
    }

    public String getChatID() {
        return this.chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getEmployerUID() {
        return employerUID;
    }

    public void setEmployerUID(String employerUID) {
        this.employerUID = employerUID;
    }

    public String getApplicantUID() {
        return applicantUID;
    }

    public void setApplicantUID(String applicantUID) {
        this.applicantUID = applicantUID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
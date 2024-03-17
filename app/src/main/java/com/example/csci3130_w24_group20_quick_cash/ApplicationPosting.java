package com.example.csci3130_w24_group20_quick_cash;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ApplicationPosting implements Serializable {
    private String jobID;
    private String applicantName;
    private String applicantUID;
    private String employerUID;
    private String jobTitle;
    private String applicantCountry;
    private String applicantCity;
    private String applicantAddress;

    private String applicantEmail;
    private String applicantAvailability;
    private String applicantEducation;
    private String applicantExperience;
    private String appOtherDetails;
    private String dateReceived;

    public ApplicationPosting(){}
    /**
     * Parameterized constructor for JobPosting.
     * @param applicantName The name of the employee applying.
     * @param applicantEmail The email of the applicant applying
     * @param applicantUID The unique ID of the employee applying.
     * @param applicantCountry The country where the applicant is currently located.
     * @param applicantCity The city where the applicant is currently located.
     * @param applicantAddress The address where the applicant is currently located.
     * @param applicantAvailability Details about when the applicant is able to start.
     * @param applicantEducation Education of the applicant.
     * @param applicantExperience Previous or current experience.
     * @param appOtherDetails Any other details relevant to the job requirements.
     */
    public ApplicationPosting(String applicantName, String applicantUID, String applicantEmail,
                             String applicantCountry, String applicantCity,
                      String applicantAddress, String applicantAvailability, String applicantEducation,
                      String applicantExperience, String appOtherDetails){
        this.jobID = getJobID();
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.applicantUID = applicantUID;
        this.employerUID = getEmployerUID();
        this.jobTitle = getJobTitle();
        this.applicantCountry = applicantCountry;
        this.applicantAvailability = applicantAvailability;
        this.applicantCity = applicantCity;
        this.applicantAddress = applicantAddress;
        this.applicantEducation = applicantEducation;
        this.applicantExperience = applicantExperience;
        this.appOtherDetails = appOtherDetails;
        this.dateReceived = setCurrentDate();
    }

    public String getJobID() {
        return jobID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantUID() {
        return applicantUID;
    }

    public String getEmployerUID() {
        return employerUID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getApplicantCountry() {
        return applicantCountry;
    }

    public String getApplicantCity() {
        return applicantCity;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public String getApplicantAvailability() {
        return applicantAvailability;
    }

    public String getApplicantEducation() {
        return applicantEducation;
    }

    public String getApplicantExperience() {
        return applicantExperience;
    }

    public String getAppOtherDetails() {
        return appOtherDetails;
    }

    public String getDateReceived() {
        return dateReceived;
    }


    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantUID(String applicantUID) {
        this.applicantUID = applicantUID;
    }

    public void setEmployerUID(String employerUID) {
        this.employerUID = employerUID;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setApplicantCountry(String applicantCountry) {
        this.applicantCountry = applicantCountry;
    }

    public void setApplicantCity(String applicantCity) {
        this.applicantCity = applicantCity;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public void setApplicantAvailability(String applicantAvailability) {
        this.applicantAvailability = applicantAvailability;
    }

    public void setApplicantEducation(String applicantEducation) {
        this.applicantEducation = applicantEducation;
    }

    public void setApplicantExperience(String applicantExperience) {
        this.applicantExperience = applicantExperience;
    }

    public void setAppOtherDetails(String appOtherDetails) {
        this.appOtherDetails = appOtherDetails;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }


    /**
     * Retrieves the current date.
     * @return The current date in the format "yyyy-MM-dd".
     */
    public String setCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        return dateFormat.format(new Date());
    }

}

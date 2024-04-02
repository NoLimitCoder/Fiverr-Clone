package com.example.csci3130_w24_group20_quick_cash;

import java.io.Serializable;

public class JobOffer implements Serializable {
    private String jobID;
    private String employerUID;
    private String applicantUID;
    private String jobTitle;
    private String employerName;
    private String applicantName;
    private String salary;
    private String startDate;
    private String otherTerms;
    private String isAccepted;
    private String isComplete;

    public JobOffer(){
        //Default Constructor
    }

    public JobOffer(ApplicationPosting appPosting, String salary, String startDate, String otherTerms) {
        this.jobID = appPosting.getJobID();
        this.employerUID = appPosting.getEmployerUID();
        this.applicantUID = appPosting.getApplicantUID();
        this.jobTitle = appPosting.getJobTitle();
        this.applicantName = appPosting.getApplicantName();
        this.salary = salary;
        this.startDate = startDate;
        this.otherTerms = otherTerms;
        this.isAccepted = "pending";
        this.isComplete = "pending";
    }

    public String getJobID() {
        return jobID;
    }

    public String getEmployerUID() {
        return employerUID;
    }

    public String getApplicantUID() {
        return applicantUID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getSalary() {
        return salary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getOtherTerms() {
        return otherTerms;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public String getIssComplete() {
        return isComplete;
    }

    public void setJobID(String jobID){
        this.jobID = jobID;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setOtherTerms(String otherTerms) {
        this.otherTerms = otherTerms;
    }

    public void setAccepted(String accepted) {
        isAccepted = accepted;
    }

    public void setComplete(String complete) {
        isComplete = complete;
    }
}

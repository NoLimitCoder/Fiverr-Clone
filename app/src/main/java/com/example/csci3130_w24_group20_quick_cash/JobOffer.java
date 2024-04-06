package com.example.csci3130_w24_group20_quick_cash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class JobOffer implements Serializable {
    private String jobID;
    private String employerUID;
    private String applicantUID;
    private String jobTitle;
    private String employerName;
    private String employeeName;
    private String salary;
    private String startDate;
    private String otherTerms;
    private String isAccepted;
    private String isComplete;
    private DatabaseReference databaseReference;

    public JobOffer(){
        //Default Constructor
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JobOffers");
    }

    public JobOffer(ApplicationPosting appPosting, String salary, String startDate, String otherTerms,
                    String employerName) {
        this.jobID = appPosting.getJobID();
        this.employerUID = appPosting.getEmployerUID();
        this.employerName = employerName;
        this.employeeName = appPosting.getApplicantName();
        this.applicantUID = appPosting.getApplicantUID();
        this.jobTitle = appPosting.getJobTitle();
        this.salary = salary;
        this.startDate = startDate;
        this.otherTerms = otherTerms;
        this.isAccepted = "pending";
        this.isComplete = "pending";
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("JobOffers");

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

    public String getEmployeeName() {
        return employeeName;
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

    public String getIsComplete() {
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
        databaseReference.child(jobID).child("isAccepted").setValue(isAccepted);
    }

    public void setComplete(String complete) {
        isComplete = complete;
        databaseReference.child(jobID).child("isComplete").setValue(isAccepted);
    }
}

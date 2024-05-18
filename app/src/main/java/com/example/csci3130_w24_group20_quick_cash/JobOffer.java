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

    public JobOffer(){
    }

    /**
     * Constructs a new JobOffer object.
     *
     * @param appPosting   The ApplicationPosting associated with the job offer.
     * @param salary       The salary offered for the job.
     * @param startDate    The start date of the job.
     * @param otherTerms   Other terms associated with the job offer.
     * @param employerName The name of the employer making the job offer.
     */

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

    /**
     * Sets the acceptance status of the job offer.
     *
     * @param accepted The acceptance status to set (e.g., "accepted", "declined").
     */

    public void setAccepted(String accepted) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JobOffers");
        isAccepted = accepted;
        databaseReference.child(jobID).child("isAccepted").setValue(isAccepted);
    }

    /**
     * Sets the completion status of the job offer.
     *
     * @param complete The completion status to set (e.g., "completed", "incomplete").
     */

    public void setComplete(String complete) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JobOffers");
        isComplete = complete;
        databaseReference.child(jobID).child("isComplete").setValue(isComplete);
    }
}

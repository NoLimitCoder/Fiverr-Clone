package com.example.csci3130_w24_group20_quick_cash;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class JobPosting implements Serializable {
    private String jobID;
    private String employerName;
    private String employerUID;
    private String jobTitle;
    private String jobCountry;
    private String jobCity;
    private String jobAddress;
    private String jobSalary;
    private String jobDescription;
    private String jobType;
    private String otherDetails;
    private String datePosted;

    public JobPosting(){}
    public JobPosting(String employerName, String employerUID, String jobTitle, String jobCountry, String jobCity,
                      String jobAddress, String jobSalary, String jobDescription,
                      String jobType, String otherDetails){
        this.jobID = generateJobID();
        this.employerName = employerName;
        this.employerUID = employerUID;
        this.jobTitle = jobTitle;
        this.jobCountry = jobCountry;
        this.jobCity = jobCity;
        this.jobAddress = jobAddress;
        this.jobSalary = jobSalary;
        this.jobDescription = jobDescription;
        this.jobType = jobType;
        this.otherDetails = otherDetails;
        this.datePosted = setCurrentDate();
    }

    public String getJobID(){
        return this.jobID;
    }
    public String getEmployerName(){
        return this.employerName;
    }
    public String getEmployerUID(){
        return this.employerUID;
    }
    public String getJobTitle(){
        return this.jobTitle;
    }

    public String getJobCountry(){return this.jobCountry;}

    public String getJobCity(){return this.jobCity;}
    public String getJobAddress(){
        return this.jobAddress;
    }
    public String getJobSalary(){
        return this.jobSalary;
    }
    public String getJobDescription(){
        return this.jobDescription;
    }
    public String getJobType(){
        return this.jobType;
    }
    public String getOtherDetails(){
        return this.otherDetails;
    }

    public void setJobID(String jobID){
        this.jobID = jobID;
    }
    public void setEmployerName(String employerName){
        this.employerName = employerName;
    }
    public void setEmployerID(String employerUID){
        this.employerUID = employerUID;
    }
    public void setJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
    }

    public void setJobCountry(String jobCountry){this.jobCountry = jobCountry;}

    public void setJobCity(String jobCity){this.jobCity = jobCity;}
    public void setJobAddress(String jobAddress){
        this.jobAddress = jobAddress;
    }

    public void setJobSalary(String jobSalary){
        this.jobSalary = jobSalary;
    }
    public void setJobDescription(String jobDescription){
        this.jobDescription = jobDescription;
    }
    public void setJobType(String jobType){
        this.jobType = jobType;
    }
    public void setOtherDetails(String otherDetails){
        this.otherDetails = otherDetails;
    }

    public String getDatePosted(){
        return datePosted;
    }

    private String setCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        return dateFormat.format(new Date());
    }

    private String generateJobID(){
        return UUID.randomUUID().toString();
    }

}

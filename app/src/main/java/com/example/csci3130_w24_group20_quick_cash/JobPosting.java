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
    /**
     * Parameterized constructor for JobPosting.
     * @param employerName The name of the employer.
     * @param employerUID The unique ID of the employer.
     * @param jobTitle The title of the job.
     * @param jobCountry The country where the job is located.
     * @param jobCity The city where the job is located.
     * @param jobAddress The address of the job location.
     * @param jobSalary The salary offered for the job.
     * @param jobDescription The description of the job.
     * @param jobType The type of the job.
     * @param otherDetails Any other details related to the job.
     */
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

    /**
     * Sets the name of the employer associated with the job posting.
     *
     * @param employerName The name of the employer to be set.
     */
    public void setEmployerName(String employerName){
        this.employerName = employerName;
    }

    public String getDatePosted(){
        return datePosted;
    }

    /**
     * Retrieves the current date.
     * @return The current date in the format "yyyy-MM-dd".
     */
    public String setCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        return dateFormat.format(new Date());
    }

    /**
     * Generates a unique job ID using UUID.
     * @return The generated job ID.
     */
    private String generateJobID(){
        return UUID.randomUUID().toString();
    }

}

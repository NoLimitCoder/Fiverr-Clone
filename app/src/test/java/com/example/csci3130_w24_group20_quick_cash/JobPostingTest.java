package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.*;

import org.junit.Test;

public class JobPostingTest {

    @Test
    public void testGenerateJobPostingID(){
        JobPosting jobPosting = new JobPosting("Test Employer", "3535", "Software Developer", "6059 Shirley Street", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school");
        assertNotNull(jobPosting.getJobID());
    }

    @Test
    public void testSetAndGetMethods(){
        JobPosting jobPosting = new JobPosting("Test", "3535", "Software Developer", "6059 Shirley Street", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school");
        jobPosting.setEmployerName("Ammar");
        assertEquals("Ammar", jobPosting.getEmployerName());
    }

    @Test
    public void testDatePosted(){
        JobPosting jobPosting = new JobPosting("Test Employer", "3535", "Software Developer", "6059 Shirley Street", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school");
        assertNotNull(jobPosting.getDatePosted());
    }

}

package com.example.csci3130_w24_group20_quick_cash;

import java.util.ArrayList;
import java.util.List;

public class MockJobPostingRepo {

    private static MockJobPostingRepo instance = new MockJobPostingRepo();
    private List<JobPosting> JobPostings = new ArrayList<>();

    private MockJobPostingRepo(){

        JobPostings.add(new JobPosting("Ammar Zain", "3535", "Software Developer", "Canada", "Halifax","6056 Shirley Street", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school"));
        JobPostings.add(new JobPosting("Ammar Zain", "3535", "Software Developer", "Canada", "Halifax","6059 Shirley Street", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school"));
        JobPostings.add(new JobPosting("Riley Devitt", "3535", "Software Developer", "Canada", "Halifax","6299 South St", "5000 dollars", "I need a coder", "Computer Science", "Must complete 3 years at school"));

    }

    public static MockJobPostingRepo getInstance(){
        return instance;
    }

    public List<JobPosting> getJobPostings() {
        return JobPostings;
    }
}

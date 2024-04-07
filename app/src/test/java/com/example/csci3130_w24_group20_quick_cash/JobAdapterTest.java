package com.example.csci3130_w24_group20_quick_cash;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JobAdapterTest {

    private List<JobPosting> jobList;

    @Before
    public void setUp() {
        jobList = new ArrayList<>();
        jobList.add(new JobPosting("Employer 1", "123", "Job 1", "Canada", "Toronto", "123 Street",
                "$50,000", "Description 1", "Full-time", "Other details 1"));
        jobList.add(new JobPosting("Employer 2", "456", "Job 2", "USA", "New York", "456 Street",
                "$60,000", "Description 2", "Part-time", "Other details 2"));
    }

    @Test
    public void testAdapterItemCount() {
         jobList = new ArrayList<>();
        JobAdapter adapter = new JobAdapter(jobList, null);
        assertEquals(0, adapter.getItemCount());
        assertNotNull(jobList);
    }

    @Test
    public void testAdapterMultipleItemCount() {
        jobList.add(new JobPosting("Employer 1", "123", "Job 1", "Canada", "Toronto", "123 Street",
                "$50,000", "Description 1", "Full-time", "Other details 1"));
        jobList.add(new JobPosting("Employer 2", "456", "Job 2", "USA", "New York", "456 Street",
                "$60,000", "Description 2", "Part-time", "Other details 2"));
        JobAdapter MultipleListadapter = new JobAdapter(jobList, null);
        assertEquals(jobList.size(), MultipleListadapter.getItemCount());
        assertNotNull(jobList);
    }
}


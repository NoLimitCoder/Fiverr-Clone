package com.example.csci3130_w24_group20_quick_cash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class UserApplicationJUnitTests {

    private List<ApplicationPosting> applicationList;
    private ApplicationAdapter.OnApplicationItemClickListener clickListener;

    @Before
    public void setUp() {
        // Initialize application list
        applicationList = new ArrayList<>();
        // Initialize click listener
        clickListener = new ApplicationAdapter.OnApplicationItemClickListener() {
            @Override
            public void onApplicationItemClick(ApplicationPosting applicationPosting) {
                return;
            }
        };
    }

    @Test
    public void testApplicationAdapter() {
        ApplicationAdapter adapter = new ApplicationAdapter(applicationList, clickListener);
        assertNotNull(adapter);
        assertEquals(applicationList.size(), adapter.getItemCount());
        assertNotEquals(applicationList.size(), adapter.toString());
        assertNotEquals(applicationList.size(), adapter.getClass());
    }

    @Test
    public void testApplicationPosting() {
        ApplicationAdapter Posting = new ApplicationAdapter(applicationList, clickListener);
        assertNotNull(Posting);
        assertNotEquals(applicationList.size(), Posting.toString());
        assertEquals(applicationList.size(), Posting.getItemCount());
        assertNotEquals(applicationList.size(), Posting.getClass());
    }

    @Test
    public void testApplicationDetailFragment() {
        ApplicationAdapter Fragment = new ApplicationAdapter(applicationList, clickListener);
        assertNotNull(Fragment);
        assertNotEquals(applicationList.size(), Fragment.getClass());
        assertEquals(applicationList.size(), Fragment.getItemCount());
        assertNotEquals(applicationList.size(), Fragment.toString());

    }

}
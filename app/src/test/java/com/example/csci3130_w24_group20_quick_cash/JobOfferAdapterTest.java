package com.example.csci3130_w24_group20_quick_cash;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JobOfferAdapterTest {

    private List<JobOffer> jobOffers;

    @Before
    public void setUp() {
        jobOffers = new ArrayList<>();
        jobOffers.add(new JobOffer());
        jobOffers.add(new JobOffer());
    }

    @Test
    public void testAdapterItemCount() {
        JobOfferAdapter adapter = new JobOfferAdapter(jobOffers, null);
        assertEquals(jobOffers.size(), adapter.getItemCount());
    }


    @Test
    public void testAdapterMethodsCount() {
        JobOfferAdapter adapterMethod = new JobOfferAdapter(jobOffers, null);
        assertEquals(jobOffers.size(), adapterMethod.getItemCount());
        assertNotNull(adapterMethod);
    }
}


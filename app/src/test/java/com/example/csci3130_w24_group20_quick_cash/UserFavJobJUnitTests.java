package com.example.csci3130_w24_group20_quick_cash;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserFavJobJUnitTests {

    private List<String> favoriteEmployeesList;

    @Before
    public void setUp() {
        favoriteEmployeesList = new ArrayList<>();
        favoriteEmployeesList.add("John Doe");
        favoriteEmployeesList.add("Jane Smith");
    }

    @Test
    public void testAdapterItemCount() {
        FavoriteEmployeesAdapter adapter = new FavoriteEmployeesAdapter(favoriteEmployeesList);
        assertEquals(favoriteEmployeesList.size(), adapter.getItemCount());
    }

    @Test
    public void testViewHolderBind() {
        FavoriteEmployeesAdapter adapter = new FavoriteEmployeesAdapter(favoriteEmployeesList);
        assertNotNull(adapter);
    }
}

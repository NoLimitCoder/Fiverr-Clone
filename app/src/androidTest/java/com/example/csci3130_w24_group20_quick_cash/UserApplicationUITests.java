package com.example.csci3130_w24_group20_quick_cash;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserApplicationUITests {

    public String launcherPackage = "ca.dal.cs.csci3130.w24_group20_quick_cash";
    public int TIME_OUT = 5000;
    UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIME_OUT);
    }

    @Test
    public void testApplicationSearch() throws UiObjectNotFoundException {
        UiObject roleSpinner = device.findObject(new UiSelector().text("Select a role"));
        roleSpinner.click();
        List<UiObject2> roles = device.findObjects(By.res("android:id/text1"));
        roles.get(1).click();
        UiObject enterButton = device.findObject(new UiSelector().text("ENTER"));
        enterButton.click();
        UiObject orderButton = device.findObject(new UiSelector().text("PLACE ORDER"));
        orderButton.clickAndWaitForNewWindow();
        UiObject categorySpinner = device.findObject(new UiSelector().text("Select a category"));
        categorySpinner.click();
        List<UiObject2> categories = device.findObjects(By.res("android:id/text1"));
        categories.get(2).click();
        UiObject searchButton = device.findObject(new UiSelector().text("SEARCH"));
        searchButton.click();
        UiScrollable recyclerView = new UiScrollable(new UiSelector().scrollable(false));
        assertTrue(recyclerView.exists());
        recyclerView.scrollIntoView(new UiSelector().text("Chicken Kebab"));
        UiObject kebab = device.findObject(new UiSelector().text("Chicken Kebab"));
        assertTrue(kebab.exists());
    }

    @Test
    public void testApplicationItemDetails() throws UiObjectNotFoundException {
        UiObject roleSpinner = device.findObject(new UiSelector().text("Select a role"));
        roleSpinner.click();
        List<UiObject2> roles = device.findObjects(By.res("android:id/text1"));
        roles.get(1).click();
        UiObject enterButton = device.findObject(new UiSelector().text("ENTER"));
        enterButton.click();
        UiObject orderButton = device.findObject(new UiSelector().text("PLACE ORDER"));
        orderButton.clickAndWaitForNewWindow();
        UiObject categorySpinner = device.findObject(new UiSelector().text("Select a category"));
        categorySpinner.click();
        List<UiObject2> categories = device.findObjects(By.res("android:id/text1"));
        categories.get(2).click();
        UiObject searchButton = device.findObject(new UiSelector().text("SEARCH"));
        searchButton.click();
        UiScrollable recyclerView = new UiScrollable(new UiSelector().scrollable(false));
        assertTrue(recyclerView.exists());
        recyclerView.scrollIntoView(new UiSelector().text("Chicken Kebab"));
        UiObject kebab = device.findObject(new UiSelector().text("Chicken Kebab"));
        kebab.clickAndWaitForNewWindow();
        UiObject perishable = device.findObject(new UiSelector().text("Perishable"));
        assertTrue(perishable.exists());
    }
}

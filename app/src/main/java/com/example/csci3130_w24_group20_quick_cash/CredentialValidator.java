package com.example.csci3130_w24_group20_quick_cash;

import android.location.Address;
import android.location.Geocoder;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Pattern;

public class CredentialValidator {
    protected boolean isEmptyUserName(String userName) {
        return userName.isEmpty();
    }

    protected boolean isValidAddress(Context context, String country, String city, String address){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> validAddress = geocoder.getFromLocationName(address + ", " + city + ", " + country, 1);
            return validAddress != null && !validAddress.isEmpty();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    protected boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z]{2,}(?: [a-zA-Z]+){0,2}$", name);
    }

    protected boolean isValidEmailAddress(String emailAddress) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailAddress);
    }

    protected boolean isValidPassword(String password) {
        return Pattern.matches("^.{8,}$", password);
    }

    protected boolean isValidContactNumber(String contactNumber){
        return Pattern.matches("^\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$", contactNumber);

    }

    protected boolean isValidRole(String role) {
        return Pattern.matches("Employee|Employer", role);
    }
}

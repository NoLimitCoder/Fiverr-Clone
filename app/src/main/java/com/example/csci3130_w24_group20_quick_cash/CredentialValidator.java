package com.example.csci3130_w24_group20_quick_cash;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Class for validating user credentials and job information.
 */
public class CredentialValidator {

    /**
     * Checks if the provided username is empty.
     *
     * @param userName The username to be checked.
     * @return True if the username is empty, otherwise false.
     */
    protected boolean isEmptyUserName(String userName) {
        return userName.isEmpty();
    }

    /**
     * Validates the provided address using Geocoder.
     *
     * @param context The context of the application.
     * @param country The country of the address.
     * @param city The city of the address.
     * @param address The address to be validated.
     * @return True if the address is valid, otherwise false.
     */
    public boolean isValidAddress(Context context, String country, String city, String address) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> validAddress = geocoder.getFromLocationName(address + ", " + city + ", " + country, 1);
            return validAddress != null && !validAddress.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if all job fields are filled out.
     *
     * @param fields The job fields to be checked.
     * @return True if all job fields are filled out, otherwise false.
     */
    public boolean areFieldsFilled(String... fields){
        for (String field : fields){
            if(field.trim().isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the provided password is empty.
     *
     * @param password The password to be checked.
     * @return True if the password is empty, otherwise false.
     */
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    /**
     * Validates the provided name.
     *
     * @param name The name to be validated.
     * @return True if the name is valid, otherwise false.
     */
    public boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z]{2,}(?: [a-zA-Z]+){0,2}$", name);
    }

    /**
     * Validates the provided email address.
     *
     * @param emailAddress The email address to be validated.
     * @return True if the email address is valid, otherwise false.
     */
    public boolean isValidEmailAddress(String emailAddress) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailAddress);
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, otherwise false.
     */
    protected boolean isValidPassword(String password) {
        return Pattern.matches("^.{8,}$", password);
    }

    /**
     * Validates the provided contact number.
     *
     * @param contactNumber The contact number to be validated.
     * @return True if the contact number is valid, otherwise false.
     */
    protected boolean isValidContactNumber(String contactNumber){
        return Pattern.matches("^\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$", contactNumber);
    }

    /**
     * Validates the provided role.
     *
     * @param role The role to be validated.
     * @return True if the role is valid, otherwise false.
     */
    protected boolean isValidRole(String role) {
        return Pattern.matches("Employee|Employer", role);
    }
}

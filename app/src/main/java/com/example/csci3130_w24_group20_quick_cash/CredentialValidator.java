package com.example.csci3130_w24_group20_quick_cash;

import java.util.regex.Pattern;

public class CredentialValidator {

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
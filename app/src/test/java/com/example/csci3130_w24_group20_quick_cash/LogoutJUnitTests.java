package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LogoutJUnitTests {
    CredentialValidator validator;

    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void testLogout() {
        assertTrue(validator.isEmptyUserName(""));

    }
}

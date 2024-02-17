package com.example.csci3130_w24_group20_quick_cash;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthSingleton {
    private static FirebaseAuth mAuthInstance;

    // Private constructor to prevent instantiation
    private FirebaseAuthSingleton() {}

    public static synchronized FirebaseAuth getInstance() {
        if (mAuthInstance == null) {
            mAuthInstance = FirebaseAuth.getInstance();
        }
        return mAuthInstance;
    }
}
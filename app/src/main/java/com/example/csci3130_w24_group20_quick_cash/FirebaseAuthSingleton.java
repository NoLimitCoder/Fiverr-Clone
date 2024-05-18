package com.example.csci3130_w24_group20_quick_cash;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Singleton class for obtaining a FirebaseAuth instance.
 */
public class FirebaseAuthSingleton {
    private static FirebaseAuth mAuthInstance;

    // Private constructor to prevent instantiation
    private FirebaseAuthSingleton() {}

    /**
     * Returns a synchronized instance of FirebaseAuth.
     *
     * @return The FirebaseAuth instance.
     */
    public static synchronized FirebaseAuth getInstance() {
        if (mAuthInstance == null) {
            mAuthInstance = FirebaseAuth.getInstance();
        }
        return mAuthInstance;
    }
}

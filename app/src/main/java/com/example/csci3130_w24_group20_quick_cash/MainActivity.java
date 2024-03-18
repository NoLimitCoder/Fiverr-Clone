/**
 * This class represents the main activity of the application.
 * It handles user login, registration, and password recovery functionalities.
 */

package com.example.csci3130_w24_group20_quick_cash;

import static android.content.ContentValues.TAG;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.BaseEmployeeActivity;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.BaseEmployerActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth mAuth;
    protected FirebaseDatabase database = null;
    protected FirebaseCRUD crud = null;

    /**
     * Called when the activity is starting.
     * Sets up the layout and initializes Firebase authentication and database access.
     * Also sets up click listeners for login, sign-up, and forgot password buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupLoginButton();
        this.setupSignUpButton();
        this.setupForgotButton();
        this.initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
    }


    /**
     * Called when the activity is about to start.
     * Checks if the user is already signed in, if yes, redirects to the appropriate activity.
     */

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            move2WelcomeWindow();
        }
    }

    /**
     * Initializes Firebase database access.
     */
    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }

    /**
     * Sets up the click listener for the login button.
     */
    protected void setupLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    /**
     * Sets up the click listener for the sign-up button.
     */
    protected void setupSignUpButton() {
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
    }

    /**
     * Sets up the click listener for the forgot password button.
     */
    protected void setupForgotButton() {
        Button forgotPassButton = findViewById(R.id.forgotPassButton);
        forgotPassButton.setOnClickListener(this);
    }

    /**
     * Redirects the user to the welcome window based on their role.
     */
    protected void move2WelcomeWindow() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uID = currentUser.getUid();
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(uID);
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);

                    if (role.equals("Employee")) {
                        Intent intent = new Intent(getBaseContext(), BaseEmployeeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "employer:success");
                        Intent intent = new Intent(getBaseContext(), BaseEmployerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled logic
            }
        });
    }

    /**
     * Redirects the user to the registration window.
     */
    protected void move2RegistrationWindow() {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Redirects the user to the forgot password window.
     */
    protected void move2forgotPass() {
        Intent intent = new Intent(getBaseContext(), ForgotPasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Displays a status message using Snackbar.
     *
     * @param v       The view to display the Snackbar in.
     * @param message The message to display.
     */
    protected void setStatusMessage(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    interface AuthCallback {
        void onResult(boolean success);
    }

    /**
     * Authenticates the user using Firebase authentication.
     *
     * @param userName The username (email) of the user.
     * @param password The password of the user.
     * @param callback The callback to be invoked with authentication result.
     */
    protected void UserExists(final String userName, final String password, final AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        callback.onResult(true);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        callback.onResult(false);
                    }
                });
    }

    /**
     * Retrieves the username (email) entered by the user.
     *
     * @return The username entered by the user.
     */
    protected String getUserName() {
        EditText netIDBox = findViewById(R.id.email);
        return netIDBox.getText().toString().trim();
    }

    /**
     * Retrieves the password entered by the user.
     *
     * @return The password entered by the user.
     */
    protected String getPassword() {
        EditText netIDBox = findViewById(R.id.password);
        return netIDBox.getText().toString().trim();
    }

    /**
     * Handles click events for various buttons.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            String userName = getUserName();
            String psswd = getPassword();
            String errorMessage = new String();
            CredentialValidator validator = new CredentialValidator();
            boolean userExists = false;
            if (validator.isEmptyUserName(userName)) {
                errorMessage = "Error: " + getString(R.string.EMPTY_USER_NAME);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            } else if (validator.isEmptyPassword(psswd)) {
                errorMessage = "Error: " + getString(R.string.EMPTY_PASSWORD);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                UserExists(userName, psswd, new AuthCallback() {
                    @Override
                    public void onResult(boolean success) {
                        // Use the result here
                        if (success) {
                            move2WelcomeWindow();
                        }
                    }
                });
            }
        } else if (v.getId() == R.id.signUpButton) {
            // Handle sign up button click
            // You can add code to navigate to the sign-up activity or perform other actions here
            move2RegistrationWindow();

        } else if (v.getId() == R.id.forgotPassButton) {
            move2forgotPass();
        }
    }

    /**
     * Hides the soft keyboard when touching outside of EditText.
     *
     * @param event The MotionEvent being dispatched.
     * @return True to consume the event here, false to allow it to continue on to other views.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

}
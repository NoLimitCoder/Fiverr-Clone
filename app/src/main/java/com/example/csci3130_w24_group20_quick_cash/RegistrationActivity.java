/**
 * This class represents the registration activity of the application.
 * It allows users to register with their name, email, password, contact number, and role.
 * Upon successful registration, the user information is stored in Firebase.
 */
package com.example.csci3130_w24_group20_quick_cash;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    // Firebase database instance
    FirebaseDatabase database = null;
    // Firebase CRUD operations
    FirebaseCRUD crud = null;

    /**
     * Called when the activity is starting.
     * Sets up the layout, loads role spinner, and initializes database access.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.loadRoleSpinner();
        this.setupRegistrationButton();
        this.initializeDatabaseAccess();
    }

    /**
     * Loads role spinner with predefined roles.
     */
    protected void loadRoleSpinner() {
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        List<String> roles = new ArrayList<>();
        roles.add("Select your role");
        roles.add("Employee");
        roles.add("Employer");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, roles);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * Sets up registration button click listener.
     */
    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }

    /**
     * Initializes database access.
     */
    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }

    /**
     * Retrieves the name entered by the user.
     * @return The name entered by the user.
     */
    protected String getName() {
        EditText nameBox = findViewById(R.id.performanceReview);
        return nameBox.getText().toString().trim();
    }

    /**
     * Retrieves the email address entered by the user.
     * @return The email address entered by the user.
     */
    protected String getEmailAddress() {
        EditText emailBox = findViewById(R.id.editTextEmail);
        return emailBox.getText().toString().trim();
    }

    /**
     * Retrieves the password entered by the user.
     * @return The password entered by the user.
     */
    protected String getPassword() {
        EditText passwordBox = findViewById(R.id.editTextPassword);
        return passwordBox.getText().toString().trim();
    }

    /**
     * Retrieves the contact number entered by the user.
     * @return The contact number entered by the user.
     */
    protected String getContactNumber() {
        EditText contactNumber = findViewById(R.id.editTextNumber);
        return contactNumber.getText().toString().trim();
    }

    /**
     * Retrieves the role selected by the user.
     * @return The role selected by the user.
     */
    protected String getRole() {
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        return roleSpinner.getSelectedItem().toString().trim();
    }

    /**
     * Sets status message to the given message.
     * @param message The message to set as the status message.
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    /**
     * Saves user information to Firebase database.
     * @param name The name of the user.
     * @param emailAddress The email address of the user.
     * @param password The password of the user.
     * @param contactNumber The contact number of the user.
     * @param role The role of the user.
     */
    protected void saveInfoToFirebase(String name, String emailAddress, String password, String contactNumber, String role) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    if (user != null) {
                        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

                        dbr.child("name").setValue(name);
                        dbr.child("emailAddress").setValue(emailAddress);
                        dbr.child("password").setValue(password);
                        dbr.child("contactNumber").setValue(contactNumber);
                        dbr.child("role").setValue(role);

                        sendEmailVerification(user);

                    }
                }
            }
        });

    }

    /**
     * Handles touch event on the screen to hide the soft keyboard when touched outside the EditText.
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
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
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    /**
     * Sends email verification to the user.
     * @param user The Firebase user.
     */
    protected void sendEmailVerification(FirebaseUser user){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegistrationActivity.this, "Verification email has been sent", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Handles click events for the registration button.
     * Validates user input and registers the user if input is valid.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        String name = getName();
        String emailAddress = getEmailAddress();
        String password = getPassword();
        String contactNumber = getContactNumber();
        String role = getRole();
        String errorMessage = new String();
        CredentialValidator validator = new CredentialValidator();
        if (!validator.isValidName(name)) {
            errorMessage = getResources().getString(R.string.INVALID_NAME).trim();
        } else if (!validator.isValidEmailAddress(emailAddress)) {
            errorMessage = getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
        } else if (!validator.isValidPassword(password)) {
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
        } else if (!validator.isValidContactNumber(contactNumber)){
            errorMessage = getResources().getString(R.string.INVALID_NUMBER).trim();
        } else if (!validator.isValidRole(role)) {
            errorMessage = getResources().getString(R.string.INVALID_ROLE).trim();
        }
        setStatusMessage(errorMessage);
        if (errorMessage.isEmpty()) {
            saveInfoToFirebase(name, emailAddress, password, contactNumber, role);
        }
    }
}

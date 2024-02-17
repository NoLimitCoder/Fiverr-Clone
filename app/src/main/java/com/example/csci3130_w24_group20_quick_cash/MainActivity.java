package com.example.csci3130_w24_group20_quick_cash;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected FirebaseAuth mAuth;
    protected FirebaseDatabase database = null;
    protected FirebaseCRUD crud = null;

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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }
    protected void setupLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    protected void setupSignUpButton() {
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
    }
    protected void setupForgotButton() {
        Button forgotPassButton = findViewById(R.id.forgotPassButton);
        forgotPassButton.setOnClickListener(this);
    }
    protected void setupLogoutButton() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);
    }

    protected void move2WelcomeWindow() {
        Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
        startActivity(intent);
    }

    protected void move2RegistrationWindow() {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivity(intent);
    }
    protected void move2forgotPass() {
        Intent intent = new Intent(getBaseContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }
    protected void setStatusMessage(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    interface AuthCallback {
        void onResult(boolean success);
    }

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

    protected String getUserName() {
        EditText netIDBox = findViewById(R.id.email);
        return netIDBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText netIDBox = findViewById(R.id.password);
        return netIDBox.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            String userName = getUserName();
            String psswd = getPassword();
            String errorMessage = new String();
            CredentialValidator validator = new CredentialValidator();

            if (validator.isEmptyUserName(userName)) {
                errorMessage = "Error: " + getString(R.string.EMPTY_USER_NAME);
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
            //setStatusMessage(v, errorMessage.trim());
        }
        else if (v.getId() == R.id.signUpButton) {
            // Handle sign up button click
            // You can add code to navigate to the sign-up activity or perform other actions here
            move2RegistrationWindow();

        }
        else if(v.getId() == R.id.forgotPassButton){
            move2forgotPass();
        }
    }

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


}
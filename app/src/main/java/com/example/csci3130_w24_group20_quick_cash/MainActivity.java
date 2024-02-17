package com.example.csci3130_w24_group20_quick_cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected String getUserName() {
        EditText netIDBox = findViewById(R.id.userName);
        return netIDBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText emailBox = findViewById(R.id.password);
        return emailBox.getText().toString().trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupLoginButton();
        this.setupForgotPassButton();

        Button loginButton = findViewById(R.id.loginButton);
        Button forgotPassButton = findViewById(R.id.forgotPassButton);

        loginButton.setOnClickListener(this);
        forgotPassButton.setOnClickListener(this);
    }


    protected void setupLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    protected void setupForgotPassButton() {
        Button loginButton = findViewById(R.id.forgotPassButton);
        loginButton.setOnClickListener(this);
    }

    protected void move2WelcomeWindow() {
        Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
        startActivity(intent);
    }

    protected void move2ForgotPassword(){
        Intent intent = new Intent(getBaseContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }

    protected void setStatusMessage(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.forgotPassButton) {
            move2ForgotPassword();
        }
        if(v.getId() == R.id.loginButton){
            String userName = getUserName();
            String errorMessage;
            CredentialValidator validator = new CredentialValidator();

            if (validator.isEmptyUserName(userName)) {
                errorMessage = "Error: " + getString(R.string.EMPTY_USER_NAME);
            } else {
                move2WelcomeWindow();
                return; // Early return since no error
            }
            setStatusMessage(v, errorMessage.trim());
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

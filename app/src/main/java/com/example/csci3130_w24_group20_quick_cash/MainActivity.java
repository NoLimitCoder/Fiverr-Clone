package com.example.csci3130_w24_group20_quick_cash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = null;
    FirebaseCRUD crud = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loadRoleSpinner();
        this.setupRegistrationButton();
        this.initializeDatabaseAccess();
    }

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

    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }

    protected String getName() {
        EditText nameBox = findViewById(R.id.nameBox);
        return nameBox.getText().toString().trim();
    }

    protected String getEmailAddress() {
        EditText emailBox = findViewById(R.id.emailBox);
        return emailBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText passwordBox = findViewById(R.id.passwordbox);
        return passwordBox.getText().toString().trim();
    }

    protected String getContactNumber() {
        EditText contactNumber = findViewById(R.id.numberbox);
        return contactNumber.getText().toString().trim();
    }

    protected String getRole() {
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        return roleSpinner.getSelectedItem().toString().trim();
    }


    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }


    protected void saveInfoToFirebase(String name, String emailAddress, String password, String contactNumber, String role) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("User Information").child(emailAddress);

        dbr.child("name").setValue(name);
        dbr.child("emailAddress").setValue(emailAddress);
        dbr.child("password").setValue(password);
        dbr.child("contact number").setValue(contactNumber);
        dbr.child("role").setValue(role);

    }

    @Override
    public void onClick(View view) {
        String name = getName();
        String emailAddress = getEmailAddress();
        String password = getPassword();
        String contactNumber = getContactNumber();
        String role = getRole();
        saveInfoToFirebase(name, emailAddress, password, contactNumber, role);
    }
}

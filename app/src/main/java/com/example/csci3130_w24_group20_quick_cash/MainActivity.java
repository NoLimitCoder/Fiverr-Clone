package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import android.view.View;
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
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    if (user != null) {
                        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(name);

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

    protected void sendEmailVerification(FirebaseUser user){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Verification email has been sent", Toast.LENGTH_LONG).show();
            }
        });

    }

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

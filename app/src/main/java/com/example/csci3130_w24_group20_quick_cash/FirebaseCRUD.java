package com.example.csci3130_w24_group20_quick_cash;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CompletionException;

/**
 * Class for performing CRUD operations on Firebase Realtime Database.
 */

public class FirebaseCRUD {
    private FirebaseDatabase database;
    private DatabaseReference nameRef = null;
    private DatabaseReference emailRef = null;
    private DatabaseReference passwordRef = null;
    private DatabaseReference contactNumberRef = null;
    private DatabaseReference roleRef = null;
    private String extractedName;
    private String extractedEmailAddress;

    private String extractedPassword;

    private String extractedContactNumber;
    private String extractedRole;

    /**
     * Constructor for FirebaseCRUD.
     *
     * @param database The FirebaseDatabase instance.
     */

    public FirebaseCRUD(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeDatabaseRefListeners();
    }

    protected DatabaseReference getNameRef() {
        return this.database.getReference("name");
    }

    protected DatabaseReference getEmailRef() {
        return this.database.getReference("email");
    }

    protected DatabaseReference getPasswordRef() {
        return this.database.getReference("password");
    }

    protected DatabaseReference getContactNumberRef() {return this.database.getReference("contact number");}

    protected DatabaseReference getRoleRef() {
        return this.database.getReference("role");
    }

    protected void setName(String name) {
        this.nameRef.setValue(name);
    }

    protected void setEmail(String email) {
        this.emailRef.setValue(email);
    }

    protected void setPassword(String password) {
        this.passwordRef.setValue(password);
    }

    protected void setContactNumber(String contactNumber) {this.contactNumberRef.setValue(contactNumber);}

    protected void setRole(String role) {
        this.roleRef.setValue(role);
    }

    protected void setNameListener() {
        this.nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    extractedName = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    //no onCancelled changes
            }
        });
    }

    protected void setEmailListener() {
        this.emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    extractedEmailAddress = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled changes
            }
        });
    }

    protected void setPasswordListener() {
        this.passwordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    extractedPassword = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled changes
            }
        });
    }

    protected void setContactNumberListener() {
        this.contactNumberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    extractedContactNumber = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled changes
            }
        });
    }
    protected void setRoleListener() {
        this.roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    extractedRole = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled changes
            }
        });
    }

    protected void initializeDatabaseRefs() {
        this.nameRef = getNameRef();
        this.emailRef = getEmailRef();
        this.passwordRef = getPasswordRef();
        this.contactNumberRef = getContactNumberRef();
        this.roleRef = getRoleRef();
    }

    protected void initializeDatabaseRefListeners() {
        this.setNameListener();
        this.setEmailListener();
        this.setPasswordListener();
        this.setContactNumberListener();
        this.setRoleListener();
    }

}


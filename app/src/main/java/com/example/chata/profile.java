package com.example.chata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class profile extends AppCompatActivity {



    private Button updateAccountSetting;
     private EditText updateName, updateStatus;
     private String currentUserId;

     private Toolbar profileToolbar;

     private FirebaseAuth mAuth;

    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitialiseFields();

        updateName.setVisibility(View.INVISIBLE);

        updateAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateSettings();

            }
        });

        RetrieveUserInfo();

    }


    private void UpdateSettings() {

        String setUser = updateName.getText().toString();
        String setStatus = updateStatus.getText().toString();


        if (TextUtils.isEmpty(setUser))
        {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(setStatus))
        {
            Toast.makeText(this, "Please enter your status", Toast.LENGTH_SHORT).show();
        }

        else{
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserId);
            profileMap.put("name", setUser);
            profileMap.put("status", setStatus);

            RootRef.child("Users").child(currentUserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                   if (task.isSuccessful())
                   {
                       SendUserToMainActivity();
                       Toast.makeText(profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       String message =  task.getException().toString();
                       Toast.makeText(profile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                   }

                }
            });

        }
    }


    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(profile.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }

    private void InitialiseFields() {

        updateAccountSetting = findViewById(R.id.update_button);
        updateName = findViewById(R.id.setUsername);
        updateStatus = findViewById(R.id.setStatus);

        profileToolbar = findViewById(R.id.profile_toolbar);

        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Account Settings");


    }

    private void RetrieveUserInfo() {

            RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if((dataSnapshot.exists()) && (dataSnapshot.hasChild("name"))){

                        String retrieveUsername = dataSnapshot.child("name").getValue().toString();
                        String retrieveStatus = dataSnapshot.child("status").getValue().toString();

                        updateName.setText(retrieveUsername);
                        updateStatus.setText(retrieveStatus);

                    }
                    else {
                        updateName.setVisibility(View.VISIBLE);
                        Toast.makeText(profile.this, "Please set & update your profile info.", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}

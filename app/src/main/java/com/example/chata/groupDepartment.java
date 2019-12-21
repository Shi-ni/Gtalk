package com.example.chata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class groupDepartment extends AppCompatActivity {


    private Button cseButton, mechanicalButton, ecButton, electButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_department);

        InitialiseFields();

        cseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToProfileActivity();
            }
        });
        mechanicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToProfileActivity();
            }
        });
        ecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToProfileActivity();
            }
        });
        electButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToProfileActivity();
            }
        });

    }


    private void InitialiseFields() {


        cseButton = (Button) findViewById(R.id.cse_button);
        mechanicalButton = (Button) findViewById(R.id.mech_button);
        ecButton = (Button) findViewById(R.id.ec_button);
        electButton = (Button) findViewById(R.id.elect_button);

    }



    private void SendUserToProfileActivity() {

        Intent mainIntent = new Intent(groupDepartment.this, profile.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }

}

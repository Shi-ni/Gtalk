package com.example.chata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText entered_email;
    private Button forget_button;
    private Toolbar forget_toolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        entered_email  = findViewById(R.id.enter_email);
        forget_button = findViewById(R.id.forget_button_link);
        forget_toolbar = findViewById(R.id.forgot_password_app_bar_layout);
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(forget_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Reset Password");

        forget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = entered_email.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter your registered email.", Toast.LENGTH_SHORT).show();
                }
                else {

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPasswordActivity.this, "Please visit your email to reset your password.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            }
                            else {
                                String message =  task.getException().getMessage();
                                Toast.makeText(ForgetPasswordActivity.this, "Error:" +message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }


}

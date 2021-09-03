package com.example.chat_settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class Biometric extends AppCompatActivity {

    //UI views

    Button btnAuth;
    TextView tvAuthstatus;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);

        btnAuth=findViewById(R.id.btnAuth);
        tvAuthstatus=findViewById(R.id.tvAuthstatus);

        //initialize the  values
        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(Biometric.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //if any error come while authentication
                tvAuthstatus.setText("Error:"+errString);
                res=0;
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Authentication Succeded
                tvAuthstatus.setText("Authentication Success");
                setContentView(R.layout.activity_main);
                res=1;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //failed to auth
                tvAuthstatus.setText("Authentication Failed");
                res=0;
            }
        });


        //setup title, description on authentication dialog
        promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using Fingerprint or FaceId ")
                .setNegativeButtonText("Cancel")
                .build();

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show authentication dialog
                biometricPrompt.authenticate(promptInfo);

            }
        });

    }
}
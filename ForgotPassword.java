package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText forgotpass_enteremail;
    Button RecoverAccount;
    String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_forgot_password );

        auth=FirebaseAuth.getInstance();
        forgotpass_enteremail=findViewById(R.id.Forgot_pass_email);
        RecoverAccount=findViewById(R.id.Recover_Account_btn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();

        RecoverAccount.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                ValidateData();
            }
        } );
    }

    private void ValidateData ( ) {
       email =  forgotpass_enteremail.getText().toString();
       if (email.isEmpty()){
           forgotpass_enteremail.setError("Required");
       }else {
           ForgotPass();
       }
    }

    private void ForgotPass ( ) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete (@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText( ForgotPassword.this, "Please check Your Email", Toast.LENGTH_LONG ).show( );
                            startActivity(new Intent(ForgotPassword.this,loginActivity.class));
                            finish();
                        } else {
                            Toast.makeText( ForgotPassword.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG ).show( );
                        }
                    }
                } );
    }
}
package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth auth;
    TextView forgotpassword;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        getSupportActionBar( ).hide( );
        auth = FirebaseAuth.getInstance( );
        email = findViewById( R.id.useremail_login );
        password = findViewById( R.id.userpassword_login );

        forgotpassword=findViewById(R.id.forgot_password);
        forgotpassword.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                startActivity(new Intent(loginActivity.this,ForgotPassword.class));
            }
        } );

    }

    public void signup (View view) {
        String userEmail = email.getText( ).toString( );
        String userPassword = password.getText( ).toString( );


        if (TextUtils.isEmpty( userEmail )) {
            Toast.makeText( this, "Enter Email", Toast.LENGTH_SHORT ).show( );
            return;
        }
        if (TextUtils.isEmpty( userPassword )) {
            Toast.makeText( this, "Enter Password", Toast.LENGTH_SHORT ).show( );
            return;
        }

        if (userPassword.length( ) < 6) {
            Toast.makeText( this, "PASSWORD TOO SHORT,PASSWORD SHOULD BE MINIMUM 8 DIGIT LONG", Toast.LENGTH_LONG ).show( );
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener( loginActivity.this, new OnCompleteListener<AuthResult>( ) {
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful( )) {
                            Toast.makeText( loginActivity.this, "Login Successful", Toast.LENGTH_SHORT ).show( );
                            startActivity( new Intent( loginActivity.this, MainActivity.class ) );
                            finish();

                        } else {
                            Toast.makeText( loginActivity.this, "No Account Found In Our Database ," +task.getException().getMessage(), Toast.LENGTH_LONG ).show( );
                        }
                    }
                } );


    }


    public void signin_login (View view) {
        startActivity( new Intent( loginActivity.this, RegistrationActivity.class ) );
    }
}
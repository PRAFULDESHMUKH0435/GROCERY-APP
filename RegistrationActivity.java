package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praful.Models.CustomeradModel;
import com.example.praful.Models.UserModel;
import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {


    private CircleImageView registrationImage;
    private Uri imageUri;
    EditText name,number, email, password,address;
    private FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );


        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity( new Intent( RegistrationActivity.this, MainActivity.class ) );
            finish();
        }

        name = findViewById( R.id.username );
        number=findViewById(R.id.usernumber);
        email = findViewById( R.id.useremail );
        password = findViewById( R.id.userpassword );
        address=findViewById(R.id.address);


    }



//    @Override
//    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult( requestCode, resultCode, data );
//        if (requestCode == 1 && resultCode == RESULT_OK && data.getData()!=null){
//            imageUri = data.getData();
//            registrationImage.setImageURI(imageUri);
//        }
//    }



    public void signup (View view) {

//        String userImage = registrationImage.getResources().toString();
        String userName = name.getText( ).toString();
        String userNumber = number.getText().toString();
        String userEmail = email.getText( ).toString( );
        String userPassword = password.getText( ).toString();
        String userAddress = address.getText( ).toString();

        if (TextUtils.isEmpty( userName )) {
            Toast.makeText( this, "Enter name", Toast.LENGTH_LONG ).show( );
            return;
        }

        if (TextUtils.isEmpty(userNumber)) {
            Toast.makeText( this, "Please Enter Valid 10 Digit  Number", Toast.LENGTH_LONG ).show( );
            return;
        }

        if (TextUtils.isEmpty( userEmail )) {
            Toast.makeText( this, "Enter Email", Toast.LENGTH_LONG ).show( );
            return;
        }
        if (TextUtils.isEmpty( userPassword )) {
            Toast.makeText( this, "Enter Password", Toast.LENGTH_LONG ).show( );
             return;
        }
        if ( userAddress.length()<10) {
            Toast.makeText( this, " Address Should Be More Than 10 Digit Long", Toast.LENGTH_LONG ).show( );
            return;
        }

        if (userPassword.length()<6){
            Toast.makeText( this, "PASSWORD TOO SHORT,PASSWORD SHOULD BE MINIMUM 8 DIGIT LONG",
                       Toast.LENGTH_LONG ).show( );
            return;
        }

          auth.createUserWithEmailAndPassword(userEmail,userName)
                  .addOnCompleteListener( RegistrationActivity.this, new OnCompleteListener<AuthResult>( ) {
                      @Override
                      public void onComplete (@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //////////////////
                            UserModel userModel = new UserModel(userName.toUpperCase(),userNumber,userEmail,userPassword,userAddress.toUpperCase());

                            //////////////CHANGES MADE HERE
                            String id = task.getResult().getUser().getUid();
                            ////////////////////////////////////////
                            database.getReference().child("Users").child(id).setValue( userModel);

                            Toast.makeText( RegistrationActivity.this, "SUCCESSFULLY REGISTERED", Toast.LENGTH_LONG ).show( );
                            startActivity( new Intent( RegistrationActivity.this, MainActivity.class ) );
                            finish();

                        }
                        else {
                            Toast.makeText( RegistrationActivity.this, "Registration Failed"+task.getException().getMessage(), Toast.LENGTH_LONG ).show( );
                        }
                      }
                  } );
    }


    public void signin (View view) {
        startActivity( new Intent( RegistrationActivity.this, loginActivity.class ) );

    }
}
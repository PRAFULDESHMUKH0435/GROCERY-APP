package com.example.praful.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.praful.R;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thanks );

        Thread thread = new Thread(){
            public  void  run(){
                try {
                    sleep( 5000 );
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent( ThanksActivity.this, MainActivity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}
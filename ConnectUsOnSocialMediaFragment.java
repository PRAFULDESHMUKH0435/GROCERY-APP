package com.example.praful;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ConnectUsOnSocialMediaFragment extends Fragment{

    ImageView portfolio,linked;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_connect_us_on_social_media, container, false );

        portfolio=view.findViewById(R.id.myportfolio);
        linked=view.findViewById(R.id.linked_in);

        portfolio.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String url = "https://v5x2owwkeqq8jpmdpz5alg-on.drv.tw/www.portfoliowebsite.in/";
                Intent i = new Intent( Intent.ACTION_VIEW);
                i.setData(Uri.parse( url));
                startActivity(i);
            }
        } );


        linked.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String url = "https://www.instagram.com/getmything/";
                Intent i = new Intent( Intent.ACTION_VIEW);
                i.setData(Uri.parse( url));
                startActivity(i);
            }
        } );
        return view;
    }




}
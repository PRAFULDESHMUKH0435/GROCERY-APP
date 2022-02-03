package com.example.praful;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ContactUsFragment extends Fragment {

    ImageView whatsapp;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_contact_us, container, false );

        whatsapp = view.findViewById( R.id.whats_app_icon );
        whatsapp.setOnClickListener( new View.OnClickListener( ) {
            final String num = "+918999384981";

            @Override
            public void onClick (View v) {
                boolean installed = isAppInstalled( "com.whatsapp" );

                if (installed) {
                    Intent intent = new Intent( Intent.ACTION_VIEW );
                    intent.setData( Uri.parse( "http://api.whatsapp.com/send?phone=" + num ) );
                    startActivity( intent );
                } else {
                    Toast.makeText( getActivity( ), "Whatsapp is not installed!", Toast.LENGTH_SHORT ).show( );
                }

            }
        } );
        return view;

    }

    private boolean isAppInstalled (String s) {
        PackageManager packageManager = getActivity().getPackageManager( );
        boolean is_installed;

        try {
            packageManager.getPackageInfo( s, PackageManager.GET_ACTIVITIES );
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace( );
        }
        return is_installed;
    }

}

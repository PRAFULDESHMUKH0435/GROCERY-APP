package com.example.praful;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImg1;
    TextView name1, email1, number1,password1, address1;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseFirestore firestore;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile, container, false );

        name1 = view.findViewById( R.id.profile_name );
        email1 = view.findViewById( R.id.profile_email );
        number1 = view.findViewById( R.id.profile_number);
        password1 = view.findViewById( R.id.profile_password);
        address1 = view.findViewById(R.id.profile_address);
        profileImg1 = view.findViewById(R.id.profile_image);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance( );
        firestore = FirebaseFirestore.getInstance();

        String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance( ).getReference( ).child("Users").child(id);
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue().toString();
                String email = snapshot.child( "userEmail").getValue().toString( );
                String number = snapshot.child( "userNumber").getValue().toString();
                String password = snapshot.child( "userPassword").getValue().toString();
                String address = snapshot.child( "userAddress").getValue().toString();

                name1.setText( name );
                email1.setText( email );
                number1.setText( number );
                password1.setText( password );
                address1.setText( address );
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error In Getting Your Data From Server", Toast.LENGTH_SHORT ).show( );
            }
        } );
        return view;
    }
}

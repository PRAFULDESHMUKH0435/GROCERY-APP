package com.example.praful.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.praful.Models.MyCartModel;
import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.app.ActivityCompat.recreate;
//import eu.dkaratzas.android.inapp.update.Constants;
//import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
//import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView headerName, headerEmail;
    CircleImageView headerImg;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference ref;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

            Toolbar toolbar = findViewById( R.id.toolbar );
            setSupportActionBar( toolbar );
            getSupportActionBar( ).setDisplayShowTitleEnabled( false );
            toolbar.setTitle( "Get My Things" );

            DrawerLayout drawer = findViewById( R.id.drawer_layout );
            database = FirebaseDatabase.getInstance( );
            auth = FirebaseAuth.getInstance( );
            firebaseUser = auth.getCurrentUser( );


            // FOR MESSAGE SENDING
            FirebaseMessaging.getInstance( ).subscribeToTopic( "Notification" );

//
//            String id = FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );
//            ref = FirebaseDatabase.getInstance( ).getReference( ).child( "Users" ).child( id );

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            NavigationView navigationView = findViewById( R.id.nav_view );
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_profile, R.id.nav_myOrders, R.id.nav_location, R.id.nav_connect_us, R.id.nav_contact_us )
                    .setDrawerLayout( drawer )
                    .build( );
            NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
            NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
            NavigationUI.setupWithNavController( navigationView, navController );


            View headerView = navigationView.getHeaderView( 0 );
            headerName = headerView.findViewById( R.id.nav_header_name);
            headerImg = headerView.findViewById( R.id.nav_header_img);
//            headerName.setText("Get My Things");

        String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance( ).getReference( ).child("Users").child(id);
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("userName").getValue().toString();
                headerName.setText(name);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error In Getting Your Data From Server", Toast.LENGTH_SHORT ).show( );
            }
        } );
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater( ).inflate( R.menu.main, menu );
            MenuItem search = menu.findItem( R.id.search_icon );
            return true;
        }


        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId( );
            if (id == R.id.search_icon) {
                Toast.makeText( this, "Search Your Items", Toast.LENGTH_SHORT ).show( );
                startActivity( new Intent( this, ShowAllActivity.class ) );
            }
            if (id == R.id.cart_icon) {
                Intent intent = new Intent( MainActivity.this, CartActivity.class );
                startActivity( intent );

            } else {
                return super.onOptionsItemSelected( item );
            }
            return true;
        }

        @Override
        public boolean onSupportNavigateUp () {
            NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
            return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                    || super.onSupportNavigateUp( );
        }

        @Override
        public void onBackPressed () {
            new AlertDialog.Builder( MainActivity.this )
                    .setIcon( R.drawable.ic_baseline_warning_24 )
                    .setTitle( "Exit" )
                    .setMessage( "Are You Sure You Want To Exit From App" )
                    .setPositiveButton( "Yes", new DialogInterface.OnClickListener( ) {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            finishActivity( 0 );
                            finish( );
                        }
                    } ).setNegativeButton( "No", new DialogInterface.OnClickListener( ) {
                @Override
                public void onClick (DialogInterface dialog, int which) {
                    dialog.dismiss( );
                }
            } ).show( );

        }


    }

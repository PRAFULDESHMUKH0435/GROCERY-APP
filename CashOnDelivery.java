package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.praful.Models.MyCartModel;
import com.example.praful.Models.UserModel;
import com.example.praful.R;
import com.example.praful.adapters.MyCartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashOnDelivery extends AppCompatActivity {
    
    FirebaseAuth auth;
    String cu;
    FirebaseFirestore firestore;
    FirebaseDatabase database;
    DatabaseReference cua, aref, ref;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    Button button;
    ImageSlider PaymentSlider;
    TextView deliveryarea;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cash_on_delivery );

        getSupportActionBar().setTitle("Order Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance( );
        auth = FirebaseAuth.getInstance( );
        firestore = FirebaseFirestore.getInstance( );
        FirebaseFirestore db = FirebaseFirestore.getInstance( );

        cartModelList = new ArrayList<>( );
        cartAdapter = new MyCartAdapter( this, cartModelList );


        deliveryarea = findViewById( R.id.deliveryarea );
        aref = FirebaseDatabase.getInstance( ).getReference( ).child( "Address" ).child( "Adresses" );
        aref.addListenerForSingleValueEvent( new ValueEventListener( ) {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                String addr = snapshot.child( "prafulad" ).getValue( ).toString( );
                deliveryarea.setText( "We Are Available For Delivery Only In:\n" + addr.toUpperCase( ) + "\nMake Sure That We Are Available In Your Area Before Placing Order" );
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
                Toast.makeText( getApplicationContext( ), "Error In Getting Your Data From Server", Toast.LENGTH_LONG ).show( );
            }
        } );


        Button button = findViewById( R.id.cnfrm_order );
        button.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( CashOnDelivery.this );
                alertDialogBuilder.setTitle( "Order Confirmation" );
                alertDialogBuilder.setIcon( R.drawable.ic_baseline_shopping_cart_24_for_alert );
                alertDialogBuilder.setMessage( "Are you sure You want To Place An Order\n" +
                                                       "Once You Place Order , You Won't Be Able To Cancel Order" );
                alertDialogBuilder.setPositiveButton( "yes",
                                                      new DialogInterface.OnClickListener( ) {
                                                          @Override
                                                          public void onClick (DialogInterface arg0, int arg1) {

                                                              ////////////////////////////////////////////////////////
                                                              Intent i = new Intent( CashOnDelivery.this, ThanksActivity.class );
                                                              ////////////////////////////////////////////
                                                              String id = FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );
                                                              ref = FirebaseDatabase.getInstance( ).getReference( ).child( "Users" ).child( id );
                                                              ref.addListenerForSingleValueEvent( new ValueEventListener( ) {
                                                                  @Override
                                                                  public void onDataChange (@NonNull DataSnapshot snapshot) {
                                                                      String username = snapshot.child( "userName" ).getValue( ).toString( );
                                                                      String usernumber = snapshot.child( "userNumber" ).getValue( ).toString( );
                                                                      String useraddress = snapshot.child( "userAddress" ).getValue( ).toString( );
//
//
                                                                      String userchname = "ORDER  FROM  :::::: " + username + "\n" + "AND  ADDRESS IS:::: " + useraddress.toUpperCase( ) + ", \n" + " USER NUMBER:::: " + usernumber;
                                                                      Map<String, String> map = new HashMap<>();
                                                                      map.put( "CustomerName", userchname );

                                                                      firestore.collection( "CurrentUser" ).document( auth.getCurrentUser( ).getUid( ) )
                                                                              .collection( "CustomerDetails" ).add( map ).addOnCompleteListener( new OnCompleteListener<DocumentReference>( ) {
                                                                          @Override
                                                                          public void onComplete (@NonNull Task<DocumentReference> task) {
                                                                              if (task.isSuccessful( )) {
                                                                                  Toast.makeText( getApplicationContext( ), "ORDER SUBMITTED SUCCESSFULLY", Toast.LENGTH_LONG );
                                                                              } else {
                                                                                  Toast.makeText( getApplicationContext( ), "SORRY YOUR ORDER  IS NOT SUBMITTED", Toast.LENGTH_LONG ).show( );
                                                                              }
                                                                          }
                                                                      } );

                                                                  }
                                                                  @Override
                                                                  public void onCancelled (@NonNull DatabaseError error) {
                                                                      Toast.makeText( getApplicationContext( ), "Error In Getting Your Data From Server", Toast.LENGTH_SHORT ).show( );
                                                                  }
                                                              } );
                                                              startActivity( i );
//                                                              ////////////////////////////////////////////////////////////////
                                                          }
                                                      } );


                alertDialogBuilder.setNegativeButton( "No", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss( );
                    }
                } );
                AlertDialog alertDialog = alertDialogBuilder.create( );
                alertDialog.show( );
          }
      } );


                // PAYMENT SLIDER
                PaymentSlider = findViewById( R.id.payment_image_slider );
                final List<SlideModel> paymentimages = new ArrayList<>( );
                FirebaseDatabase.getInstance( ).getReference( ).child( "PaymentSlider" )
                        .addListenerForSingleValueEvent( new ValueEventListener( ) {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren( ))
                                    paymentimages.add( new SlideModel( data.child( "url" ).getValue( ).toString( ), data.child( "title" ).getValue( ).toString( ), ScaleTypes.FIT ) );

                                PaymentSlider.setImageList( paymentimages, ScaleTypes.CENTER_CROP );

                                PaymentSlider.setItemClickListener( new ItemClickListener( ) {
                                    @Override
                                    public void onItemSelected (int i) {
                                        Toast.makeText( getBaseContext( ), paymentimages.get( i ).getTitle( ).toString( ), Toast.LENGTH_SHORT );
                                    }
                                } );
                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError error) {
                            }
                        } );
            }
}

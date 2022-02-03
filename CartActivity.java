package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praful.Models.MyCartModel;
import com.example.praful.R;
import com.example.praful.adapters.MyCartAdapter;
import com.example.praful.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    TextView overAllAmount, buynow , movingtext;
    RecyclerView recyclerView;
    DatabaseReference aref,ref,uref;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart );

        getSupportActionBar().setTitle("My Cart Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

       //GET DATA FROM DETAILED ACTIVITY
        Object obj = getIntent( ).getSerializableExtra( "item");



        movingtext = findViewById(R.id.textView);
        movingtext.setSelected(true);
        buynow = findViewById(R.id.buy_now);
        buynow.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent okintent = new Intent( CartActivity.this, CashOnDelivery.class );
                startActivity( okintent );
            }
        } );


        /////////////////////////////////////////////////////////
        LocalBroadcastManager.getInstance( this )
                .registerReceiver( mMessageReceiver, new IntentFilter( "MyTotalAmount" ) );

        overAllAmount = findViewById( R.id.my_cart_total_price );
        recyclerView = findViewById( R.id.cart_rec );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        cartModelList = new ArrayList<>( );
        cartAdapter = new MyCartAdapter( this, cartModelList );
        recyclerView.setAdapter( cartAdapter );

        firestore.collection( "CurrentUser" ).document( auth.getCurrentUser( ).getUid() )
                .collection( "AddToCart" ).get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful( )) {
                    for (QueryDocumentSnapshot doc : task.getResult( )) {
                        String documentId = doc.getId( );
                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                        cartModelList.add( myCartModel);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
            }
        } );
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver( ) {
        @Override
        public void onReceive (Context context, Intent intent) {
            int totalBill = intent.getIntExtra( "totalAmount", 0 );
            intent.putExtra("praful",totalBill);
            long delivery = 5;
            long sum = totalBill +delivery;
            if (totalBill<=500){
                overAllAmount.setText("Total Bill =Rs." + sum + (" (Including 5 Rs. Delivery) ") );
            }else {
                overAllAmount.setText( "Total Bill =Rs." + totalBill +(" (Free Delivery Applied)"));
            }
            if (totalBill < 150) {
                buynow.setVisibility( View.GONE );
            } else if (totalBill == 0) {
                buynow.setVisibility( View.GONE );
            } else {
                buynow.setVisibility( View.VISIBLE );
                intent.putExtra( "prafuld", totalBill );
            }
        }
    };

}
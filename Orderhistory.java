package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praful.ContactUsFragment;
import com.example.praful.Models.MyCartModel;
import com.example.praful.Models.OrderHistoryModel;
import com.example.praful.R;
import com.example.praful.adapters.MyCartAdapter;
import com.example.praful.adapters.OrderHistoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class Orderhistory extends AppCompatActivity {

    RecyclerView recyclerView;
    List<OrderHistoryModel> orderHistoryModelList;
    OrderHistoryAdapter orderHistoryAdapter;
    MyCartAdapter cartAdapter;
    TextView useradress,orderhis,deladdress;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_history );

        getSupportActionBar().setTitle("My Order History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance( );
        firestore = FirebaseFirestore.getInstance( );

        // TO LOAD DATA FROM FIREBASE TO SHOW USERADDRESS
        auth = FirebaseAuth.getInstance( );
        database = FirebaseDatabase.getInstance( );


        String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance( ).getReference( ).child("Users").child(id);
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                String address = snapshot.child( "userAddress").getValue().toString();

                deladdress=findViewById(R.id.addressdel);
                deladdress.setText("You Will Receive Your Order On Your Address:  "+address);

            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error In Getting Your Data From Server", Toast.LENGTH_SHORT ).show( );
            }
        } );



//        String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
//        ref = FirebaseDatabase.getInstance( ).getReference( ).child("Users").child(id);
//        ref.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange (@NonNull DataSnapshot snapshot) {
//
//                useradress = findViewById(R.id.show_address);
//                String address = snapshot.child( "userAddress" ).getValue( ).toString( );
//                useradress.setText("YOUR ORDER WILL BE DELIVERED TO YOUR ADDRESS :::\n "+address);
//            }
//
//            @Override
//            public void onCancelled (@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Error In Getting Your Data From Server", Toast.LENGTH_SHORT ).show( );
//            }
//        } );

        // TO LOAD DATA FROM ORDER HISTORY
        recyclerView = findViewById( R.id.my_order_historyrec);
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        orderHistoryModelList = new ArrayList<>( );
        orderHistoryAdapter = new OrderHistoryAdapter( this, orderHistoryModelList );
        recyclerView.setAdapter( orderHistoryAdapter );

        firestore.collection( "CurrentUser" ).document( auth.getCurrentUser( ).getUid( ) )
                .collection( "AddToCart" ).get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful( )) {
                    for (QueryDocumentSnapshot doc : task.getResult( )) {
                        String documentId = doc.getId( );
                        OrderHistoryModel orderHistoryModel = doc.toObject( OrderHistoryModel.class );
                        orderHistoryModel.setDocumentId( documentId );
                        orderHistoryModelList.add( orderHistoryModel);
                        orderHistoryAdapter.notifyDataSetChanged( );
                    }
                }
            }
        } );
    }


}
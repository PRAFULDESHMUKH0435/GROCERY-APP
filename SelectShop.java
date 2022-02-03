package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praful.Models.ShopModel;
import com.example.praful.R;
import com.example.praful.adapters.ShopAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SelectShop extends AppCompatActivity {

    EditText txt_search;
    RecyclerView ShopRecyclerview;
    Button Continuebtn;
    //Shop Recycler View
    ShopAdapter shopAdapter;
    List<ShopModel> shopModelList;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_shop );


        ShopRecyclerview =findViewById( R.id.shop_rec );
        FirebaseFirestore db = FirebaseFirestore.getInstance( );

        Continuebtn = findViewById(R.id.continue_btn);
        Continuebtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent selecti = new Intent(SelectShop.this,CashOnDelivery.class);
                startActivity(selecti);
            }
        } );

        // SHOP RECYCLER VIEW
        ShopRecyclerview.setLayoutManager( new LinearLayoutManager(this, RecyclerView.VERTICAL, false ) );
        shopModelList = new ArrayList<>( );
        shopAdapter = new ShopAdapter(this, shopModelList );
        ShopRecyclerview.setAdapter( shopAdapter );
        db.collection( "ShopList" )
                .get( )
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete (@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful( )) {
                            for (QueryDocumentSnapshot document : task.getResult( )) {

                                ShopModel shopModel = document.toObject( ShopModel.class );
                                shopModelList.add( shopModel );
                                shopAdapter.notifyDataSetChanged( );
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable To Load Shops , \nCheck Your Internet Connection" + task.getException().getMessage(), Toast.LENGTH_SHORT ).show( );
                        }
                    }
                } );


        //SEARCH FUNCTIONALITY
        txt_search = findViewById(R.id.txt_searchtext);
        txt_search.addTextChangedListener( new TextWatcher( ) {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged (Editable s) {
                filter(s.toString());
            }
        } );
    }

    private void filter (String text) {
        ArrayList <ShopModel> filterList = new ArrayList<>();
        for (ShopModel item: shopModelList){
            if (item.getShopadd().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        shopAdapter.filteredlist(filterList);
    }


}
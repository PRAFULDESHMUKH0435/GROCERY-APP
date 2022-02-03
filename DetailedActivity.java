package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.praful.Models.CatShowAllModel;
import com.example.praful.Models.NewProductModel;
import com.example.praful.Models.PopularProductModel;
import com.example.praful.Models.ShowAllModel;
import com.example.praful.Models.newproShowAllModel;
import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity,ProDesc;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;
    int totalQuantity = 1;
    int totalPrice = 0;
    Menu cartmenu;


    NewProductModel newProductModel;
    newproShowAllModel newproShowAllModel;
    PopularProductModel popularProductModel;
    ShowAllModel showAllModel;
    CatShowAllModel catShowAllModel;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_detailed);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartmenu=(Menu)findViewById(R.id.cart_icon);
        auth = FirebaseAuth.getInstance( );
        firestore = FirebaseFirestore.getInstance();
        final Object obj = getIntent( ).getSerializableExtra( "detailed" );

        if (obj instanceof NewProductModel) {
            newProductModel = (NewProductModel) obj;
        } else if (obj instanceof PopularProductModel) {
            popularProductModel = (PopularProductModel) obj;
        }  else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        } else if (obj instanceof CatShowAllModel) {
            catShowAllModel = (CatShowAllModel) obj;
        } else if (obj instanceof newproShowAllModel) {
            newproShowAllModel = (newproShowAllModel) obj;
        }


        detailedImg = findViewById( R.id.detailed_img );
        quantity = findViewById( R.id.quantity );
        ProDesc= findViewById(R.id.detailed_desc);
        rating = findViewById( R.id.my_rating );
        name = findViewById( R.id.detailed_name );
        description = findViewById( R.id.detailed_desc );
        price = findViewById( R.id.detailed_price );
        addToCart = findViewById( R.id.add_to_cart );
        addItems = findViewById( R.id.add_item );
        removeItems = findViewById( R.id.remove_item );


        //NEW PRODUCTS
        if (newProductModel != null) {
            Glide.with( getApplicationContext( ) ).load( newProductModel.getImg_url( ) ).into( detailedImg );
            name.setText( newProductModel.getName( ) );
            rating.setText( newProductModel.getRating( ) );
            description.setText( newProductModel.getDescription( ) );
            price.setText( String.valueOf( newProductModel.getPrice( ) ) );
            totalPrice = newProductModel.getPrice( ) * totalQuantity;

        }

        //POPULAR PRODUCTS
        if (popularProductModel != null) {
            Glide.with( getApplicationContext( ) ).load( popularProductModel.getImg_url( ) ).into( detailedImg );
            name.setText( popularProductModel.getName( ) );
            rating.setText( popularProductModel.getRating( ) );
            description.setText( popularProductModel.getDescription( ) );
            price.setText( String.valueOf( popularProductModel.getPrice( ) ) );
            totalPrice = popularProductModel.getPrice( ) * totalQuantity;
        }

        //Show All PRODUCTS
        if (showAllModel != null) {
            Glide.with( getApplicationContext( ) ).load( showAllModel.getImg_url( ) ).into(detailedImg);
            name.setText( showAllModel.getName( ) );
            rating.setText( showAllModel.getRating( ) );
            description.setText(showAllModel.getDescription( ) );
            price.setText(String.valueOf(showAllModel.getPrice()));
            totalPrice = showAllModel.getPrice( ) * totalQuantity;
        }

        // Category Show All PRODUCTS
        if (catShowAllModel != null) {
            Glide.with( getApplicationContext( ) ).load(catShowAllModel.getImg_url()).into(detailedImg);
            name.setText(catShowAllModel.getName( ) );
            rating.setText(catShowAllModel.getRating( ) );
            description.setText(catShowAllModel.getDescription( ) );
            price.setText(String.valueOf(catShowAllModel.getPrice()));
            totalPrice = catShowAllModel.getPrice( ) * totalQuantity;
        }

        // New Products Show All PRODUCTS
        if (newproShowAllModel != null) {
            Glide.with( getApplicationContext()).load(newproShowAllModel.getImg_url()).into(detailedImg);
            name.setText(newproShowAllModel.getName( ) );
            rating.setText(newproShowAllModel.getRating( ) );
            description.setText(newproShowAllModel.getDescription( ) );
            price.setText(String.valueOf(newproShowAllModel.getPrice()));
            totalPrice = newproShowAllModel.getPrice( ) * totalQuantity;
        }

        addToCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                addtocart( );
            }
        } );

        addItems.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText( String.valueOf( totalQuantity ) );

                    if (newProductModel != null) {
                        totalPrice = newProductModel.getPrice( ) * totalQuantity;
                    }
                    if (popularProductModel != null) {
                        totalPrice = popularProductModel.getPrice( ) * totalQuantity;
                    }
                    if (showAllModel != null) {
                        totalPrice = showAllModel.getPrice( ) * totalQuantity;
                    }
                    if (catShowAllModel!= null) {
                        totalPrice = catShowAllModel.getPrice( ) * totalQuantity;

                    } if (newproShowAllModel!= null) {
                        totalPrice = newproShowAllModel.getPrice( ) * totalQuantity;
                    }
                }
            }
        } );

        removeItems.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText( String.valueOf( totalQuantity) );
                }
            }
        } );
    }

    private void addtocart () {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance( );

            SimpleDateFormat currentDate = new SimpleDateFormat( "dd/MM/yyyy" );
            saveCurrentDate = currentDate.format( calForDate.getTime( ) );
//            SimpleDateFormat currentTime = new SimpleDateFormat( "HH:MM:SS" );
//            saveCurrentTime = currentTime.format( calForDate.getTime( ) );
            final HashMap<String, Object> cartMap = new HashMap<>( );
            cartMap.put( "productName", name.getText().toString());
            cartMap.put( "productPrice", price.getText().toString());
//            cartMap.put( "currentTime", saveCurrentTime);
            cartMap.put( "currentDate", saveCurrentDate);
            cartMap.put( "totalQuantity", quantity.getText().toString());
            cartMap.put("ProDesc",ProDesc.getText().toString());
            cartMap.put( "totalPrice", totalPrice);


//        AddToCart=>CurrentUser
//        User=>AddToCart
            firestore.collection( "CurrentUser" ).document( auth.getCurrentUser( ).getUid() )
                    .collection( "AddToCart" ).add( cartMap ).addOnCompleteListener( new OnCompleteListener<DocumentReference>( ) {
                @Override
                public void onComplete (@NonNull Task<DocumentReference> task) {
                    StyleableToast.makeText(getApplicationContext(),totalQuantity +" Items Of  "+name.getText().toString()+" Added To Cart", Toast.LENGTH_LONG, R.style.mytoast).show();
                }
            } );
    }
}
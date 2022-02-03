package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.praful.Models.CategoryModel;
import com.example.praful.Models.NewProductModel;
import com.example.praful.Models.ShopModel;
import com.example.praful.Models.ShowAllModel;
import com.example.praful.Models.newproShowAllModel;
import com.example.praful.R;
import com.example.praful.adapters.NewProductsAdapter;
import com.example.praful.adapters.ShopAdapter;
import com.example.praful.adapters.ShowAllAdapter;
import com.example.praful.adapters.newproShowAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;

    List<NewProductModel> newProductModelList;
    NewProductsAdapter newProductsAdapter;
    FirebaseFirestore firestore;

    //for search
    EditText showallsearch;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show_all );
        String type = getIntent( ).getStringExtra( "type" );
        getSupportActionBar( ).setTitle("All Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance( );
        recyclerView = findViewById( R.id.show_all_rec );
        recyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
        showAllModelList = new ArrayList<>( );
        showAllAdapter = new ShowAllAdapter( this, showAllModelList );
        newProductModelList = new ArrayList<>( );
        newProductsAdapter = new NewProductsAdapter( this, newProductModelList );
        recyclerView.setAdapter( showAllAdapter );


        //SEARCH FUNCTION
        showallsearch = findViewById(R.id.showall_search);
        showallsearch.addTextChangedListener( new TextWatcher( ) {
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



        //SEARCH FUNCTION ENDS HERE



        if (type == null || type.isEmpty( )) {
            firestore.collection( "ShowAllProducts" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );

        }


        if (type != null && type.equalsIgnoreCase( "cookingoil" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "cookingoil" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }

                    } );

        }


        if (type != null && type.equalsIgnoreCase( "dalandpulses" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "dalandpulses" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );

        }

        if (type != null && type.equalsIgnoreCase( "Soaps" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "soaps" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );

        }

        if (type != null && type.equalsIgnoreCase( "Spices" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "spices" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );

        }



        /*NEW PRODUCTS STARTS FROM HERE */
        if (type != null && type.equalsIgnoreCase( "cakes" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "cakes" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }


        if (type != null && type.equalsIgnoreCase( "salt" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "salt" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }

        if (type != null && type.equalsIgnoreCase( "sugar" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "sugar" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }



        if (type != null && type.equalsIgnoreCase("Cleaners")) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "cleaners" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );

        }



        if (type != null && type.equalsIgnoreCase( "salt" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "salt" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }

        if (type != null && type.equalsIgnoreCase( "sugar" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "sugar" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }



        if (type != null && type.equalsIgnoreCase( "biscuits" )) {
            firestore.collection( "ShowAllProducts" ).whereEqualTo( "type", "biscuits" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    ShowAllModel showAllModel = doc.toObject( ShowAllModel.class );
                                    showAllModelList.add( showAllModel );
                                    showAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }
                    } );
        }
    }

    private void filter (String text) {
        ArrayList <ShowAllModel> filterList = new ArrayList<>();
        for (ShowAllModel showitem: showAllModelList){
            if (showitem.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(showitem);
            }
        }
        showAllAdapter.filteredlist(filterList);
    }


}

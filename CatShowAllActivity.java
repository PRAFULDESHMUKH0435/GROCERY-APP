package com.example.praful.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.praful.Models.CatShowAllModel;
import com.example.praful.Models.ShowAllModel;
import com.example.praful.R;
import com.example.praful.adapters.CatShowAllAdapter;
import com.example.praful.adapters.ShowAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CatShowAllAdapter catShowAllAdapter;
    List<CatShowAllModel> catshowAllModelList;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cat_show_all );
        firestore = FirebaseFirestore.getInstance( );
        recyclerView = findViewById( R.id.cat_show_all_rec );
        String type = getIntent( ).getStringExtra( "type" );
        getSupportActionBar( ).setTitle( "All Categories" );


        firestore = FirebaseFirestore.getInstance( );
        recyclerView = findViewById( R.id.cat_show_all_rec );
        recyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
        catshowAllModelList = new ArrayList<>( );
        catShowAllAdapter = new CatShowAllAdapter( this, catshowAllModelList );
        recyclerView.setAdapter( catShowAllAdapter );


        if (type == null || type.isEmpty( )) {
            firestore.collection( "Categoryitems" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot doc : task.getResult( )) {
                                    CatShowAllModel catshowAllModel = doc.toObject( CatShowAllModel.class );
                                    catshowAllModelList.add( catshowAllModel );
                                    catShowAllAdapter.notifyDataSetChanged( );
                                }
                            }
                        }

                    } );

        }




    }
}
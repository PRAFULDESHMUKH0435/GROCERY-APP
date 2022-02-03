package com.example.praful;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praful.Models.CategoryModel;
import com.example.praful.Models.ShopModel;
import com.example.praful.adapters.CategoryAdapter;
import com.example.praful.adapters.ShopAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetectMyLocationFragment extends Fragment  {

    EditText txt_search;
    RecyclerView ShopRecyclerview;

    //Shop Recycler View
    ShopAdapter shopAdapter;
    List <ShopModel> shopModelList;


    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_detect_my_location, container, false );
        ShopRecyclerview = view.findViewById( R.id.shop_rec );
        FirebaseFirestore db = FirebaseFirestore.getInstance( );


        // SHOP RECYCLER VIEW
        ShopRecyclerview.setLayoutManager( new LinearLayoutManager( getActivity( ), RecyclerView.VERTICAL, false ) );
        shopModelList = new ArrayList<>( );
        shopAdapter = new ShopAdapter( getContext( ), shopModelList );
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
                            Toast.makeText( getContext( ), "Unable To Load Shops , \nCheck Your Internet Connection" + task.getException().getMessage(), Toast.LENGTH_SHORT ).show( );
                        }
                    }
                } );


        //SEARCH FUNCTIONALITY
        txt_search = view.findViewById(R.id.txt_searchtext);
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
        return view;
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
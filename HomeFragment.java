package com.example.praful.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import com.example.praful.Activities.CashOnDelivery;
import com.example.praful.Activities.Orderhistory;
import com.example.praful.Activities.ThanksActivity;
import com.example.praful.Models.CategoryModel;
import com.example.praful.Models.NewProductModel;
import com.example.praful.Models.PopularProductModel;
import com.example.praful.R;
import com.example.praful.Activities.ShowAllActivity;
import com.example.praful.adapters.CategoryAdapter;
import com.example.praful.adapters.NewProductsAdapter;
import com.example.praful.adapters.PopularProductsAdapter;
import com.google.android.gms.dynamic.RemoteCreator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    Dialog dialog;
    ImageSlider mainslider, mainslider2;
    TextView catShowAll, popularShowAll, newProductShowAll;
    RecyclerView catRecyclerview, newProductRecyclerview, popularRecyclerview;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener mAuthListener;

    //CategoryRecyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Product Recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductModel> newProductModelList;


    //Popular Products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductModel> popularProductModelList;

    //FIRESTORE
    FirebaseFirestore db;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

            ////////////////////////////
            View root = inflater.inflate( R.layout.fragment_home, container, false );

            linearLayout = root.findViewById( R.id.home_layout );
            linearLayout.setVisibility( View.GONE );
            progressDialog = new ProgressDialog( getActivity( ) );
            catRecyclerview = root.findViewById( R.id.rec_category );
            newProductRecyclerview = root.findViewById( R.id.new_product_rec );
            popularRecyclerview = root.findViewById( R.id.popular_rec );
            catShowAll = root.findViewById( R.id.category_see_all );
            popularShowAll = root.findViewById( R.id.popular_see_all );
            newProductShowAll = root.findViewById( R.id.newProducts_see_all );
            db = FirebaseFirestore.getInstance( );
            progressDialog.setTitle( "Welcome To Get My Things" );
            progressDialog.setMessage( "Please Wait While We Load Data....." );
            progressDialog.setCanceledOnTouchOutside( false );
            progressDialog.show( );
            auth = FirebaseAuth.getInstance( );
            firebaseUser = auth.getCurrentUser( );
            String email = auth.getCurrentUser( ).getEmail( );

            dialog = new Dialog(getContext());
            ///////////////////
             checkconnection();
            /////////////////////////

            catShowAll.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick (View v) {
                    Intent intent = new Intent( getContext( ), ShowAllActivity.class );
                    startActivity( intent );
                }
            } );


            popularShowAll.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick (View v) {
                    Intent intent = new Intent( getContext( ), ShowAllActivity.class );
                    startActivity( intent );
                }
            } );


            newProductShowAll.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick (View v) {
                    Intent intent = new Intent( getContext( ), ShowAllActivity.class );
                    startActivity( intent );
                }
            } );


            mainslider = root.findViewById( R.id.image_slider );
            final List<SlideModel> remoteimages = new ArrayList<>( );


            FirebaseDatabase.getInstance( ).getReference( ).child( "Slider" )
                    .addListenerForSingleValueEvent( new ValueEventListener( ) {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren( ))
                                remoteimages.add( new SlideModel( data.child( "url" ).getValue( ).toString( ), data.child( "title" ).getValue( ).toString( ), ScaleTypes.FIT ) );

                            mainslider.setImageList( remoteimages, ScaleTypes.FIT );

                            mainslider.setItemClickListener( new ItemClickListener( ) {
                                @Override
                                public void onItemSelected (int i) {
                                    Toast.makeText( getContext( ), remoteimages.get( i ).getTitle( ).toString( ), Toast.LENGTH_SHORT );

                                }
                            } );
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error) {

                        }
                    } );


            mainslider2 = root.findViewById( R.id.image_slider2 );
            final List<SlideModel> remoteimages2 = new ArrayList<>( );
            FirebaseDatabase.getInstance( ).getReference( ).child( "Slider2" )
                    .addListenerForSingleValueEvent( new ValueEventListener( ) {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren( ))
                                remoteimages2.add( new SlideModel( data.child( "url" ).getValue( ).toString( ), data.child( "title" ).getValue( ).toString( ), ScaleTypes.FIT ) );

                            mainslider2.setImageList( remoteimages2, ScaleTypes.FIT );

                            mainslider2.setItemClickListener( new ItemClickListener( ) {
                                @Override
                                public void onItemSelected (int i) {
                                    Toast.makeText( getContext( ), remoteimages2.get( i ).getTitle( ).toString( ), Toast.LENGTH_SHORT );

                                }
                            } );
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error) {

                        }
                    } );


            //Category
            catRecyclerview.setLayoutManager( new LinearLayoutManager( getActivity( ), RecyclerView.HORIZONTAL, false ) );
            categoryModelList = new ArrayList<>( );
            categoryAdapter = new CategoryAdapter( getContext( ), categoryModelList );
            catRecyclerview.setAdapter( categoryAdapter );
            db.collection( "Categoryitems" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot document : task.getResult( )) {

                                    CategoryModel categoryModel = document.toObject( CategoryModel.class );
                                    categoryModelList.add( categoryModel );
                                    categoryAdapter.notifyDataSetChanged( );
                                    linearLayout.setVisibility( View.VISIBLE );
                                    progressDialog.dismiss( );
                                }

                            } else {
                                Toast.makeText( getActivity( ), "" + task.getException( ), Toast.LENGTH_SHORT ).show( );
                            }
                        }
                    } );


//           New Products
            newProductRecyclerview.setLayoutManager( new LinearLayoutManager( getActivity( ), RecyclerView.HORIZONTAL, false ) );
//           newProductRecyclerview.setLayoutManager( new LinearLayoutManager( getActivity( ), RecyclerView.HORIZONTAL, false ) );
            newProductModelList = new ArrayList<>( );
            newProductsAdapter = new NewProductsAdapter( getContext( ), newProductModelList );
            newProductRecyclerview.setAdapter( newProductsAdapter );


            db.collection( "NewProducts" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot document : task.getResult( )) {

                                    NewProductModel newProductModel = document.toObject( NewProductModel.class );
                                    newProductModelList.add( newProductModel );
                                    newProductsAdapter.notifyDataSetChanged( );
                                }
                            } else {
                                Toast.makeText( getActivity( ), "" + task.getException( ), Toast.LENGTH_SHORT ).show( );
                            }
                        }
                    } );


            popularRecyclerview.setLayoutManager( new GridLayoutManager( getActivity( ), 2 ) );
            popularProductModelList = new ArrayList<>( );
            popularProductsAdapter = new PopularProductsAdapter( getContext( ), popularProductModelList );
            popularRecyclerview.setAdapter( popularProductsAdapter );

            db.collection( "PopularProducts" )
                    .get( )
                    .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete (@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful( )) {
                                for (QueryDocumentSnapshot document : task.getResult( )) {

                                    PopularProductModel popularProductModel = document.toObject( PopularProductModel.class );
                                    popularProductModelList.add( popularProductModel );
                                    popularProductsAdapter.notifyDataSetChanged( );
                                }
                            } else {
                                Toast.makeText( getActivity( ), "" + task.getException( ), Toast.LENGTH_SHORT ).show( );
                            }
                        }
                    } );

                return root;
     }

    private void checkconnection ( ) {
        ConnectivityManager manager  = (ConnectivityManager)getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if (null!=activenetwork){
            if (activenetwork.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(getContext(), "Wifi Network Enabled", Toast.LENGTH_SHORT ).show( );
            }
            else if (activenetwork.getType() == ConnectivityManager.TYPE_MOBILE){
            }
        }else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG ).show( );
            dialog.setContentView(R.layout.alertdialogfornetwork);
            dialog.show();
            dialog.setCancelable(false);
            Button okay = dialog.findViewById(R.id.okaybtn);
            okay.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick (View v) {
                    dialog.dismiss();
                }
            } );
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( getContext());
//            alertDialogBuilder.setTitle( "No Internet  Confirmation" );
//            alertDialogBuilder.setIcon( R.drawable.ic_baseline_shopping_cart_24_for_alert );
//            alertDialogBuilder.setMessage( "Are you sure You want To Place An Order\n" +
//                                                   "Once You Place Order , You Won't Be Able To Cancel Order" );
//            alertDialogBuilder.setPositiveButton( "yes",
//                                                  new DialogInterface.OnClickListener( ) {
//                                                      @Override
//                                                      public void onClick (DialogInterface arg0, int arg1) {
//
//                                                          ////////////////////////////////////////////////////////
//                                                          Intent i = new Intent( getContext(), ThanksActivity.class );
//                                                         ///////////////////////////////////////////////
//                                                      }
//                                                  } );
//
//
//            alertDialogBuilder.setNegativeButton( "No", new DialogInterface.OnClickListener( ) {
//                @Override
//                public void onClick (DialogInterface dialog, int which) {
//                    dialog.dismiss( );
//                }
//            } );
//            AlertDialog alertDialog = alertDialogBuilder.create( );
//            alertDialog.show( );
        }
    }
}




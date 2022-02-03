package com.example.praful.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praful.Models.ShopModel;
import com.example.praful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    Context context;
    List<ShopModel> list;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public ShopAdapter (Context context, List<ShopModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate( R.layout.shoppro,parent,false) );
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {

        Glide.with( context).load(list.get(position).getShopimg()).into(holder.ShopImg);
        holder.ShopName.setText(list.get( position).getShopname());
        holder.ShopAdd.setText(list.get( position).getShopadd());

       firestore = FirebaseFirestore.getInstance();
       auth = FirebaseAuth.getInstance();


        holder.itemView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                new AlertDialog.Builder( context)
                        .setMessage( "Are You Sure You Want To Receive Your Order From "+holder.ShopName.getText().toString()+ " Shop" )
                        .setIcon( R.drawable.ic_baseline_add_business_24 )
                        .setPositiveButton( "YES", new DialogInterface.OnClickListener( ) {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick (DialogInterface dialog, int which) {
                                String shopname = holder.ShopName.getText().toString() + "\nAnd Address Is \n"+ holder.ShopAdd.getText().toString();
                                String shpname = "Order To Be Taken From " + shopname + " And Time Is " + Calendar.getInstance().getTime();
                                Map<String,String> map = new   HashMap<>();
                                map.put("ShopName", shpname);

                                firestore.collection( "CurrentUser" ).document(auth.getCurrentUser().getUid())
                                        .collection( "ShopName" ).add(map).addOnCompleteListener( new OnCompleteListener<DocumentReference>( ) {
                                    @Override
                                    public void onComplete (@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful( )) {
                                            Toast.makeText( context,"SHOP SET SUCCESSFULLY\n" +
                                                    "YOU WILL RECEIVE YOUR ORDER FROM "+ holder.ShopName.getText().toString(), Toast.LENGTH_LONG).show( );
                                        } else {
                                            Toast.makeText( context, "SORRY SHOP IS NOT SELECTED", Toast.LENGTH_LONG ).show( );
                                        }
                                    }
                                } );
                            }
                        } ).setNegativeButton( "No", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } ).show( );
            }
        } );
    }



    @Override
    public int getItemCount ( ) {
        return list.size();
    }

    public void filteredlist (ArrayList<ShopModel> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ShopImg;
        TextView ShopName;
        TextView ShopAdd;
        public ViewHolder (@NonNull View itemView) {
            super( itemView );
            ShopImg = itemView.findViewById(R.id.Shop_img);
            ShopName = itemView.findViewById(R.id.shop_name);
            ShopAdd = itemView.findViewById(R.id.shop_add);
        }
    }
}

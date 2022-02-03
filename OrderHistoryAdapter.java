package com.example.praful.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praful.Activities.MainActivity;
import com.example.praful.Activities.Orderhistory;
import com.example.praful.Models.OrderHistoryModel;
import com.example.praful.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Context context;
    List <OrderHistoryModel> list;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public OrderHistoryAdapter (Context context, List<OrderHistoryModel> list) {
        this.context = context;
        this.list = list;
        firestore= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate( R.layout.orderhistoryitems,parent,false) );
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.price.setText(list.get(position).getProductPrice().toUpperCase());
        holder.name.setText(list.get(position).getProductName());
        holder.OrdDesc.setText(String.valueOf(list.get(position).getProDesc()));
        holder.totalQuantity.setText(String.valueOf(list.get(position).getTotalQuantity()));
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));

    }
    @Override
    public int getItemCount ( ) {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,time,price,name,totalQuantity,totalPrice,OrdDesc;
        public ViewHolder (@NonNull View itemView) {
            super( itemView );
            name = itemView.findViewById(R.id.product_name);
            OrdDesc =itemView.findViewById(R.id.product_info);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}

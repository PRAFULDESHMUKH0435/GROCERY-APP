package com.example.praful.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praful.Activities.DetailedActivity;
import com.example.praful.Models.newproShowAllModel;
import com.example.praful.R;

import java.util.List;

public class newproShowAllAdapter  extends RecyclerView.Adapter<newproShowAllAdapter.ViewHolder> {
    private  Context context;
    private  List<newproShowAllModel> list;

    public newproShowAllAdapter (Context context, List<newproShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate( R.layout.newpro_show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        Glide.with( context).load( list.get( position).getImg_url()).into( holder.newproItemImg);
        holder.newproCost.setText("Rs"+(list.get(position).getPrice()));
        holder.newproName.setText(list.get(position).getName());


        holder.itemView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent( context, DetailedActivity.class);
                intent.putExtra( "detailed",list.get(position));
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount ( ) {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newproItemImg;
        TextView newproCost,newproName;
        public ViewHolder (@NonNull View itemView) {
            super( itemView );
            newproItemImg = itemView.findViewById(R.id.newpro_item_image);
            newproName = itemView.findViewById(R.id.newpro_item_nam);
            newproCost= itemView.findViewById(R.id.newpro_item_cost);
        }
    }
}

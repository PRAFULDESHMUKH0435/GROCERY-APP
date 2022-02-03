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
import com.example.praful.Models.CatShowAllModel;
import com.example.praful.R;

import java.util.List;

public class CatShowAllAdapter extends RecyclerView.Adapter<CatShowAllAdapter.ViewHolder> {

    private Context context;
    private List<CatShowAllModel> list;


    public CatShowAllAdapter (Context context, List<CatShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new CatShowAllAdapter.ViewHolder( LayoutInflater.from( parent.getContext()).inflate(R.layout.cat_show_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        Glide.with( context).load( list.get( position).getImg_url()).into( holder.CatItemImg);
        holder.CatCost.setText("Rs"+(list.get(position).getPrice()));
        holder.CatName.setText(list.get(position).getName());


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
        ImageView CatItemImg;
        TextView CatCost,CatName;
        public ViewHolder (@NonNull View itemView) {
            super( itemView );
            CatItemImg = itemView.findViewById(R.id.cat_item_image);
            CatName = itemView.findViewById(R.id.cat_item_nam);
            CatCost = itemView.findViewById(R.id.cat_item_cost);

        }
    }
}

package com.aadilmehraj.android.mvvmarch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MainViewHolder> {


    Context context;
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_list,viewGroup,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView imageView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titletextview);
            description=itemView.findViewById(R.id.descriptiontv);
            imageView=itemView.findViewById(R.id.item_imageview);
        }
    }
}

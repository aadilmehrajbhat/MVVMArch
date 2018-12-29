package com.aadilmehraj.android.mvvmarch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aadilmehraj.android.mvvmarch.model.StackApiResponse;
import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MainHolder> {

    List<StackApiResponse.Item> userList;

    public Context context;

    public Adapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_list_item,viewGroup,false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        StackApiResponse.Item item = userList.get(i);
        mainHolder.textView.setText(item.owner.display_name);
        Glide.with(context).load(item.owner.profile_image).into(mainHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if (userList !=null){
            return userList.size();
        }
        return 0;
    }

    public void setData(List<StackApiResponse.Item> data){
        userList = data;
        notifyDataSetChanged();
    }
    public class MainHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageview);
        }

    }
}

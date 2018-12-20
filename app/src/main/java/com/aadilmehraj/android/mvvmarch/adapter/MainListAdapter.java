package com.aadilmehraj.android.mvvmarch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainViewHolder> {


    private Context mContext;
    private List<Item> mItemList;

    public MainListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_list_item,viewGroup,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int i) {

        Item item = mItemList.get(i);
        holder.bind(item);
    }


    @Override
    public int getItemCount() {
        if (mItemList != null){
            return mItemList.size();
        }
        return 0;
    }


    public void setData(List<Item> itemList){
        mItemList = itemList;
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView imageView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_tv);
            description=itemView.findViewById(R.id.description_tv);
            imageView=itemView.findViewById(R.id.item_iv);
        }

        void bind(Item item){
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            Glide.with(mContext).load(item.getUrl()).into(imageView);

        }
    }
}

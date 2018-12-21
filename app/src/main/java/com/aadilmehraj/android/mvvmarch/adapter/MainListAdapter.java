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
import com.aadilmehraj.android.mvvmarch.service.model.ModelItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainViewHolder> {


    private Context mContext;
    private List<ModelItem> mModelItemList;

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

        ModelItem modelItem = mModelItemList.get(i);
        holder.bind(modelItem);
    }


    @Override
    public int getItemCount() {
        if (mModelItemList != null){
            return mModelItemList.size();
        }
        return 0;
    }


    public void setData(List<ModelItem> modelItemList){
        mModelItemList = modelItemList;
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

        void bind(ModelItem modelItem){
            title.setText(modelItem.getTitle());
            description.setText(modelItem.getDescription());
            Glide.with(mContext).load(modelItem.getUrl()).into(imageView);

        }
    }
}

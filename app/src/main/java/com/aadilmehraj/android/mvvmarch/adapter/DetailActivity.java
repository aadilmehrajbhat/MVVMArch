package com.aadilmehraj.android.mvvmarch.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.databinding.ActivityDetailBinding;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "extra-item";

    private ActivityDetailBinding mDetailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ITEM)) {
            Item item = intent.getExtras().getParcelable(EXTRA_ITEM);
            mDetailBinding.itemTitleTv.setText(item.getTitle());
            mDetailBinding.itemDescTv.setText(item.getDescription());
            Glide.with(this).load(item.getUrl()).into(mDetailBinding.itemIv);
            setSupportActionBar(mDetailBinding.detailToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

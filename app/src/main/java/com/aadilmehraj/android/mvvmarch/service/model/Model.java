package com.aadilmehraj.android.mvvmarch.service.model;

import java.util.List;

public class Model {

    private String mTitle;
    private List<Item> mItems;

    public Model() {
    }

    public Model(String title, List<Item> items) {
        mTitle = title;
        mItems = items;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    @Override
    public String toString() {
        return "Model{" +
            "mTitle='" + mTitle + '\'' +
            ", mItems=" + mItems +
            '}';
    }
}

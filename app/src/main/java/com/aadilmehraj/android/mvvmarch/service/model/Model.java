package com.aadilmehraj.android.mvvmarch.service.model;

import java.util.List;

public class Model {

    private String mTitle;
    private List<ModelItem> mModelItems;

    public Model() {
    }

    public Model(String title, List<ModelItem> modelItems) {
        mTitle = title;
        mModelItems = modelItems;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<ModelItem> getModelItems() {
        return mModelItems;
    }

    public void setModelItems(List<ModelItem> modelItems) {
        mModelItems = modelItems;
    }

    @Override
    public String toString() {
        return "Model{" +
            "mTitle='" + mTitle + '\'' +
            ", mModelItems=" + mModelItems +
            '}';
    }
}

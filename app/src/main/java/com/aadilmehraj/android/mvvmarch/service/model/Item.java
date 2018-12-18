package com.aadilmehraj.android.mvvmarch.service.model;

public class Item {

    private int mId;
    private String mTitle;
    private String mDescription;
    private boolean mIsRequired;
    private String mUrl;

    public Item() {
    }

    public Item(int id, String title, String description, boolean isRequired, String url) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mIsRequired = isRequired;
        mUrl = url;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isRequired() {
        return mIsRequired;
    }

    public void setRequired(boolean required) {
        mIsRequired = required;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "Item{" +
            "mId=" + mId +
            ", mTitle='" + mTitle + '\'' +
            ", mDescription='" + mDescription + '\'' +
            ", mIsRequired=" + mIsRequired +
            ", mUrl='" + mUrl + '\'' +
            '}';
    }
}

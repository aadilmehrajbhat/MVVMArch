package com.aadilmehraj.android.mvvmarch.service.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.PropertyName;

public class Item implements Parcelable {

    private String mId;
    private String mCategoryId;
    private String mDescription;
    private String mTitle;
    private String mUrl;

    public Item() {
    }

    public Item(String id, String categoryId, String description, String title, String url) {
        mId = id;
        mCategoryId = categoryId;
        mDescription = description;
        mTitle = title;
        mUrl = url;
    }

    protected Item(Parcel in) {
        mId = in.readString();
        mCategoryId = in.readString();
        mDescription = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCategoryId);
        dest.writeString(mDescription);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @PropertyName("cat_id")
    public String getCategoryId() {
        return mCategoryId;
    }

    @PropertyName("cat_id")
    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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
            "mId='" + mId + '\'' +
            ", mCategoryId='" + mCategoryId + '\'' +
            ", mDescription='" + mDescription + '\'' +
            ", mTitle='" + mTitle + '\'' +
            ", mUrl='" + mUrl + '\'' +
            '}';
    }
}

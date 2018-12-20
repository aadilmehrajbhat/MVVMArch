package com.aadilmehraj.android.mvvmarch.service.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.PropertyName;

public class CategoryItem implements Parcelable {

    private String mCategoryId;
    private String mId;
    private String mTitle;
    private String mUrl;

    public CategoryItem() {
    }

    public CategoryItem(String categoryId, String id, String title, String url) {
        mCategoryId = categoryId;
        mId = id;
        mTitle = title;
        mUrl = url;
    }

    protected CategoryItem(Parcel in) {
        mCategoryId = in.readString();
        mId = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategoryId);
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };

    @PropertyName("cat_id")
    public String getCategoryId() {
        return mCategoryId;
    }

    @PropertyName("cat_id")
    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
        return "CategoryItem{" +
            "mCategoryId='" + mCategoryId + '\'' +
            ", mId='" + mId + '\'' +
            ", mTitle='" + mTitle + '\'' +
            ", mUrl='" + mUrl + '\'' +
            '}';
    }
}

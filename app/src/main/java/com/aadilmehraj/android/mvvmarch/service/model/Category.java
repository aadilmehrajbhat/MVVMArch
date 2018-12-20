package com.aadilmehraj.android.mvvmarch.service.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.PropertyName;
import java.util.List;

public class Category implements Parcelable {

    private String mCategoryId;
    private String mId;
    private String mTitle;
    private String mUrl;
    private List<CategoryItem> mCategoryItems;

    public Category() {
    }

    public Category(String categoryId, String id, String title, String url,
        List<CategoryItem> categoryItems) {
        mCategoryId = categoryId;
        mId = id;
        mTitle = title;
        mUrl = url;
        mCategoryItems = categoryItems;
    }

    protected Category(Parcel in) {
        mCategoryId = in.readString();
        mId = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
        mCategoryItems = in.createTypedArrayList(CategoryItem.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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

    public List<CategoryItem> getCategoryItems() {
        return mCategoryItems;
    }

    public void setCategoryItems(
        List<CategoryItem> categoryItems) {
        mCategoryItems = categoryItems;
    }

    @Override
    public String toString() {
        return "Category{" +
            "mCategoryId='" + mCategoryId + '\'' +
            ", mId='" + mId + '\'' +
            ", mTitle='" + mTitle + '\'' +
            ", mUrl='" + mUrl + '\'' +
            ", mCategoryItems=" + mCategoryItems +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCategoryId);
        parcel.writeString(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mUrl);
        parcel.writeTypedList(mCategoryItems);
    }
}

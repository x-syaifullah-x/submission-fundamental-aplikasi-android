package com.e.myapplication.db.themoviedb.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataModel implements Parcelable {

    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private ArrayList<ResultData> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public DataModel() {
    }

    protected DataModel(Parcel in) {
        this.mPage = (Long) in.readValue(Long.class.getClassLoader());
        this.mResults = in.createTypedArrayList(ResultData.CREATOR);
        this.mTotalPages = (Long) in.readValue(Long.class.getClassLoader());
        this.mTotalResults = (Long) in.readValue(Long.class.getClassLoader());
    }

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public ArrayList<ResultData> getResults() {
        return mResults;
    }

    public void setResults(ArrayList<ResultData> results) {
        mResults = results;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mPage);
        dest.writeTypedList(this.mResults);
        dest.writeValue(this.mTotalPages);
        dest.writeValue(this.mTotalResults);
    }
}

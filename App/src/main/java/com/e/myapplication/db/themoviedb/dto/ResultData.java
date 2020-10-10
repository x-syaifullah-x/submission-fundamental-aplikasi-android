package com.e.myapplication.db.themoviedb.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultData implements Parcelable {

    public static final Parcelable.Creator<ResultData> CREATOR = new Parcelable.Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel source) {
            return new ResultData(source);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };
    @SerializedName("first_air_date")
    private String FirstAirDate;
    @SerializedName("original_name")
    private String OriginalName;
    @SerializedName("adult")
    private Boolean Adult;
    @SerializedName("backdrop_path")
    private String BackdropPath;
    @SerializedName("genre_ids")
    private List<Long> GenreIds;
    @SerializedName("id")
    private Long Id;
    @SerializedName("original_language")
    private String OriginalLanguage;
    @SerializedName("original_title")
    private String OriginalTitle;
    @SerializedName("overview")
    private String Overview;
    @SerializedName("popularity")
    private Double Popularity;
    @SerializedName("poster_path")
    private String PosterPath;
    @SerializedName("release_date")
    private String ReleaseDate;
    @SerializedName("title")
    private String Title;
    @SerializedName("video")
    private Boolean Video;
    @SerializedName("vote_average")
    private Double VoteAverage;
    @SerializedName("vote_count")
    private Long VoteCount;

    public ResultData() {
    }

    protected ResultData(Parcel in) {
        this.FirstAirDate = in.readString();
        this.OriginalName = in.readString();
        this.Adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.BackdropPath = in.readString();
        this.GenreIds = new ArrayList<Long>();
        in.readList(this.GenreIds, Long.class.getClassLoader());
        this.Id = (Long) in.readValue(Long.class.getClassLoader());
        this.OriginalLanguage = in.readString();
        this.OriginalTitle = in.readString();
        this.Overview = in.readString();
        this.Popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.PosterPath = in.readString();
        this.ReleaseDate = in.readString();
        this.Title = in.readString();
        this.Video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.VoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.VoteCount = (Long) in.readValue(Long.class.getClassLoader());
    }

    public String getFirstAirDate() {
        return FirstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        FirstAirDate = firstAirDate;
    }

    public String getOriginalName() {
        return OriginalName;
    }

    public void setOriginalName(String originalName) {
        OriginalName = originalName;
    }

    public Boolean getAdult() {
        return Adult;
    }

    public void setAdult(Boolean adult) {
        Adult = adult;
    }

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    public List<Long> getGenreIds() {
        return GenreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        GenreIds = genreIds;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getOriginalLanguage() {
        return OriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        OriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public Double getPopularity() {
        return Popularity;
    }

    public void setPopularity(Double popularity) {
        Popularity = popularity;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Boolean getVideo() {
        return Video;
    }

    public void setVideo(Boolean video) {
        Video = video;
    }

    public Double getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        VoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(Long voteCount) {
        VoteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FirstAirDate);
        dest.writeString(this.OriginalName);
        dest.writeValue(this.Adult);
        dest.writeString(this.BackdropPath);
        dest.writeList(this.GenreIds);
        dest.writeValue(this.Id);
        dest.writeString(this.OriginalLanguage);
        dest.writeString(this.OriginalTitle);
        dest.writeString(this.Overview);
        dest.writeValue(this.Popularity);
        dest.writeString(this.PosterPath);
        dest.writeString(this.ReleaseDate);
        dest.writeString(this.Title);
        dest.writeValue(this.Video);
        dest.writeValue(this.VoteAverage);
        dest.writeValue(this.VoteCount);
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}

package com.e.myapplication.db.room.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.e.myapplication.db.room.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.e.myapplication.db.room.DatabaseContract.IMAGE_NAME;
import static com.e.myapplication.db.room.DatabaseContract.ORIGINAL_LANGUAGE;
import static com.e.myapplication.db.room.DatabaseContract.OVERVIEW;
import static com.e.myapplication.db.room.DatabaseContract.RELEASE;
import static com.e.myapplication.db.room.DatabaseContract.TITLE;
import static com.e.myapplication.db.room.DatabaseContract.TYPE;
import static com.e.myapplication.db.room.DatabaseContract.USER_SCORE;

@Entity(tableName = DatabaseContract.TABLE_NAME)
public class FavoriteModel implements Parcelable {

    public static final Parcelable.Creator<FavoriteModel> CREATOR = new Parcelable.Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel source) {
            return new FavoriteModel(source);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = _ID)
    private int id;
    @ColumnInfo(name = TYPE)
    private String type;
    @ColumnInfo(name = IMAGE_NAME)
    private String image_name;
    @ColumnInfo(name = TITLE)
    private String title;
    @ColumnInfo(name = RELEASE)
    private String release;
    @ColumnInfo(name = OVERVIEW)
    private String overview;
    @ColumnInfo(name = USER_SCORE)
    private Double userScore;
    @ColumnInfo(name = ORIGINAL_LANGUAGE)
    private String originalLanguage;

    public FavoriteModel() {
    }

    protected FavoriteModel(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.image_name = in.readString();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.userScore = (Double) in.readValue(Double.class.getClassLoader());
        this.originalLanguage = in.readString();
    }

    public static FavoriteModel contentValues(ContentValues values) {
        final FavoriteModel model = new FavoriteModel();
        if (values.containsKey(TYPE)) {
            model.type = values.getAsString(TYPE);
        }
        if (values.containsKey(IMAGE_NAME)) {
            model.image_name = values.getAsString(IMAGE_NAME);
        }
        if (values.containsKey(TITLE)) {
            model.title = values.getAsString(TITLE);
        }
        if (values.containsKey(RELEASE)) {
            model.release = values.getAsString(RELEASE);
        }

        if (values.containsKey(OVERVIEW)) {
            model.overview = values.getAsString(OVERVIEW);
        }

        if (values.containsKey(USER_SCORE)) {
            model.userScore = values.getAsDouble(USER_SCORE);
        }

        if (values.containsKey(ORIGINAL_LANGUAGE)) {
            model.originalLanguage = values.getAsString(ORIGINAL_LANGUAGE);
        }
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getUserScore() {
        return userScore;
    }

    public void setUserScore(Double userScore) {
        this.userScore = userScore;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.image_name);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.overview);
        dest.writeValue(this.userScore);
        dest.writeString(this.originalLanguage);
    }
}

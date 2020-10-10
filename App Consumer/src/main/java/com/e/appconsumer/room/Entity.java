package com.e.appconsumer.room;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;


@androidx.room.Entity(tableName = DatabaseContract.TABLE_NAME)
public class Entity implements Parcelable {

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
    @PrimaryKey()
    @ColumnInfo(index = true, name = BaseColumns._ID)
    private long id;
    @ColumnInfo(name = DatabaseContract.TYPE)
    private String type;
    @ColumnInfo(name = DatabaseContract.IMAGE_NAME)
    private String image_name;
    @ColumnInfo(name = DatabaseContract.TITLE)
    private String title;
    @ColumnInfo(name = DatabaseContract.RELEASE)
    private String release;
    @ColumnInfo(name = DatabaseContract.OVERVIEW)
    private String overview;
    @ColumnInfo(name = DatabaseContract.USER_SCORE)
    private Double userScore;
    @ColumnInfo(name = DatabaseContract.ORIGINAL_LANGUAGE)
    private String originalLanguage;

    public Entity() {
    }

    protected Entity(Parcel in) {
        id = in.readInt();
        type = in.readString();
        image_name = in.readString();
        title = in.readString();
        release = in.readString();
        overview = in.readString();
        if (in.readByte() == 0) {
            userScore = null;
        } else {
            userScore = in.readDouble();
        }
        originalLanguage = in.readString();
    }

    public static Entity contentValues(ContentValues values) {
        final Entity model = new Entity();
        if (values.containsKey(DatabaseContract.TYPE)) {
            model.type = values.getAsString(DatabaseContract.TYPE);
        }
        if (values.containsKey(DatabaseContract.IMAGE_NAME)) {
            model.image_name = values.getAsString(DatabaseContract.IMAGE_NAME);
        }
        if (values.containsKey(DatabaseContract.TITLE)) {
            model.title = values.getAsString(DatabaseContract.TITLE);
        }
        if (values.containsKey(DatabaseContract.RELEASE)) {
            model.release = values.getAsString(DatabaseContract.RELEASE);
        }

        if (values.containsKey(DatabaseContract.OVERVIEW)) {
            model.overview = values.getAsString(DatabaseContract.OVERVIEW);
        }

        if (values.containsKey(DatabaseContract.USER_SCORE)) {
            model.userScore = values.getAsDouble(DatabaseContract.USER_SCORE);
        }

        if (values.containsKey(DatabaseContract.ORIGINAL_LANGUAGE)) {
            model.originalLanguage = values.getAsString(DatabaseContract.ORIGINAL_LANGUAGE);
        }
        return model;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(type);
        dest.writeString(image_name);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(overview);
        if (userScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(userScore);
        }
        dest.writeString(originalLanguage);
    }

    public long getId() {
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
}

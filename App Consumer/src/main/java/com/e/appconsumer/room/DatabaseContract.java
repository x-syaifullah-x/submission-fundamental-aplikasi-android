package com.e.appconsumer.room;

import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class DatabaseContract implements BaseColumns {

    public static final String AUTHORITY = "com.e.myapplication";

    //    public static final String ID = "id";
    public static final String TABLE_NAME = "db_favorite";
    public static final String TYPE = "type";
    public static final String IMAGE_NAME = "image_name";
    public static final String TITLE = "title";
    public static final String RELEASE = "release";
    public static final String OVERVIEW = "overview";
    public static final String USER_SCORE = "userScore";
    public static final String ORIGINAL_LANGUAGE = "originalLanguage";
    private static final String SCHEME = "content";


    // content://com.e.myapplication/path_image/image_name
    public static Uri getUriImage(String pathImage, @Nullable String imageName) {
        return new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(pathImage).appendPath(imageName).build();
    }

    // content://com.e.myapplication/table_name
    public static Uri getUri() {
        return new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build();
    }
}

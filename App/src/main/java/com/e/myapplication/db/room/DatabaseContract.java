package com.e.myapplication.db.room;

import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class DatabaseContract implements BaseColumns {

    public static final String AUTHORITY = "com.e.myapplication";

    public static final String TABLE_NAME = "db_favorite";
    public static final String TYPE = "type";
    public static final String IMAGE_NAME = "image_name";
    public static final String TITLE = "title";
    public static final String RELEASE = "release";
    public static final String OVERVIEW = "overview";
    public static final String USER_SCORE = "userScore";
    public static final String ORIGINAL_LANGUAGE = "originalLanguage";
    private static final String SCHEME_CONTENT = "content";

    /* untuk membuat URI content://com.e.myapplication/table_name */
    public static final Uri CONTENT_URI = new Builder()
            .scheme(SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static Uri getUriImage(String pathImage, @Nullable String id) {
        return new Builder().scheme(SCHEME_CONTENT).authority(AUTHORITY).appendPath(pathImage).appendPath(id).build();
    }
}

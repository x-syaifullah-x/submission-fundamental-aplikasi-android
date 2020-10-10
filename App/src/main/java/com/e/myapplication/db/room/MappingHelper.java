package com.e.myapplication.db.room;

import android.content.ContentValues;
import android.database.Cursor;

import com.e.myapplication.db.room.entity.FavoriteModel;
import com.e.myapplication.db.themoviedb.dto.ResultData;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.e.myapplication.db.room.DatabaseContract.IMAGE_NAME;
import static com.e.myapplication.db.room.DatabaseContract.ORIGINAL_LANGUAGE;
import static com.e.myapplication.db.room.DatabaseContract.OVERVIEW;
import static com.e.myapplication.db.room.DatabaseContract.RELEASE;
import static com.e.myapplication.db.room.DatabaseContract.TITLE;
import static com.e.myapplication.db.room.DatabaseContract.TYPE;
import static com.e.myapplication.db.room.DatabaseContract.USER_SCORE;

public class MappingHelper {

    public static ArrayList<FavoriteModel> cursorToArrayList(Cursor cursor) {
        ArrayList<FavoriteModel> models = new ArrayList<>();
        while (cursor.moveToNext()) {
            FavoriteModel model = new FavoriteModel();
            model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            model.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
            model.setImage_name(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_NAME)));
            model.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            model.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
            model.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
            model.setUserScore(cursor.getDouble(cursor.getColumnIndexOrThrow(USER_SCORE)));
            model.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
            models.add(model);
        }
        return models;
    }

    //  Add Data
    public static ContentValues values(ResultData model, String type) {
        ContentValues values = new ContentValues();
        if (type.equalsIgnoreCase("movies")) {
            values.put(TITLE, model.getTitle());
            values.put(RELEASE, model.getReleaseDate());
        } else {
            values.put(TITLE, model.getOriginalName());
            values.put(RELEASE, model.getFirstAirDate());
        }
        values.put(OVERVIEW, model.getOverview());
        values.put(ORIGINAL_LANGUAGE, model.getOriginalLanguage());
        values.put(USER_SCORE, model.getVoteAverage());
        values.put(TYPE, type);
        values.put(IMAGE_NAME, model.getPosterPath());
        return values;
    }
}

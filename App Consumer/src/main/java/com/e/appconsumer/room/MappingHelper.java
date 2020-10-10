package com.e.appconsumer.room;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class MappingHelper {

    public static ArrayList<Entity> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Entity> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            Entity model = new Entity();
            model.setId(notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID)));
            model.setType(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TYPE)));
            model.setImage_name(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.IMAGE_NAME)));
            model.setTitle(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TITLE)));
            model.setRelease(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.RELEASE)));
            model.setOverview(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.OVERVIEW)));
            model.setUserScore(notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(DatabaseContract.USER_SCORE)));
            model.setOriginalLanguage(notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.ORIGINAL_LANGUAGE)));
            notesList.add(model);
        }
        return notesList;
    }
}

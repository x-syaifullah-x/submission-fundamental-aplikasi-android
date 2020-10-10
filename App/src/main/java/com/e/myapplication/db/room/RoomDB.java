package com.e.myapplication.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.e.myapplication.db.room.entity.FavoriteModel;

import static androidx.room.Room.databaseBuilder;
import static com.e.myapplication.db.room.DatabaseContract.TABLE_NAME;

@Database(entities = {FavoriteModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static volatile RoomDB INSTANCE;

    public static RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, TABLE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RoomDao theMoviesDbDao();
}

package com.e.myapplication.db.room;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.e.myapplication.db.room.entity.FavoriteModel;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM db_favorite ORDER BY _id DESC")
    Cursor getAllData();

    @Query("SELECT * FROM db_favorite WHERE type IN (:type) ORDER BY _id DESC")
    Cursor getDataByType(String type);

    @Query("SELECT * FROM db_favorite WHERE title IN (:title) ORDER BY _id DESC")
    boolean getDataByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertData(FavoriteModel data);

    @Query("DELETE FROM db_favorite WHERE _id = :id")
    int deleteById(String id);
}

package com.e.myapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import static com.e.myapplication.db.room.DatabaseContract.CONTENT_URI;

public class LoadDataFavorite extends AsyncTask<String, Void, Cursor> {

    private WeakReference<Context> context;
    private WeakReference<OnLoadData> onLoadData;

    public LoadDataFavorite(Context context, OnLoadData onLoadData) {
        this.onLoadData = new WeakReference<>(onLoadData);
        this.context = new WeakReference<>(context);
    }

    @Override
    protected Cursor doInBackground(String... strings) {
        return context.get().getContentResolver().query(
                CONTENT_URI, null, null, null, strings.length > 0 ? strings[0] : null
        );
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        onLoadData.get().onLoadData(cursor);
    }

    public interface OnLoadData {
        void onLoadData(Cursor dataCursor);
    }
}

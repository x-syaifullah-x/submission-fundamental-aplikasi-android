package com.e.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.e.myapplication.db.room.DatabaseContract;
import com.e.myapplication.db.room.entity.FavoriteModel;
import com.e.myapplication.widget.FavoriteWidget;

import java.lang.ref.WeakReference;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;

public class DeleteDataFavorite extends AsyncTask<FavoriteModel, Void, Boolean> {
    private WeakReference<Context> mContext;
    private WeakReference<OnDelete> mOnDelete;
    private int position;

    public DeleteDataFavorite(Context context, OnDelete onDelete, int position) {
        this.mContext = new WeakReference<>(context);
        this.mOnDelete = new WeakReference<>(onDelete);
        this.position = position;
    }

    @Override
    protected Boolean doInBackground(FavoriteModel... models) {
        return mContext.get().getContentResolver().delete(Uri.parse((DatabaseContract.CONTENT_URI + "/" + models[0].getId())), null, null) != 0;
    }

    @Override
    protected void onPostExecute(Boolean succsess) {
        super.onPostExecute(succsess);
        mOnDelete.get().onDelete(mContext.get(), succsess, position);
        mContext.get().sendBroadcast(new Intent(mContext.get(), FavoriteWidget.class).setAction(ACTION_APPWIDGET_UPDATE));
    }

    public interface OnDelete {
        void onDelete(Context context, boolean success, int position);
    }
}

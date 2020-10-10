package com.e.myapplication.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.e.myapplication.R;
import com.e.myapplication.db.room.entity.FavoriteModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.graphics.BitmapFactory.decodeStream;
import static android.os.Binder.clearCallingIdentity;
import static android.os.Binder.restoreCallingIdentity;
import static com.e.myapplication.db.room.DatabaseContract.CONTENT_URI;
import static com.e.myapplication.db.room.DatabaseContract.getUriImage;
import static com.e.myapplication.db.room.MappingHelper.cursorToArrayList;
import static com.e.myapplication.widget.FavoriteWidget.EXTRA_ITEM;
import static java.lang.String.valueOf;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mCtx;
    private ArrayList<FavoriteModel> data = new ArrayList<>();


    public StackRemoteViewsFactory(Context context) {
        this.mCtx = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = clearCallingIdentity();
        Cursor cursor = mCtx.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null)
            data = cursorToArrayList(cursor);
        restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        data.clear();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mCtx.getPackageName(), R.layout.widget_item);
        try {
            if (data.size() > 0) {
                final long identityToken = clearCallingIdentity();
                remoteViews.setImageViewBitmap(R.id.imageView, Glide.with(mCtx)
                        .asBitmap()
                        .load(decodeStream(mCtx.getContentResolver().openInputStream(getUriImage("image_data", valueOf(data.get(position).getId())))))
                        .submit().get());
                restoreCallingIdentity(identityToken);
                remoteViews.setOnClickFillInIntent(R.id.imageView, new Intent().putExtra(EXTRA_ITEM, data.get(position).getTitle()));
            }
        } catch (ExecutionException | InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}

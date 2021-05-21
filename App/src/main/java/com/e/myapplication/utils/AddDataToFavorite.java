package com.e.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;

import com.e.myapplication.R;
import com.e.myapplication.db.themoviedb.dto.ResultData;
import com.e.myapplication.widget.FavoriteWidget;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.text.TextUtils.isEmpty;
import static com.e.myapplication.db.room.DatabaseContract.CONTENT_URI;
import static com.e.myapplication.db.room.MappingHelper.values;
import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;
import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

public class AddDataToFavorite extends AsyncTask<ResultData, Void, String> {
    private WeakReference<View> mView;
    private String mResult;
    private Drawable drawable;
    private File mDir;
    private WeakReference<Context> mCtx;

    public AddDataToFavorite(View view, Drawable drawable) {
        this.drawable = drawable;
        this.mView = new WeakReference<>(view);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCtx = new WeakReference<>(mView.get().getContext());
        mDir = new File(mCtx.get().getFilesDir().getPath(), "image_data");
    }

    @Override
    protected String doInBackground(ResultData... data) {
        for (ResultData rd : data) {
            String type = isEmpty(rd.toString()) ? "tv" : "movies";
            try {
                ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
                ((BitmapDrawable) drawable).getBitmap().compress(PNG, 100, mBaos);
                mBaos.close();

                if (!mDir.exists()) mDir.mkdirs();

                /* insert data to database */
                int id = parseInt(requireNonNull(requireNonNull(mCtx.get().getContentResolver().insert(CONTENT_URI, values(rd, type))).getLastPathSegment()));
                if (id != 0) {
                    new FileOutputStream(mDir.getPath() + "/" + id).write(mBaos.toByteArray()); /* put file byte image to storage */
                    mCtx.get().sendBroadcast(new Intent(mCtx.get(), FavoriteWidget.class).setAction(ACTION_APPWIDGET_UPDATE));
                    mResult = mCtx.get().getString(R.string.message_success_add_to_favorite, type);
                } else
                    mResult = mCtx.get().getString(R.string.message_data_ready, isEmpty(rd.toString()) ? rd.getOriginalName() : rd.getTitle());
            } catch (IOException | RuntimeException e) {
                mResult = mCtx.get().getString(R.string.message_image_failure, type);
            }
        }
        return mResult;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Snackbar.make(mView.get(), result, LENGTH_LONG).show();
    }
}
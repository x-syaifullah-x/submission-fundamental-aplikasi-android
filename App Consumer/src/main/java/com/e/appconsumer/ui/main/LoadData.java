package com.e.appconsumer.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.e.appconsumer.adapter.FavoriteAdapter;
import com.e.appconsumer.databinding.FragmentMainBinding;
import com.e.appconsumer.room.MappingHelper;

import java.lang.ref.WeakReference;

import static android.view.View.GONE;
import static com.e.appconsumer.room.DatabaseContract.getUri;

public class LoadData extends AsyncTask<String, Void, Cursor> {

    private WeakReference<FragmentMainBinding> mBinding;
    private WeakReference<Context> mContex;

    public LoadData(FragmentMainBinding favoriteContentBinding) {
        this.mBinding = new WeakReference<>(favoriteContentBinding);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mContex = new WeakReference<>(mBinding.get().getRoot().getContext());

    }

    @Override
    protected Cursor doInBackground(String... strings) {
        return mContex.get().getContentResolver().query(
                getUri(), null, null, null, strings[0]
        );
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mBinding.get().progressBar.setVisibility(GONE);
        new FavoriteAdapter().setData(MappingHelper.mapCursorToArrayList(cursor), mBinding.get().rv);
    }
}

package com.e.myapplication.ui.favorite;

import android.app.Application;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.myapplication.utils.LoadDataFavorite;
import com.e.myapplication.utils.LoadDataFavorite.OnLoadData;

import static com.e.myapplication.db.room.DatabaseContract.CONTENT_URI;

public class FavoriteViewModel extends AndroidViewModel implements OnLoadData {
    private MutableLiveData<Cursor> data = new MutableLiveData<>();
    private String arg;

    public FavoriteViewModel(@NonNull Application application, String arg) {
        super(application);
        this.arg = arg;
        getApplication().getApplicationContext().getContentResolver().registerContentObserver(CONTENT_URI, true, new MyObserver(new Handler()));
        new LoadDataFavorite(getApplication(), this).execute(arg);
    }

    public LiveData<Cursor> getData() {
        return data;
    }

    @Override
    public void onLoadData(Cursor dataCursor) {
        data.setValue(dataCursor);
    }

    class MyObserver extends ContentObserver {
        MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadDataFavorite(getApplication(), FavoriteViewModel.this).execute(arg);
        }
    }
}

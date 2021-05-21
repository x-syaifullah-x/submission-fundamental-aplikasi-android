package com.e.myapplication.ui.movie_tv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.e.myapplication.R;
import com.e.myapplication.db.themoviedb.TheMoviesRepository;
import com.e.myapplication.db.themoviedb.dto.DataModel;

import static com.e.myapplication.BuildConfig.THE_MOVIE_DB_API;

public class MovieTvViewModel extends AndroidViewModel {
    private LiveData<DataModel> mutableLiveData;

    public MovieTvViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<DataModel> getData(String... str) {
        if (mutableLiveData == null) {
            mutableLiveData = new TheMoviesRepository().getData(
                    "discover",                                 /* discover or search        */
                    str[0],                                          /* tv or movie               */
                    THE_MOVIE_DB_API,                                /* api                       */
                    getApplication().getString(R.string.language),                                            /* language                  */
                    null,                                            /* query                     */
                    str.length > 1 ? str[1] : null,                  /* Date1                     */
                    str.length > 1 ? str[1] : null                   /* Date2                     */
            );
        }
        return mutableLiveData;
    }

    public LiveData<DataModel> searchData(String... str) {
        mutableLiveData = new TheMoviesRepository().getData(
                str[0],                                         /* discover or search        */
                str[1],                                              /* tv or movie               */
                THE_MOVIE_DB_API,                                    /* api                       */
                getApplication().getString(R.string.language),                                                /* language                  */
                str.length > 2 ? str[2] : null,                      /* query                     */
                null,                                                /* Date1                     */
                null                                                 /* Date2                     */
        );
        return mutableLiveData;
    }
}
package com.e.myapplication.db.themoviedb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.myapplication.db.themoviedb.dto.DataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.e.myapplication.ui.dialog.DialogErrorFragment.KEY_ERROR;
import static com.e.myapplication.ui.dialog.DialogErrorFragment.error;

public class TheMoviesRepository {

    private TheMoviesDao apiRequest;
    private MutableLiveData<DataModel> data = new MutableLiveData<>();

    public TheMoviesRepository() {
        apiRequest = RetrofitClient.getClient().create(TheMoviesDao.class);
    }

    public LiveData<DataModel> getData(String... s) {
        /* [0]_request, [1]_tv-movie, [2]_api_key, [3]_language, [4]_query, [5]_primary_release_date.gte, [6]_primary_release_date.lte */
        apiRequest.getData(s[0], s[1], s[2], s[3], s[4], s[5], s[6]).enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.body() != null) {
                    data.postValue(response.body());
                } else {
                    if (response.code() != 200) {
                        data.postValue(null);
                        error.put(KEY_ERROR, "Respons Server " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                data.postValue(null);
                error.put(KEY_ERROR, t.getMessage());
            }
        });
        return data;
    }
}

package com.e.myapplication.ui.search;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.e.myapplication.R;
import com.e.myapplication.adapter.MovieTvAdapter;
import com.e.myapplication.adapter.ViewHolderTv;
import com.e.myapplication.base.BaseActivity;
import com.e.myapplication.databinding.ActivitySearchResultBinding;
import com.e.myapplication.ui.movie_tv.MovieTvViewModel;

import java.util.ArrayList;

import static android.app.SearchManager.QUERY;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.e.myapplication.ui.search.SearchData.DATA_EXTRA;
import static java.util.Objects.requireNonNull;

public class SearchResultActivity extends BaseActivity<ActivitySearchResultBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);

        setSupportActionBar(binding.toolbar);
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        MovieTvAdapter adapter = new MovieTvAdapter(R.layout.movies_tv_grid_item, ViewHolderTv.class, new ArrayList<>(), null);
        binding.rv.setAdapter(adapter);

        if (!requireNonNull(getIntent().getStringExtra(DATA_EXTRA)).equals("favorite")) {
            new ViewModelProvider(this)
                    .get(MovieTvViewModel.class).searchData("search", getIntent().getStringExtra(DATA_EXTRA), getIntent().getStringExtra(QUERY), null, null)
                    .observe(this, dataModel -> {
                        if (dataModel != null) {
                            if (dataModel.getResults().size() > 0)
                                adapter.setModel(dataModel.getResults());
                            else
                                binding.noData.setVisibility(VISIBLE);
                        } else
                            binding.noData.setVisibility(VISIBLE);

                        binding.progressBar.setVisibility(GONE);
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}

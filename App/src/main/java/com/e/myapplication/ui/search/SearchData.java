package com.e.myapplication.ui.search;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.myapplication.R;
import com.e.myapplication.adapter.FavoriteAdapter;
import com.e.myapplication.adapter.MovieTvAdapter;
import com.e.myapplication.adapter.OnItemClick;
import com.e.myapplication.adapter.ViewHolderFavorite;
import com.e.myapplication.adapter.ViewHolderTv;
import com.e.myapplication.databinding.SearchInDataBinding;
import com.e.myapplication.db.room.MappingHelper;
import com.e.myapplication.db.themoviedb.dto.ResultData;
import com.e.myapplication.ui.movie_tv.MovieTvViewModel;
import com.e.myapplication.utils.LoadDataFavorite;
import com.e.myapplication.utils.LoadDataFavorite.OnLoadData;

import java.util.ArrayList;

import static android.app.SearchManager.QUERY;
import static android.content.Intent.ACTION_SEARCH;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchData extends Fragment implements OnItemClick<ViewHolderTv, ResultData>, OnLoadData {
    static final String TYPE = "MOVIE_TV_FAVORITE";
    static final String DATA_EXTRA = "DATA_EXTRA";
    private SearchInDataBinding binding;
    private SearchView searchView;
    private Runnable runnable;
    private MovieTvAdapter adapter;
    private FavoriteAdapter favoriteAdapter;
    private final Handler handler = new Handler();
    private String type;


    public SearchData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_in_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = SearchInDataBinding.bind(view);

        if (getArguments() != null)
            type = getArguments().getString(TYPE);

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));

        if ("favorite".equals(type)) {
            new LoadDataFavorite(getContext(), this).execute();
            binding.noData.setVisibility(GONE);
            favoriteAdapter = new FavoriteAdapter(R.layout.favorite_list_item, ViewHolderFavorite.class, new ArrayList<>());
            binding.rv.setAdapter(favoriteAdapter);
        } else {
            adapter = new MovieTvAdapter(R.layout.search_list_item, ViewHolderTv.class, new ArrayList<>(), this);
            binding.rv.setAdapter(adapter);
        }

        searchView = view.getRootView().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                if ("favorite".equals(type)) {
                    favoriteAdapter.filter(s, getContext());
                } else {
                    runnable = () -> setData(s);
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 200);
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!"favorite".equals(type)) {
                    Intent intent = new Intent(getContext(), SearchResult.class);
                    intent.putExtra(DATA_EXTRA, type);
                    intent.putExtra(QUERY, query);
                    intent.setAction(ACTION_SEARCH);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void setData(String query) {
        binding.progressBar.setVisibility(VISIBLE);
        new ViewModelProvider(this).get(MovieTvViewModel.class)
                .searchData("search", type, query)
                .observe(this, dataModel -> {
                    if (!isEmpty(query)) {
                        if (dataModel != null) {
                            if (dataModel.getResults().size() > 0) {
                                adapter.setModel(dataModel.getResults());
                                binding.progressBar.setVisibility(GONE);
                                binding.noData.setVisibility(GONE);
                            } else noData(query, "");
                        } else noData(query, getString(R.string.no_connection));
                    } else {
                        noData(query, "");
                        binding.noData.setText(getString(R.string.search, type));
                    }
                });
    }

    private void noData(String query, String message) {
        adapter.setModel(new ArrayList<>());
        binding.progressBar.setVisibility(GONE);
        binding.noData.setVisibility(VISIBLE);
        binding.noData.setText(getString(R.string.no_data, query.toUpperCase(), message));
    }

    @Override
    public void onItemClick(ViewHolderTv viewHolder, ResultData resultData, int position) {
        if (resultData != null)
            viewHolder.itemView.setOnClickListener(v ->
                    searchView.setQuery(!isEmpty(resultData.toString()) ? resultData.getTitle() : resultData.getOriginalName(), true));
    }

    @Override
    public void onLoadData(Cursor dataCursor) {
        if (favoriteAdapter != null)
            favoriteAdapter.setModel(MappingHelper.cursorToArrayList(dataCursor));
    }
}

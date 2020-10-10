package com.e.myapplication.ui.movie_tv;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.myapplication.R;
import com.e.myapplication.adapter.MovieTvAdapter;
import com.e.myapplication.adapter.OnItemClick;
import com.e.myapplication.adapter.ViewHolderMovieTv;
import com.e.myapplication.base.BaseFragment;
import com.e.myapplication.databinding.MoviesTvshowBinding;
import com.e.myapplication.db.themoviedb.dto.ResultData;
import com.e.myapplication.ui.dialog.DialogErrorFragment;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.INVISIBLE;
import static androidx.navigation.Navigation.findNavController;
import static com.e.myapplication.R.layout.movies_tv_card_item;
import static com.e.myapplication.ui.prefs.Settings.SettingsFragment.KEY_LAYOUT_MODE;

public class MovieTvFragment extends BaseFragment<MoviesTvshowBinding> implements OnItemClick<ViewHolderMovieTv, ResultData> {
    private static NavDestination destination;
    private final String STAT_SCROLL_POSITION_MOVIES = "STAT_SCROLL_POSITION_MOVIES";
    private final String STAT_SCROLL_POSITION_TV_SHOW = "STAT_SCROLL_POSITION_TV";
    private Parcelable statScrollMovies;
    private Parcelable statScrollTvShow;

    public MovieTvFragment() {
    }

    @Override
    protected int resFragmentLayout() {
        return R.layout.movies_tvshow;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = binding.getRoot().getContext();

        destination = findNavController(view).getCurrentDestination();

        int layoutMode = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).getInt(KEY_LAYOUT_MODE, R.layout.movies_tv_grid_item);

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(layoutMode == movies_tv_card_item ? new LinearLayoutManager(context) : new GridLayoutManager(context, 2));
        MovieTvAdapter adapter = new MovieTvAdapter(layoutMode, ViewHolderMovieTv.class, new ArrayList<>(), this);
        binding.rv.setAdapter(adapter);

        new ViewModelProvider(this).get(MovieTvViewModel.class)
                .getData(destination.getId() == R.id.navigation_movies ? "movie" /* set movies url */ : "tv" /* set tv show url */)
                .observe(getViewLifecycleOwner(), result -> {
                    if (result != null) {
                        adapter.setModel(result.getResults());
                        if (binding.rv.getLayoutManager() != null) {
                            if (destination.getId() == R.id.navigation_movies) {
                                binding.rv.getLayoutManager().onRestoreInstanceState(statScrollMovies);
                            } else if (destination.getId() == R.id.navigation_tv_show) {
                                binding.rv.getLayoutManager().onRestoreInstanceState(statScrollTvShow);
                            }
                        }
                        binding.progressBar.setVisibility(INVISIBLE);
                    } else {
                        binding.progressBar.setVisibility(INVISIBLE);
                        new DialogErrorFragment().show(getChildFragmentManager(), "error");
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding.rv.getLayoutManager() != null) {
            if (destination.getId() == R.id.navigation_movies) {
                statScrollMovies = binding.rv.getLayoutManager().onSaveInstanceState();
            } else if (destination.getId() == R.id.navigation_tv_show) {
                statScrollTvShow = binding.rv.getLayoutManager().onSaveInstanceState();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (binding != null && binding.rv.getLayoutManager() != null) {
            if (destination.getId() == R.id.navigation_movies) {
                outState.putParcelable(STAT_SCROLL_POSITION_MOVIES, binding.rv.getLayoutManager().onSaveInstanceState());
            } else if (destination.getId() == R.id.navigation_tv_show) {
                outState.putParcelable(STAT_SCROLL_POSITION_TV_SHOW, binding.rv.getLayoutManager().onSaveInstanceState());
            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            statScrollMovies = savedInstanceState.getParcelable(STAT_SCROLL_POSITION_MOVIES);
            statScrollTvShow = savedInstanceState.getParcelable(STAT_SCROLL_POSITION_TV_SHOW);
        }
    }

    @Override
    public void onItemClick(ViewHolderMovieTv viewHolder, ResultData data, int position) {
        viewHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("data" /* key argument detail fragment */, data);
            switch (destination.getId()) {
                case R.id.navigation_movies:
                    findNavController(binding.getRoot()).navigate(R.id.action_moviesFragment_to_detailFragment, bundle);
                    break;
                case R.id.navigation_tv_show:
                    findNavController(binding.getRoot()).navigate(R.id.action_tvShowFragment_to_detailFragment, bundle);
                    break;
            }
        });
    }
}
package com.e.myapplication.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.databinding.MoviesTvCardItemBinding;
import com.e.myapplication.databinding.MoviesTvGridItemBinding;
import com.e.myapplication.databinding.SearchListItemBinding;
import com.e.myapplication.db.themoviedb.dto.ResultData;

public class ViewHolderMovieTv extends RecyclerView.ViewHolder {
    private MoviesTvCardItemBinding cardItemBinding;
    private MoviesTvGridItemBinding gridItemBinding;
    private SearchListItemBinding searchListItemBinding;

    public ViewHolderMovieTv(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(ResultData result, int layout) {
        switch (layout) {
            case R.layout.movies_tv_card_item:
                if (cardItemBinding == null) {
                    cardItemBinding = MoviesTvCardItemBinding.bind(itemView);
                }
                cardItemBinding.setData(result);
                cardItemBinding.executePendingBindings();
                break;
            case R.layout.movies_tv_grid_item:
                if (gridItemBinding == null) {
                    gridItemBinding = MoviesTvGridItemBinding.bind(itemView);
                }
                gridItemBinding.setData(result);
                gridItemBinding.executePendingBindings();
                break;
            case R.layout.search_list_item:
                if (searchListItemBinding == null) {
                    searchListItemBinding = SearchListItemBinding.bind(itemView);
                }
                searchListItemBinding.setData(result);
                searchListItemBinding.executePendingBindings();
                break;
        }
    }
}

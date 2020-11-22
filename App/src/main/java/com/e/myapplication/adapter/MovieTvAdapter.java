package com.e.myapplication.adapter;

import com.e.myapplication.base.BaseAdapter;
import com.e.myapplication.db.themoviedb.dto.ResultData;

import java.util.ArrayList;

public class MovieTvAdapter extends BaseAdapter<ResultData, ViewHolderTv> {
    private final OnItemClick<ViewHolderTv, ResultData> onItemClick;

    public MovieTvAdapter(int layout, Class<ViewHolderTv> viewHolder, ArrayList<ResultData> model, OnItemClick<ViewHolderTv, ResultData> onItemClick) {
        super(layout, viewHolder, model);
        this.onItemClick = onItemClick;
    }

    @Override
    protected void bind(int layout, ViewHolderTv holder, ResultData resultData, int position) {
        holder.bind(resultData, layout);
        if (onItemClick != null) {
            onItemClick.onItemClick(holder, resultData, position);
        }
    }
}

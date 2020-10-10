package com.e.myapplication.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.databinding.FavoriteListItemBinding;

public class ViewHolderFavorite extends RecyclerView.ViewHolder {
    FavoriteListItemBinding biding;

    public ViewHolderFavorite(@NonNull View itemView) {
        super(itemView);
        biding = FavoriteListItemBinding.bind(itemView);
    }
}

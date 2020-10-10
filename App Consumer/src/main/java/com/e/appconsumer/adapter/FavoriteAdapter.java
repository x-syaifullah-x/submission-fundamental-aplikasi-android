package com.e.appconsumer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.appconsumer.R;
import com.e.appconsumer.databinding.ListItemBinding;
import com.e.appconsumer.room.Entity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MoviesTvViewHolder> {
    private ArrayList<Entity> data = new ArrayList<>();

    public void setData(List<Entity> data, RecyclerView recyclerView) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(this);
    }

    @NonNull
    @Override
    public MoviesTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesTvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesTvViewHolder holder, final int position) {
        final Entity data = this.data.get(position);
        holder.biding.setData(data);
        holder.biding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MoviesTvViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding biding;

        MoviesTvViewHolder(@NonNull View itemView) {
            super(itemView);
            biding = ListItemBinding.bind(itemView);
        }
    }
}

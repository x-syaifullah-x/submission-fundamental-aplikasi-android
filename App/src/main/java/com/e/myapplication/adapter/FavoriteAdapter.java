package com.e.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;

import androidx.appcompat.app.AlertDialog;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseAdapter;
import com.e.myapplication.db.room.entity.FavoriteModel;
import com.e.myapplication.utils.DeleteDataFavorite;
import com.e.myapplication.utils.DeleteDataFavorite.OnDelete;
import com.e.myapplication.utils.LoadDataFavorite;
import com.e.myapplication.utils.LoadDataFavorite.OnLoadData;

import java.util.ArrayList;

import static com.e.myapplication.db.room.MappingHelper.cursorToArrayList;

public class FavoriteAdapter extends BaseAdapter<FavoriteModel, ViewHolderFavorite> implements OnDelete, OnLoadData {
    private String query;

    public FavoriteAdapter(int layout, Class<ViewHolderFavorite> viewHolder, ArrayList<FavoriteModel> model) {
        super(layout, viewHolder, model);
    }

    @Override
    protected void bind(int layout, ViewHolderFavorite holder, FavoriteModel favoriteModel, int position) {
        holder.biding.setData(favoriteModel);
        holder.biding.btnDelete.setOnClickListener(v -> itemDelete(holder.itemView.getContext(), favoriteModel, position));
    }

    private void itemDelete(Context context, FavoriteModel model, int position) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.delete_data_favorite))
                .setMessage(context.getString(R.string.message_delete_data_favorite, model.getTitle()))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.confirm_delete_data_favorite), (dialog, which) ->
                        new DeleteDataFavorite(context, this, position).execute(model))
                .setNegativeButton(context.getString(R.string.cencel_delete_data_favorite), null).show();
    }

    @Override
    public void onDelete(Context context, boolean success, int position) {
        if (success) {
            notifyItemRemoved(position);
        }
    }

    public void filter(String query, Context context) {
        this.query = query;
        LoadDataFavorite loadDataFavorite = new LoadDataFavorite(context, this);
        loadDataFavorite.execute();
    }

    @Override
    public void onLoadData(Cursor dataCursor) {
        ArrayList<FavoriteModel> models = new ArrayList<>();
        for (FavoriteModel model : cursorToArrayList(dataCursor)) {
            if (model.getTitle().toLowerCase().contains(query.toLowerCase()))
                models.add(model);
        }
        setModel(models);
    }
}

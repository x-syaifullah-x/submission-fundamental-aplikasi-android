package com.e.myapplication.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static android.view.LayoutInflater.from;

public abstract class BaseAdapter<MODEL, VH extends ViewHolder> extends Adapter<VH> {

    private int layout;
    private Class<VH> viewHolder;
    private ArrayList<MODEL> model;

    public BaseAdapter(int layout, Class<VH> viewHolder, ArrayList<MODEL> model) {
        this.layout = layout;
        this.viewHolder = viewHolder;
        this.model = model;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            Constructor<VH> constructor = viewHolder.getConstructor(View.class);
            return constructor.newInstance(from(parent.getContext()).inflate(layout, parent, false));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        bind(this.layout, holder, this.model.get(position), position);

    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    abstract protected void bind(int layout, VH holder, MODEL model, int position);

    public void setModel(ArrayList<MODEL> model) {
        this.model.clear();
        this.model.addAll(model);
        notifyDataSetChanged();
    }
}

package com.e.myapplication.adapter;

public interface OnItemClick<VH, MODEL> {
    void onItemClick(VH viewHolder, MODEL model, int position);
}

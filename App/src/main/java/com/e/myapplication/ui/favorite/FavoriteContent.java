package com.e.myapplication.ui.favorite;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.myapplication.R;
import com.e.myapplication.adapter.FavoriteAdapter;
import com.e.myapplication.adapter.ViewHolderFavorite;
import com.e.myapplication.base.BaseFragment;
import com.e.myapplication.databinding.FavoriteContentBinding;
import com.e.myapplication.db.room.MappingHelper;

import java.util.ArrayList;

public class FavoriteContent extends BaseFragment<FavoriteContentBinding> {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private FavoriteAdapter adapter;

    static FavoriteContent newInstance(int index) {
        FavoriteContent fragment = new FavoriteContent();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int resFragmentLayout() {
        return R.layout.favorite_content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteAdapter(R.layout.favorite_list_item, ViewHolderFavorite.class, new ArrayList<>());
        binding.rv.setAdapter(adapter);

        if (getArguments() != null && getActivity() != null) {
            String arg = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ? "movies" : "tv";
            new FavoriteViewModel(getActivity().getApplication(), arg).getData().observe(getViewLifecycleOwner(), cursor -> {
                adapter.setModel(MappingHelper.cursorToArrayList(cursor));
                binding.progressBar.setVisibility(View.GONE);
            });
        }
    }
}
package com.e.myapplication.ui.favorite;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseFragment;
import com.e.myapplication.databinding.FavoriteBinding;

public class FavoriteFragment extends BaseFragment<FavoriteBinding> {
    public FavoriteFragment() {
    }

    @Override
    protected int resFragmentLayout() {
        return R.layout.favorite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.viewPager.setAdapter(new SectionsPagerAdapter(view.getContext(), getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewPager);
    }
}

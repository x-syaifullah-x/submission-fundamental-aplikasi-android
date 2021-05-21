package com.e.myapplication.ui.detail;


import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseFragment;
import com.e.myapplication.databinding.DetailBinding;
import com.e.myapplication.db.themoviedb.dto.ResultData;
import com.e.myapplication.utils.AddDataToFavorite;
import com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener;

import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.e.myapplication.ui.detail.DetailFragmentArgs.fromBundle;
import static java.lang.String.valueOf;

public class DetailFragment extends BaseFragment<DetailBinding> {
    private ResultData mData;

    public DetailFragment() {
    }

    @Override
    protected int resFragmentLayout() {
        return R.layout.detail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            binding.setData(fromBundle(getArguments()).getData());
            binding.detailContent.setData(fromBundle(getArguments()).getData());
            mData = binding.getData();
        }

        binding.appBar.addOnOffsetChangedListener((BaseOnOffsetChangedListener) (appBarLayoutHome, i) -> {
            if (valueOf(i).equalsIgnoreCase(valueOf(-binding.appBar.getTotalScrollRange()))) {
                binding.detailContent.imgMovie1.setVisibility(VISIBLE);
                binding.detailContent.imgMovie2.setVisibility(VISIBLE);
            } else if (i > -500) {
                binding.detailContent.imgMovie1.setVisibility(GONE);
                binding.detailContent.imgMovie2.setVisibility(GONE);
            }
        });

        /* add data to favorite */
        binding.fab.setOnClickListener(v -> new Builder(getContext())
                .setTitle(getString(R.string.add_to_favorite, isEmpty(mData.toString()) ? getString(R.string.tv_show) : getString(R.string.movies)))
                .setMessage(getString(R.string.message_add_to_favorite,
                        isEmpty(mData.toString()) ? mData.getOriginalName() : mData.getTitle(),
                        isEmpty(mData.toString()) ? getString(R.string.tv_show) : getString(R.string.movies)))
                .setNegativeButton(getString(R.string.negative_button_cancel_favorite), null)
                .setPositiveButton(getString(R.string.positive_button_ok_favorite), (dialog, which) ->
                        new AddDataToFavorite(getView(), binding.posterDetail.getDrawable()).execute(binding.getData()))
                .show());
    }
}

package com.e.myapplication.ui.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.myapplication.R;

import org.jetbrains.annotations.NotNull;

import static com.e.myapplication.ui.favorite.FavoriteContent.newInstance;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_1_movies,
            R.string.tab_2_tv_show
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {
        return newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
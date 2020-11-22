package com.e.myapplication.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public abstract class BaseActivity<ActivityBinding extends ViewDataBinding> extends AppCompatActivity {
    protected abstract int layoutRes();

    protected ActivityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutRes());
    }

    /* Forward onActivityResult to fragment */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().getFragments().get(0);
        List<Fragment> fragments = navHostFragment.getChildFragmentManager().getFragments();
        for (Fragment a : fragments) {
            a.onActivityResult(requestCode, resultCode, data);
        }
    }
}

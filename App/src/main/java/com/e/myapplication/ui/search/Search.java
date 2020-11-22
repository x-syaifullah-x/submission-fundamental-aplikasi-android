package com.e.myapplication.ui.search;

import android.os.Bundle;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseActivity;
import com.e.myapplication.databinding.SearchBinding;

import static com.e.myapplication.ui.search.SearchData.TYPE;

public class Search extends BaseActivity<SearchBinding> {
    public static final String DATA_EXTRA = "DATA_EXTRA";

    @Override
    protected int layoutRes() {
        return R.layout.search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchData searchData = new SearchData();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, getIntent().getStringExtra(DATA_EXTRA));
        searchData.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_content, searchData)
                .commit();
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
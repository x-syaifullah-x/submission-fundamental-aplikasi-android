package com.e.myapplication.ui.search;

import android.os.Bundle;

import com.e.myapplication.R;
import com.e.myapplication.base.BaseActivity;
import com.e.myapplication.databinding.ActivitySearchBinding;

import static com.e.myapplication.ui.search.SearchData.TYPE;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    public static final String DATA_EXTRA = "DATA_EXTRA";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
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
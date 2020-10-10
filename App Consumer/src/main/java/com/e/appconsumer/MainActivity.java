package com.e.appconsumer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.e.appconsumer.databinding.ActivityMainBinding;
import com.e.appconsumer.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding main = DataBindingUtil.setContentView(this, R.layout.activity_main);

        main.viewPager.setAdapter(new SectionsPagerAdapter(this, getSupportFragmentManager()));
        main.tabs.setupWithViewPager(main.viewPager);
    }
}
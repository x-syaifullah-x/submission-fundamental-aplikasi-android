package com.e.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration.Builder;
import androidx.navigation.ui.NavigationUI;

import com.e.myapplication.base.BaseActivity;
import com.e.myapplication.databinding.ActivityMainBinding;
import com.e.myapplication.databinding.BindingEventHandler;
import com.e.myapplication.reminder.Daily;
import com.e.myapplication.reminder.ReleaseToday;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static androidx.navigation.Navigation.findNavController;
import static com.e.myapplication.R.id.detailFragment;
import static com.e.myapplication.reminder.ReleaseToday.isFirst;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavController.OnDestinationChangedListener {
    @Override
    protected int resLayoutActivity() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.setClick(new BindingEventHandler());

        /* set reminder pertama kali app di run*/
        if (isFirst(this)) {
            new ReleaseToday().setRepeatingAlarm(this, true);
            new Daily().setRepeatingAlarm(this, true);
        }

        setSupportActionBar(binding.toolbar);

        NavController navController = findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, new Builder(
                R.id.navigation_movies,
                R.id.navigation_tv_show,
                R.id.navigation_favorite
        ).build());
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == detailFragment) {
            binding.cvToolbarMain.setVisibility(INVISIBLE);
            binding.appBar.setExpanded(false);
            binding.bottomNavigationView.setVisibility(GONE);
        } else {
            binding.toolbar.setTitle(getString(R.string.search, destination.getLabel()));
            binding.cvToolbarMain.setVisibility(VISIBLE);
            binding.appBar.setExpanded(true);
            binding.bottomNavigationView.setVisibility(VISIBLE);
        }
    }
}
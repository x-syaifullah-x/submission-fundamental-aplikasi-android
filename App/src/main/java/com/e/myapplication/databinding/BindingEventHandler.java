package com.e.myapplication.databinding;

import android.content.Intent;
import android.view.View;

import androidx.navigation.NavDestination;

import com.e.myapplication.R;
import com.e.myapplication.ui.prefs.Settings;
import com.e.myapplication.ui.search.SearchActivity;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static androidx.navigation.Navigation.findNavController;
import static com.e.myapplication.ui.search.SearchActivity.DATA_EXTRA;

public class BindingEventHandler {

    /* on clik bottom setting */
    public void settings(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), Settings.class));
    }

    /* on clik toolbar */
    public void toolbarOnclick(View view) {
        NavDestination destination = findNavController(view.getRootView().findViewById(R.id.nav_host_fragment)).getCurrentDestination();
        Intent intent = new Intent(view.getContext(), SearchActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);

        if (destination != null) {
            if (destination.getId() == R.id.navigation_movies)
                intent.putExtra(DATA_EXTRA, "movie");
            else if (destination.getId() == R.id.navigation_tv_show)
                intent.putExtra(DATA_EXTRA, "tv");
            else if (destination.getId() == R.id.navigation_favorite)
                intent.putExtra(DATA_EXTRA, "favorite");
        }
        view.getContext().startActivity(intent);
    }
}

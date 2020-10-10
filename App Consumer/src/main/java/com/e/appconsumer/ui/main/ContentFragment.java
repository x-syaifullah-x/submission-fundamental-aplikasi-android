package com.e.appconsumer.ui.main;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.appconsumer.R;
import com.e.appconsumer.databinding.FragmentMainBinding;

import static com.e.appconsumer.room.DatabaseContract.getUri;


public class ContentFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentMainBinding binding;
    private String arg;

    public static ContentFragment newInstance(int index) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMainBinding.bind(view);
        if (getArguments() != null) {
            arg = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ? "movies" : "tv";
        }


        new LoadData(binding).execute(arg);
        HandlerThread handlerThread = new HandlerThread("observer");
        handlerThread.start();

        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(
                    getUri(),
                    true,
                    new ContentObserver(new Handler(handlerThread.getLooper())) {
                        @Override
                        public void onChange(boolean selfChange) {
                            super.onChange(selfChange);
                            new LoadData(binding).execute(arg);
                        }
                    });
        }
    }
}
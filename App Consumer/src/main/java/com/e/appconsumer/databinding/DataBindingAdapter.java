package com.e.appconsumer.databinding;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.e.appconsumer.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.graphics.BitmapFactory.decodeStream;
import static com.e.appconsumer.room.DatabaseContract.getUriImage;

public class DataBindingAdapter {

    @BindingAdapter({"set_date"})
    public static void setFormatReleaseDate(TextView textView, String releaseDate) {
        if (!TextUtils.isEmpty(releaseDate)) {
            List<Integer> integers = new ArrayList<>();
            for (String a : releaseDate.split("-")) {
                integers.add(Integer.parseInt(a));
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(integers.get(0), (integers.get(1) - 1), integers.get(2));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            textView.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }

    @BindingAdapter({"set_image_with_uri"})
    public static void setImageWithByteUsingUri(final ImageView imageView, final long id) {
        try {
            Glide.with(imageView)
                    .load(decodeStream(imageView.getContext().getContentResolver().openInputStream(getUriImage("image_data", String.valueOf(id)))))
                    .into(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"overview"})
    public static void setOverview(TextView textView, String overview) {
        if (!TextUtils.isEmpty(overview)) {
            textView.setText(overview);
        } else {
            textView.setText(R.string.tidak_ada_bahasa_indonesia);
        }
    }
}

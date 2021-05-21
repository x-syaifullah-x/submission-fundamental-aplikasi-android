package com.e.myapplication.databinding;


import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.e.myapplication.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.graphics.BitmapFactory.decodeStream;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static com.bumptech.glide.Glide.with;
import static com.e.myapplication.BuildConfig.IMAGE_URL_ORIGINAL;
import static com.e.myapplication.BuildConfig.IMAGE_URL_W185;
import static com.e.myapplication.R.string.no_indonesia_language;
import static com.e.myapplication.db.room.DatabaseContract.getUriImage;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Locale.getDefault;

public class BindingDataAdapter {

    @BindingAdapter({"poster_favorite"})
    public static void setImageWithByte(final ImageView imageView, final int id) {
        try {
            with(imageView.getContext())
                    .load(decodeStream(imageView.getContext().getContentResolver().openInputStream(getUriImage("image_data", valueOf(id)))))
                    .into(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"release_date"})
    public static void setFormatReleaseDate(TextView textView, String releaseDate) {
        if (!isEmpty(releaseDate)) {
            String[] a = releaseDate.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(parseInt(a[0]), (parseInt(a[1]) - 1), parseInt(a[2]));
            textView.setText(new SimpleDateFormat("dd MMMM yyyy", getDefault()).format(calendar.getTime()));
        }
    }

    @BindingAdapter({"overview"})
    public static void setOverview(TextView textView, String overview) {
        textView.setText(!TextUtils.isEmpty(overview) ? overview : textView.getContext().getString(no_indonesia_language));
    }

    @BindingAdapter({"setimage_detail"})
    public static void setImage(final ImageView imageView, String posterPath) {
        final ProgressBar pbDetailPoster = imageView.getRootView().findViewById(R.id.pb_detail_poster);
        final ProgressBar pbPosterList = imageView.getRootView().findViewById(R.id.pb_poster_list);
        with(imageView.getContext())
                .load(imageView.getId() == R.id.poster_detail ? IMAGE_URL_ORIGINAL + posterPath : IMAGE_URL_W185 + posterPath)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        switch (imageView.getId()) {
                            case R.id.poster_detail:
                                pbDetailPoster.setVisibility(GONE);
                                break;
                            case R.id.img_poster_list:
                                pbPosterList.setVisibility(GONE);
                                break;
                        }
                        return false;
                    }
                }).into(imageView);
    }
}

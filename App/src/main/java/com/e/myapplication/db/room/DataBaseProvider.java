package com.e.myapplication.db.room;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;

import static android.content.ContentUris.withAppendedId;
import static android.content.UriMatcher.NO_MATCH;
import static android.os.ParcelFileDescriptor.MODE_READ_ONLY;
import static android.os.ParcelFileDescriptor.open;
import static com.e.myapplication.db.room.DatabaseContract.AUTHORITY;
import static com.e.myapplication.db.room.DatabaseContract.TABLE_NAME;
import static com.e.myapplication.db.room.DatabaseContract.TITLE;
import static com.e.myapplication.db.room.RoomDB.getDatabase;
import static com.e.myapplication.db.room.entity.FavoriteModel.contentValues;
import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

public class DataBaseProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = new UriMatcher(NO_MATCH);
    private static final int NAME_TABLE = 1;
    private static final int ID = 2;
    private static final int IMAGE_DATA = 3;

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, NAME_TABLE); // content://com.e.myapplication/name_table
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/*", ID); // content://com.e.myapplication/name_table/id
        URI_MATCHER.addURI(AUTHORITY, "image_data" + "/*", IMAGE_DATA); // content://com.e.myapplication/image_data/nama_file
    }

    private RoomDao mDao;
    private ContentResolver mResolver;

    public DataBaseProvider() {
    }

    @Override
    public boolean onCreate() {
        mDao = getDatabase(getContext()).theMoviesDbDao();
        if (getContext() != null)
            mResolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (URI_MATCHER.match(uri) == NAME_TABLE)
            return !TextUtils.isEmpty(sortOrder) ? mDao.getDataByType(sortOrder) : mDao.getAllData();
        return null;
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == NAME_TABLE)
            if (!mDao.getDataByTitle(values.getAsString(TITLE))) { /* title validation */
                mResolver.notifyChange(uri, null);
                return withAppendedId(uri, mDao.insertData(contentValues(values)));
            }
        return withAppendedId(uri, 0);
    }

    @Override
    public int delete(@NotNull Uri uri, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ID)
            if (mDao.deleteById(uri.getLastPathSegment()) != 0)
                if (getContext() != null) {
                    new File(getContext().getString(R.string.dir_file_image, requireNonNull(getContext()).getFilesDir().getPath(), uri.getLastPathSegment())).delete();
                    mResolver.notifyChange(uri, null);
                    return parseInt(requireNonNull(uri.getLastPathSegment()));
                }
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        if (getContext() != null)
            if (requireNonNull(new File(getContext().getFilesDir(), "image_data").list()).length > 0)
                if (URI_MATCHER.match(uri) == IMAGE_DATA)
                    return new ParcelFileDescriptor(open(new File(requireNonNull(getContext()).getFilesDir(), requireNonNull(uri.getPath())), MODE_READ_ONLY));
        return null;
    }
}
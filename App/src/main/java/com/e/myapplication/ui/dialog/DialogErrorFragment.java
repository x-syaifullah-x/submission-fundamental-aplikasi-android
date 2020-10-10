package com.e.myapplication.ui.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.e.myapplication.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class DialogErrorFragment extends DialogFragment {
    public static String KEY_ERROR = "key_error";
    public static Map<String, String> error = new LinkedHashMap<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(getString(R.string.error_message_dialog_error));
        dialog.setMessage(error.get(KEY_ERROR));
        dialog.setNegativeButton(getString(R.string.bottom_ok_dialog_error), null);
        setCancelable(false);
        return dialog.create();
    }
}

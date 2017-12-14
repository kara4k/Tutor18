package com.kara4k.tutor18.view.custom;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.kara4k.tutor18.R;

public class EditTextDialog extends DialogFragment {

    private String mTitle;
    private String mText;
    private int mInputType = InputType.TYPE_CLASS_TEXT;
    private OnOkListener mOnOkListener;


    public interface OnOkListener {
        void onOkClicked(String text);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_text, null);
        EditText editText = dialogView.findViewById(R.id.edit_text);

        DialogInterface.OnClickListener listener = (dialogInterface, i) -> {
            if (mOnOkListener != null) {
                mOnOkListener.onOkClicked(editText.getText().toString());
            }
        };

        editText.setText(mText);
        editText.setInputType(mInputType);
        editText.setSelection(editText.getText().length());

        return new AlertDialog.Builder(getContext())
                .setTitle(mTitle)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, null)
                .setView(dialogView)
                .create();

    }

    public String getTitle() {
        return mTitle;
    }

    public EditTextDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getText() {
        return mText;
    }

    public EditTextDialog setText(String text) {
        mText = text;
        return this;
    }

    public OnOkListener getOnOkListener() {
        return mOnOkListener;
    }

    public EditTextDialog setOnOkListener(OnOkListener onOkListener) {
        mOnOkListener = onOkListener;
        return this;
    }

    public int getInputType() {
        return mInputType;
    }

    public EditTextDialog setInputType(int inputType) {
        mInputType = inputType;
        return this;
    }
}

package com.kara4k.tutor18.view.activities;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import com.kara4k.tutor18.R;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTextActivity extends BaseActivity {

    public static final String TEXT = "text";
    public static final String TITLE = "title";

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_text;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(TITLE);
            setTitle(title);

            String text = getIntent().getStringExtra(TEXT);
            mEditText.setText(text);
            if (text != null) {
                mEditText.setSelection(text.length());
            }
        }
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        Intent intent = new Intent();
        intent.putExtra(TEXT, mEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    public static Intent newIntent(Context context, String title, String text) {
        Intent intent = new Intent(context, EditTextActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(TEXT, text);
        return intent;
    }
}

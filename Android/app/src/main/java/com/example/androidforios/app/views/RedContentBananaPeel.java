package com.example.androidforios.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidforios.app.R;
import com.smb.loadingbananapeel.LoadingBananaPeelView;

/**
 * Created by smbarne on 11/16/14.
 */
public class RedContentBananaPeel extends LoadingBananaPeelView {

    public RedContentBananaPeel(Context context) {
        super(context);
    }

    public RedContentBananaPeel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View setContentView(View contentView) {
        View inflatedView = super.setContentView(contentView);

        // Modify the inflated content view (hideously)
        inflatedView.setBackgroundColor(getResources().getColor(R.color.Material_Red));
        int padding = getResources().getDimensionPixelSize(R.dimen.Padding_Large);
        inflatedView.setPadding(padding, padding, padding, padding);

        return inflatedView;
    }
}

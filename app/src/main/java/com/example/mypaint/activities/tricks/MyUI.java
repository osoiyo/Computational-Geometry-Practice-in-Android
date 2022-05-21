package com.example.mypaint.activities.tricks;

import android.app.ActionBar;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

public class MyUI {

    public static void hideStatusBar(Activity myActivity){
        View decorView = myActivity.getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = myActivity.getActionBar();
        actionBar.hide();
    }

    public static float getStatusBarHeight(Activity myActivity){
        int resourceId = myActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            return myActivity.getResources().getDimensionPixelSize(resourceId);
        }
        else return 0;
    }

    public static float[] getScreenSize(Activity myActivity){
        float screenSize[] = new float[2];
        DisplayMetrics dm = new DisplayMetrics();
        myActivity.getWindowManager().getDefaultDisplay().getMetrics((dm));
        screenSize[0] = dm.widthPixels;
        screenSize[1] = dm.heightPixels;
        return screenSize;
    }
}

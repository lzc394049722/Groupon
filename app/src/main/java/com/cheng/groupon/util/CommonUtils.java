package com.cheng.groupon.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Explode;
import android.widget.Toast;

import com.cheng.groupon.app.MyApp;

/**
 * Created by cheng on 2017/6/15 0015.
 */

public class CommonUtils {

    /**
     * getWindow().setEnterTransition(new Explode());
     * getWindow().setEnterTransition(new Slide());
     * getWindow().setEnterTransition(new Fade());
     *
     * @param activity
     * @param loadClass
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity activity, Class loadClass) {
        Intent intent = new Intent(activity, loadClass);

        // activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static void toast(CharSequence text) {
        Toast.makeText(MyApp.mContext, text, Toast.LENGTH_SHORT).show();
    }

}

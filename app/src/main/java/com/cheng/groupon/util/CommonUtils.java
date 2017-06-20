package com.cheng.groupon.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Explode;

/**
 * Created by cheng on 2017/6/15 0015.
 */

public class CommonUtils {

    /**
     * getWindow().setEnterTransition(new Explode());
        getWindow().setEnterTransition(new Slide());
        getWindow().setEnterTransition(new Fade());
     * @param activity
     * @param loadClass
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(Activity activity, Class loadClass) {
        Intent intent = new Intent(activity, loadClass);

        // activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Explode explode = new Explode();
        explode.setDuration(1000L);
        activity.getWindow().setEnterTransition(explode);
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

}

package com.cheng.groupon.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by cheng on 2017/6/15 0015.
 */

public class CommonUtils {

    public static void startActivity(Activity activity, Class loadClass) {
        Intent intent = new Intent(activity, loadClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}

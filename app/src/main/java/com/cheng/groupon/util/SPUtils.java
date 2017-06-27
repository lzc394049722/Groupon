package com.cheng.groupon.util;

/**
 * 对偏好文件的操作
 * <p>
 * Created by cheng on 2017/6/15 0015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cheng.groupon.C;

/**
 * 1.Context->getsharepreference  特别的个性化设计
 * 2.activity-->getpreference 应该存与界面相关的，例如样式！！！
 * 3.preferencemanager getdefault方法 应存全局通用的！！！
 */
public class SPUtils {

    private SharedPreferences sp;

    public SPUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SPUtils(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirst() {
        return sp.getBoolean(C.FIRST_IN, true);
    }

    public void setFirst(boolean flag) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(C.FIRST_IN, flag);
        editor.commit();
    }

    public boolean isCloseBanner() {
        return sp.getBoolean(C.ISAD_CLOSE, false);
    }

    public void setCloseBanner(boolean flag) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(C.ISAD_CLOSE, flag);
        editor.commit();
    }

}

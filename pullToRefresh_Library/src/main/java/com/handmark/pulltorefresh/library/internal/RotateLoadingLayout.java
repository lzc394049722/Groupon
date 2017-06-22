/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

public class RotateLoadingLayout extends LoadingLayout {

    int[] resIds = new int[]{
            R.drawable.dropdown_anim_00,
            R.drawable.dropdown_anim_01,
            R.drawable.dropdown_anim_02,
            R.drawable.dropdown_anim_03,
            R.drawable.dropdown_anim_04,
            R.drawable.dropdown_anim_05,
            R.drawable.dropdown_anim_06,
            R.drawable.dropdown_anim_07,
            R.drawable.dropdown_anim_08,
            R.drawable.dropdown_anim_09,
            R.drawable.dropdown_anim_10
    };


    public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

    }

    /**
     * 设置旋转动画中心点
     *
     * @param imageDrawable
     */
    public void onLoadingDrawableSet(Drawable imageDrawable) {
        // NO-OP
    }

    protected void onPullImpl(float scaleOfLayout) {
        int idx = (int) (scaleOfLayout * 10);

        if (idx <= 10) {
            int id = resIds[idx];
            Bitmap src = BitmapFactory.decodeResource(getResources(), id);
            int width = (int) (scaleOfLayout * src.getWidth());
            int height = (int) (scaleOfLayout * src.getHeight());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, width, height, true);
            mHeaderImage.setImageBitmap(scaledBitmap);

           /* @SuppressLint("NewApi") Drawable draw = getContext().getDrawable(id);
            draw.setLevel(1);
            ScaleDrawable sd = new ScaleDrawable(draw, Gravity.CENTER, 0.5f, 0.5f);
            mHeaderImage.setImageDrawable(sd);*/
        } else {
            int resId = resIds[idx % 11];
            mHeaderImage.setImageResource(resId);
        }
    }

    /**
     * 松手后的动画状态
     */
    @Override
    protected void refreshingImpl() {
        mHeaderImage.setImageResource(R.drawable.refreshing_anim);
        AnimationDrawable drawable = (AnimationDrawable) mHeaderImage.getDrawable();
        drawable.start();
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        // NO-OP
    }

    private void resetImageRotation() {

    }

    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_anim_00;
    }

}

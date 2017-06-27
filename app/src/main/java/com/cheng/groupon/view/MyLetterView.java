package com.cheng.groupon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.cheng.groupon.R;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class MyLetterView extends View {

    private String[] letters = {"热门", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    Paint paint;
    private final int color;

    public MyLetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.MyLetterView);
        color = t.getColor(R.styleable.MyLetterView_letter_color, Color.BLACK);
        t.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //直传数字，一般都是px（像素）,所以用typeValue适配
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            int lPadding = getPaddingLeft();
            int rPadding = getPaddingRight();
            int contentWidth = 0;
            for (int i = 0; i < letters.length; i++) {
                String letter = letters[i];

                Rect bounds = new Rect();
                paint.getTextBounds(letter, 0, letter.length(), bounds);

                if (bounds.width() > contentWidth) {
                    contentWidth = bounds.width();
                }
            }
            int size = lPadding + contentWidth + rPadding;
            setMeasuredDimension(size, MeasureSpec.getSize(heightMeasureSpec));

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        //每个空间高度=整个控件/27
        for (int i = 0; i < letters.length; i++) {
            String text = letters[i];
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            //获取文字的边界大小
            // bounds.width() / bounds.height
            //x,y是左下角区域坐标
            float x = width / 2 - bounds.width() / 2;//分配给letter空间宽度/2-字体宽/2
            float y = height / letters.length / 2 + bounds.height() + i * height / letters.length;//分配给letter空间高度/2+字体高/2

            canvas.drawText(text, x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.GRAY);
                if (listener != null) {

                    float y = event.getY();
                    //根据y换算下标值
                    int idx = (int) ((y / getHeight()) * letters.length);

                    listener.onTouchLetter(letters[idx]);
                }
                break;
            default:
                setBackgroundColor(Color.TRANSPARENT);
                break;
        }

        return true;
    }

    public interface OnTouchLetterListener {
        void onTouchLetter(String letter);
    }

    private OnTouchLetterListener listener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.listener = listener;
    }
}

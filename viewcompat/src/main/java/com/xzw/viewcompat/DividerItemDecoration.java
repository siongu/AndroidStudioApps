package com.xzw.viewcompat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by xzw on 2015/6/17.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int left, top, right, bottom;
    private int mSize = 0;
    private int mOrientation;
    private Context mContext;
    private Drawable divider;

    /**
     * @param context
     * @param orientation orientation for divider.(RecyclerView.Vertical or RecyclerView.Horizontal)
     */
    public DividerItemDecoration(Context context, int orientation) {
        init(context, orientation);
    }

    /**
     * @param context
     * @param divider     a drawable for divider
     * @param orientation orientation for divider.(RecyclerView.Vertical or RecyclerView.Horizontal)
     */
    public DividerItemDecoration(Context context, Drawable divider, int orientation) {
        init(context, divider, orientation);
    }

    /**
     * @param context
     * @param resId       resouceId
     * @param orientation orientation for divider.(RecyclerView.Vertical or RecyclerView.Horizontal)
     */
    public DividerItemDecoration(Context context, int resId, int orientation) {
        init(context, resId, orientation);
    }

    private void init(Context context, int orientation) {
        if (orientation != RecyclerView.HORIZONTAL && orientation != RecyclerView.VERTICAL) {
            throw new IllegalArgumentException("orientation is not valid");
        }
        this.mContext = context;
        this.mOrientation = orientation;
        TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        this.divider = a.getDrawable(0);
        a.recycle();
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSize, context.getResources().getDisplayMetrics());
    }

    private void init(Context context, Drawable divider, int orientation) {
        if (orientation != RecyclerView.HORIZONTAL && orientation != RecyclerView.VERTICAL) {
            throw new IllegalArgumentException("orientation is not valid");
        }

        this.mContext = context;
        this.divider = divider;
        this.mOrientation = orientation;
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSize, context.getResources().getDisplayMetrics());
    }


    private void init(Context context, int resId, int orientation) {
        if (orientation != RecyclerView.HORIZONTAL && orientation != RecyclerView.VERTICAL) {
            throw new IllegalArgumentException("orientation is not valid");
        }
        this.mContext = context;
        this.divider = context.getResources().getDrawable(resId);
        this.mOrientation = orientation;
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSize, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        switch (mOrientation) {
            case RecyclerView.HORIZONTAL:
                drawHorizontalDivider(c, parent);
                break;
            case RecyclerView.VERTICAL:
                drawVerticalDivider(c, parent);
                break;
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        left = parent.getLeft() + parent.getPaddingLeft();
        right = parent.getRight() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + (mSize == 0 ? divider.getIntrinsicHeight() : mSize);
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        top = parent.getTop() + parent.getPaddingTop();
        bottom = parent.getBottom() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + params.rightMargin;
            right = left + (mSize == 0 ? divider.getIntrinsicWidth() : mSize);
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == RecyclerView.VERTICAL) {
            outRect.bottom = mSize == 0 ? divider.getIntrinsicHeight() : mSize;
        } else {
            outRect.right = mSize == 0 ? divider.getIntrinsicWidth() : mSize;
        }
    }

    public void setDividerSize(int size) {
        this.mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, mContext.getResources().getDisplayMetrics());
    }

}

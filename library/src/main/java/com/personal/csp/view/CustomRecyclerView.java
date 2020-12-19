package com.personal.csp.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 给recyclerView添加一个设置最大高度的方法
 */
public class CustomRecyclerView extends RecyclerView {
    private int mMaxHeight=1150;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(mMaxHeight,MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
    public void setMaxHeight(int maxHeight){
        if (mMaxHeight!=maxHeight){
            mMaxHeight=maxHeight;
            requestLayout();
        }
    }
}

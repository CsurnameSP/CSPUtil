package com.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkbanlv.library.R;



/**
 * @author csp
 * @data 2019/2/16.
 * Github https://github.com/CsurnameSP
 * @Description 封装一个网络错误的View布局
 */
public class InternetErrorView extends FrameLayout {

    private ImageView emptyImage;
    private TextView emptyTvName;
    private NestedScrollView emptyContainer;

    public InternetErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final View view = LayoutInflater.from(context).inflate(R.layout.internet_error_view, this,true);
        emptyImage=view.findViewById(R.id.empty_image);
        emptyTvName=view.findViewById(R.id.empty_tv_name);
        emptyContainer=view.findViewById(R.id.empty_container);

        emptyTvName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(GONE);
                //网路错误，点击刷新
                if (onErrorMsgClickListener!=null){
                    onErrorMsgClickListener.onClick();
                }
            }
        });
    }

    /**
     * 修改展示文字信息
      * @param msg
     */
    public void setEmptyTvName(String msg){
        emptyTvName.setText(msg);
    }

    /**
     * 修改展示图片信息
     * @param resId
     */
    public void setEmptyImage(@DrawableRes int resId){
        emptyImage.setImageResource(resId);
    }

    /**
     * 设置监听接口
     */
    public void setOnErrorMsgClickListener(OnErrorMsgClickListener listener){
        this.onErrorMsgClickListener=listener;
    }
    private OnErrorMsgClickListener onErrorMsgClickListener;
    public interface OnErrorMsgClickListener{
        void onClick();
    }


    private boolean hasLoadSuccess=false;

    /**
     * 仅当第一次加载没数据时显示布局，如果之前加载成功过数据，则显示历史缓存
     * @param visibility
     */
    public void setVisibilityOnlyNoHistory(int visibility){
        if (!hasLoadSuccess){
            setErrorViewLayout();
            setVisibility(visibility);
        }
    }

    /**
     * 设置两个常用布局，一个是网络错误时的布局显示，一个是网络访问成功但是数据为空的显示
     */
    public void setNoDataViewLayout(){
       emptyTvName.setText("暂无数据");
       emptyImage.setImageResource(R.drawable.icon_image_empty);
       emptyTvName.setClickable(false);
    }
    public void setErrorViewLayout(){
        emptyTvName.setText("网络错误,点击重试");
        emptyImage.setImageResource(R.drawable.icon_image_error);
        emptyTvName.setClickable(true);
    }

    /***以下是常用get set方法***/
    public boolean isHasLoadSuccess() {
        return hasLoadSuccess;
    }

    public void setHasLoadSuccess(boolean hasLoadSuccess) {
        this.hasLoadSuccess = hasLoadSuccess;
    }
}

package com.personal.csp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.personal.csp.utils.CspUtil;
import com.personal.personal.csp.R;


/**
 * 简单提示弹框改变样式
 * create by csp in 2019/1/31
 */
public class CustomDialog {
    private Context mContext;
    private String mTitle;
    private String mMessage;
    private String mPositiveText;
    private String mNegativeText;
    private boolean mCancelable = true;
    private boolean mShowOneBtn = false;//只显示一个按钮
    private OnPositiveButtonClickListener mPositiveListener;
    private OnNegativeButtonClickListener mNegativeListener;
    private Dialog mDialog;
    /***描述： 添加自定义View @author csp 创建日期 ：2019/11/14 17:41***/
    private View mCustomView;

    /**
     * description:  支持对文字颜色的改动  ——add notes by CSP on 18:23
     **/
    private ColorStateList mTitleTextColor, mContentTextColor, mPositiveTextColor, mNegativeTextColor;

    private CustomDialog(Context context) {
        mContext = context;
    }


    public void show() {
        mDialog = showCustomSimpleDialog();
    }

    public void cancel() {
        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    /***
     * add notes by CSP on 2020/12/21
     * description:添加获取window的方法
     */
    public Window getWindow() {
        if (mDialog != null) {
            return mDialog.getWindow();
        }
        return null;
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mPositiveText;
        private String mNegativeText;
        private OnPositiveButtonClickListener mPositiveListener;
        private OnNegativeButtonClickListener mNegativeListener;
        private boolean mCancelable = true;
        private boolean mShowOneBtn = false;//只显示一个按钮
        private ColorStateList mTitleTextColor, mContentTextColor, mPositiveTextColor, mNegativeTextColor;
        private View mCustomView;

        public Builder setCustomView(View view) {
            this.mCustomView = view;
            return this;
        }

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public Builder setPositiveText(String text) {
            this.mPositiveText = text;
            return this;
        }

        public Builder setNegativeText(String text) {
            this.mNegativeText = text;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setShowOneBtn(boolean showOneBtn) {
            this.mShowOneBtn = showOneBtn;
            return this;
        }

        public Builder setOnPositiveButtonClickListener(OnPositiveButtonClickListener listener) {
            this.mPositiveListener = listener;
            return this;
        }

        public Builder setOnNegativeButtonClickListener(OnNegativeButtonClickListener listener) {
            this.mNegativeListener = listener;
            return this;
        }

        public Builder setTitleTextColor(@ColorInt int color) {
            this.mTitleTextColor = ColorStateList.valueOf(color);
            return this;
        }

        public Builder setTitleTextColor(ColorStateList colors) {
            this.mTitleTextColor = colors;
            return this;
        }

        public Builder setContentTextColor(@ColorInt int color) {
            this.mContentTextColor = ColorStateList.valueOf(color);
            return this;
        }

        public Builder setContentTextColor(ColorStateList colors) {
            this.mContentTextColor = colors;
            return this;
        }


        public Builder setPositiveTextColor(ColorStateList colors) {
            this.mPositiveTextColor = colors;
            return this;
        }

        public Builder setPositiveTextColor(@ColorInt int color) {
            this.mPositiveTextColor = ColorStateList.valueOf(color);
            return this;
        }


        public Builder setNegativeTextColor(ColorStateList colors) {
            this.mNegativeTextColor = colors;
            return this;
        }

        public Builder setNegativeTextColor(@ColorInt int color) {
            this.mNegativeTextColor = ColorStateList.valueOf(color);
            return this;
        }

        public CustomDialog build() {
            CustomDialog customDialog = new CustomDialog(mContext);
            customDialog.mTitle = this.mTitle;
            customDialog.mMessage = this.mMessage;
            customDialog.mPositiveText = this.mPositiveText;
            customDialog.mNegativeText = this.mNegativeText;
            customDialog.mPositiveListener = this.mPositiveListener;
            customDialog.mNegativeListener = this.mNegativeListener;
            customDialog.mCancelable = this.mCancelable;
            customDialog.mShowOneBtn = this.mShowOneBtn;
            customDialog.mCustomView = this.mCustomView;
            customDialog.mTitleTextColor = this.mTitleTextColor;
            customDialog.mContentTextColor = this.mContentTextColor;
            customDialog.mPositiveTextColor = this.mPositiveTextColor;
            customDialog.mNegativeTextColor = this.mNegativeTextColor;
            customDialog.show();
            return customDialog;
        }
    }

    /**
     * 自定义弹框逻辑事件接口回调处理
     */
    public interface OnPositiveButtonClickListener {
        void onPositiveButtonClick(Dialog dialog);
    }

    public interface OnNegativeButtonClickListener {
        void onNegativeButtonClick(Dialog dialog);
    }

    /**
     * 简单提示弹框改变样式
     * context 上下文对象
     * title 标题
     * msg 内容
     * positiveText 确认按钮文字
     * negativeText 取消按钮文字
     * positiveListener 确认按钮监听回调
     * negativeListener 取消按钮监听回调
     * cancelable 是否可以取消弹框
     * showOneBtn 是否隐藏取消按钮
     */
    @SuppressLint("ResourceAsColor")
    private Dialog showCustomSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final Dialog dialog = builder.show();
        //是否可以取消
        dialog.setCancelable(mCancelable);
        Window window = dialog.getWindow();

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_simple_toast, null);

        TextView clickNegative = view.findViewById(R.id.click_negative);
        TextView clickPositive = view.findViewById(R.id.click_positive);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView dialogMsg = view.findViewById(R.id.dialog_msg);
        View clickLine = view.findViewById(R.id.click_line);
        LinearLayout dialogCustomViewContainer = view.findViewById(R.id.dialog_custom_view_container);

        if (mCustomView != null) {
            dialogMsg.setVisibility(View.GONE);
            dialogCustomViewContainer.setVisibility(View.VISIBLE);
            dialogCustomViewContainer.addView(mCustomView);
        } else {
            dialogMsg.setVisibility(View.VISIBLE);
            //消息自定义
            if (!TextUtils.isEmpty(mMessage)) {
                dialogMsg.setText(mMessage);
            }
            dialogCustomViewContainer.setVisibility(View.GONE);
        }

        //标题自定义
        if (!TextUtils.isEmpty(mTitle)) {
            dialogTitle.setText(mTitle);
        }
        if (mTitleTextColor != null) {
            dialogTitle.setTextColor(mTitleTextColor);
        }

        //消息自定义
        if (!TextUtils.isEmpty(mMessage)) {
            dialogMsg.setText(mMessage);
        }
        if (mContentTextColor != null) {
            dialogMsg.setTextColor(mContentTextColor);
        }
        if (mShowOneBtn) {
            clickNegative.setVisibility(View.GONE);//只显示一个按钮，隐藏取消按钮
            clickLine.setVisibility(View.GONE);
        } else {
            clickNegative.setVisibility(View.VISIBLE);
            clickLine.setVisibility(View.VISIBLE);
        }
        //确认按钮自定义
        if (!TextUtils.isEmpty(mPositiveText)) {
            clickPositive.setText(mPositiveText);
        }
        if (mPositiveTextColor != null) {
            clickPositive.setTextColor(mPositiveTextColor);
        }
        //取消按钮自定义
        if (!TextUtils.isEmpty(mNegativeText)) {
            clickNegative.setText(mNegativeText);
        }
        if (mNegativeTextColor != null) {
            clickNegative.setTextColor(mNegativeTextColor);
        }
        //取消
        clickNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //接口回调
                if (mNegativeListener != null) {
                    mNegativeListener.onNegativeButtonClick(dialog);
                }
            }
        });
        //确认
        clickPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //接口回调
                if (mPositiveListener != null) {
                    mPositiveListener.onPositiveButtonClick(dialog);
                }
            }
        });

        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = CspUtil.getPhoneWidth(mContext) * 82 / 100;
            window.setAttributes(params);
            window.setBackgroundDrawableResource(R.drawable.bg_white_corner_5);
            window.setContentView(view);
        }
        return dialog;
    }
}
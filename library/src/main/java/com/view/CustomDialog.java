package com.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jkbanlv.library.R;
import com.utils.CspUtil;


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
    private boolean mCancelable=true;
    private OnPositiveButtonClickListener mPositiveListener;
    private OnNegativeButtonClickListener mNegativeListener;
    private Dialog mDialog;

    private CustomDialog(Context context) {
        mContext = context;
    }


    public void show() {
        mDialog=showCustomSimpleDialog(mContext, mTitle, mMessage, mPositiveText,mPositiveListener,mNegativeText,mNegativeListener,mCancelable);
    }
    public void cancel(){
        if (mDialog!=null){
            mDialog.cancel();
        }
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mPositiveText;
        private String mNegativeText;
        private OnPositiveButtonClickListener mPositiveListener;
        private OnNegativeButtonClickListener mNegativeListener;
        private boolean mCancelable=true;

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
        public Builder setCancelable(boolean cancelable){
            this.mCancelable=cancelable;
            return this;
        }
        public Builder setOnPositiveButtonClickListener(OnPositiveButtonClickListener listener){
            this.mPositiveListener=listener;
            return this;
        }
        public Builder setOnNegativeButtonClickListener(OnNegativeButtonClickListener listener){
            this.mNegativeListener=listener;
            return this;
        }
        public CustomDialog build() {
            CustomDialog customDialog = new CustomDialog(mContext);
            customDialog.mTitle = this.mTitle;
            customDialog.mMessage = this.mMessage;
            customDialog.mPositiveText = this.mPositiveText;
            customDialog.mNegativeText = this.mNegativeText;
            customDialog.mPositiveListener=this.mPositiveListener;
            customDialog.mNegativeListener=this.mNegativeListener;
            customDialog.mCancelable=this.mCancelable;
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
     */
    public static Dialog showCustomSimpleDialog(Context context, String title, String msg,
                                                String positiveText, final OnPositiveButtonClickListener positiveListener,
                                                String negativeText, final OnNegativeButtonClickListener negativeListener,
                                                boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog = builder.show();
        //是否可以取消
        dialog.setCancelable(cancelable);
        Window window = dialog.getWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_simple_toast, null);

        TextView clickNegative = view.findViewById(R.id.click_negative);
        TextView clickPositive = view.findViewById(R.id.click_positive);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView dialogMsg = view.findViewById(R.id.dialog_msg);


        //标题自定义
        if (!TextUtils.isEmpty(title)) {
            dialogTitle.setText(title);
        }
        //消息自定义
        if (!TextUtils.isEmpty(msg)) {
            dialogMsg.setText(msg);
        }

        //确认按钮自定义
        if (!TextUtils.isEmpty(positiveText)) {
            clickPositive.setText(positiveText);
        }
        //取消按钮自定义
        if (!TextUtils.isEmpty(negativeText)){
            clickNegative.setText(negativeText);
        }
        //取消
        clickNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //接口回调
                if (negativeListener!=null){
                    negativeListener.onNegativeButtonClick(dialog);
                }
            }
        });
        //确认
        clickPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //接口回调
                if (positiveListener!=null) {
                    positiveListener.onPositiveButtonClick(dialog);
                }
            }
        });

        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = CspUtil.getPhoneWidth(context) * 82 / 100;
            window.setAttributes(params);
            window.setBackgroundDrawableResource(R.drawable.bg_white_corner_5);
            window.setContentView(view);
        }
        return dialog;
    }
}

package com.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
    private boolean mShowOneBtn=false;//只显示一个按钮
    private OnPositiveButtonClickListener mPositiveListener;
    private OnNegativeButtonClickListener mNegativeListener;
    private Dialog mDialog;
    /***描述： 添加自定义View @author csp 创建日期 ：2019/11/14 17:41***/
    private View mCustomView;

    private CustomDialog(Context context) {
        mContext = context;
    }


    public void show() {
        mDialog=showCustomSimpleDialog(mContext, mTitle, mMessage,mCustomView, mPositiveText,mPositiveListener,mNegativeText,mNegativeListener,mCancelable,mShowOneBtn);
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
        private boolean mShowOneBtn=false;//只显示一个按钮

        private View mCustomView;

        public Builder setCustomView(View view){
            this.mCustomView=view;
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
        public Builder setCancelable(boolean cancelable){
            this.mCancelable=cancelable;
            return this;
        }
        public Builder setShowOneBtn(boolean showOneBtn){
            this.mShowOneBtn=showOneBtn;
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
            customDialog.mShowOneBtn=this.mShowOneBtn;
            customDialog.mCustomView=this.mCustomView;
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
     * @param context 上下文对象
     * @param title 标题
     * @param msg 内容
     * @param positiveText 确认按钮文字
     * @param negativeText 取消按钮文字
     * @param positiveListener 确认按钮监听回调
     * @param negativeListener 取消按钮监听回调
     * @param cancelable 是否可以取消弹框
     * @param showOneBtn 是否隐藏取消按钮
     */
    public static Dialog showCustomSimpleDialog(Context context, String title, String msg, View customView,
                                                String positiveText, final OnPositiveButtonClickListener positiveListener,
                                                String negativeText, final OnNegativeButtonClickListener negativeListener,
                                                boolean cancelable, boolean showOneBtn) {
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
        View clickLine = view.findViewById(R.id.click_line);
        LinearLayout dialogCustomViewContainer=view.findViewById(R.id.dialog_custom_view_container);

        if (customView!=null){
            dialogMsg.setVisibility(View.GONE);
            dialogCustomViewContainer.setVisibility(View.VISIBLE);
            dialogCustomViewContainer.addView(customView);
        }else {
            dialogMsg.setVisibility(View.VISIBLE);
            //消息自定义
            if (!TextUtils.isEmpty(msg)) {
                dialogMsg.setText(msg);
            }
            dialogCustomViewContainer.setVisibility(View.GONE);
        }

        //标题自定义
        if (!TextUtils.isEmpty(title)) {
            dialogTitle.setText(title);
        }
        //消息自定义
        if (!TextUtils.isEmpty(msg)) {
            dialogMsg.setText(msg);
        }
        if (showOneBtn){
            clickNegative.setVisibility(View.GONE);//只显示一个按钮，隐藏取消按钮
            clickLine.setVisibility(View.GONE);
        }else {
            clickNegative.setVisibility(View.VISIBLE);
            clickLine.setVisibility(View.VISIBLE);
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
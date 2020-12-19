package com.personal.csp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.google.android.material.tabs.TabLayout;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by csp on 2017/9/22.
 */

public class CspUtil {

    /**
     * 根据手机分辨率从dp转换成px
     *
     * @param context
     * @param dp      device independent pixels(设备独立像素)
     * @return pixels(像素)
     */
    public static int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机分辨率从px转换成dp
     *
     * @param context
     * @param px      pixels(像素)
     * @return device independent pixels(设备独立像素)
     */
    public static int pxToDp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取手机屏幕像素密度dpi
     *
     * @param context
     * @return
     */
    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Toast显示手机分辨率及屏幕像素密度及相对密度
     *
     * @param context
     */
    public static void showDpi(Context context, Activity activity) {
        Point size = new Point();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int x = size.x;
        int y = size.y;
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        float density = context.getResources().getDisplayMetrics().density;
        Toast.makeText(context, "屏幕分辨率为:" + x + "X" + y + "\n屏幕像素密度为:" + densityDpi +
                "\n相对密度为:" + density, Toast.LENGTH_LONG).show();
    }

    /**************************************************时间格式化工具*******************************************************/
    /**
     * 时间格式化工具
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取出生日期
     *
     * @param millisecond
     * @return
     */
    public static String getBornTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间格式化工具
     *
     * @param millisecond 传入毫秒值，返回2017101812011形式
     *                    hh代表12小时制 HH代表24小时制
     * @return
     */
    public static String getDateFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        String time = dateStr.replaceAll("-", "").
                replaceAll(":", "").
                replaceAll("/", "").trim();

        return time;
    }

    public static String getDateYearMonthDay(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        String time = dateStr.replaceAll("-", "").
                replaceAll(":", "").
                replaceAll("/", "").trim();
        return time;
    }

    /**
     * 返回形式2017.11.01
     *
     * @param millisecond
     * @return
     */
    public static String getYearMonthDay(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(millisecond);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间格式化工具
     *
     * @param millisecond 传入毫秒值，返回Hh:mm:ss形式
     * @return
     */
    public static String showTimeCount(long millisecond) {
        if (millisecond >= 360000000) {
            return "00:00:00";
        }
        String timeCount = "";
        long hourc = millisecond / 3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length() - 2, hour.length());
        long minuec = (millisecond - hourc * 3600000) / (60000);
        String minue = "0" + minuec;
        minue = minue.substring(minue.length() - 2, minue.length());
        long secc = (millisecond - hourc * 3600000 - minuec * 60000) / 1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length() - 2, sec.length());
        timeCount = hour + ":" + minue + ":" + sec;
        return timeCount;
    }

    /**
     * 时间格式化工具
     *
     * @param millisecond 传入毫秒值，返回mm:ss形式
     * @return
     */
    public static String showVideoTime(long millisecond) {
        if (millisecond >= 360000000) {
            return "00:00";
        }
        String timeCount = "";
        long hourc = millisecond / 3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length() - 2, hour.length());
        long minuec = (millisecond - hourc * 3600000) / (60000);
        long minutes = millisecond / 60000;
        String minue = "0" + minutes;
        minue = minue.substring(minue.length() - 2, minue.length());
        long secc = (millisecond - hourc * 3600000 - minuec * 60000) / 1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length() - 2, sec.length());
        timeCount = minue + ":" + sec;
        return timeCount;
    }

    /**************************************************手机号密码***********************************************************/
    private static Pattern NUMBER_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(19[0-9])|(16[0-9])|(14[5,7]))\\d{8}$");
    private static Pattern QQ_PATTERN = Pattern.compile("[1-9][0-9]{4,14}");
    private static Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    private static Pattern PASSWORD_PATTERN = Pattern.compile("^([a-z]|[0-9]|[A-Z]){6,16}$");

    /**
     * 将字符串中的连续的多个换行缩减成一个换行
     *
     * @param str 要处理的内容
     * @return 返回的结果
     */
    public static String replaceLineBlanks(String str) {
        String result = "";
        if (str != null) {
            Pattern p = Pattern.compile("(\r?\n(\\s*\r?\n)+)");
            Matcher m = p.matcher(str);
            result = m.replaceAll("\r\n");
        }
        return result;
    }


    /**
     * 正则表达式检测是否是手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNumber(String mobile) {
        return NUMBER_PATTERN.matcher(mobile).matches();
    }

    /**
     * 正则表达式检测是否是QQ号码
     *
     * @param qq
     * @return
     */
    public static boolean isQQNumber(String qq) {
        return QQ_PATTERN.matcher(qq).matches();
    }

    /**
     * 正则表达式检测是否是邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmailNumber(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 正则表达式限制密码，6--16位大小写字母和数字的组合
     *
     * @param password
     * @return
     */
    public static boolean passwordRule(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    /****************************************************MD5加密**********************************************************/
    /**
     * 将byte数组转换为16进制字符串输出
     *
     * @param bytes
     * @return
     */
    public static String convertByteToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            int temp = bytes[i] & 0xff;
            String tempHex = Integer.toHexString(temp);
            if (tempHex.length() < 2) {
                result += "0" + tempHex;
            } else {
                result += tempHex;
            }
        }
        return result;
    }

    /**
     * jdk自带的md5加密
     *
     * @param message
     * @return
     */
    public static String md5jdk(String message) {
        String temp = "";
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] encodeMd5Digest = md5Digest.digest(message.getBytes());
            temp = convertByteToHexString(encodeMd5Digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**************************************************GPS计算距离**********************************************************/
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 获取两个GPS点之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 单位:米
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 求两坐标点之间的距离，返回km
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @param radius
     * @return
     */
    public static String getDistance(double lat1, double lng1, double lat2,
                                     double lng2, int radius) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        if (s < radius) {
            return 0 + "m";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df2 = new DecimalFormat("#.0");
        String msg = "";
        s = s - radius;
        double km = s / 1000;
        if (km > 1) {
            msg = df.format(km) + "km";
        } else {
            msg = df2.format(s) + "m";
        }
        return msg;
    }
    /**************************************************手机设备相关******************************************************/
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }
    /****************************************************************************************************/
    /**
     * 修改电池栏颜色
     */
//    public static void setHintColor(Activity context, int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //   setTranslucentStatus(true);
//            Window win = context.getWindow();
//            WindowManager.LayoutParams winParams = win.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            winParams.flags |= bits;
//            win.setAttributes(winParams);
//            SystemBarTintManager tintManager = new SystemBarTintManager(context);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(color);
//        }
//    }

    /**
     * 获取手机屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getPhoneWidth(Context context) {
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;

        return widthPixels;
    }

    public static int getPhoneDpWidth(Context context) {
        int phoneWidth = getPhoneWidth(context);

        return CspUtil.pxToDp(context, phoneWidth);
    }

    /**
     * 获取手机高度
     *
     * @param context
     * @return
     */
    public static int getPhoneHeight(Context context) {
        int widthPixels = context.getResources().getDisplayMetrics().heightPixels;

        return widthPixels;
    }

    public static int getPhoneDpHeight(Context context) {
        int height = getPhoneHeight(context);

        return CspUtil.pxToDp(context, height);
    }


    public static final long KB = 1024;
    public static final long MB = 1024 * 1024;
    public static final long GB = 1024 * 1024 * 1024;
    public static final long TB = 1024 * 1024 * 1024 * 1024;

    /**
     * 传入一个byte的总数，返回1.23Mb或者12.45Kb的字符串形式
     *
     * @param num
     * @return
     */
    public static String byteToString(long num) {
        if (num < KB) {
            //return new DecimalFormat("#.00").format(num)+"B";
            return num + "B";
        } else if (num < MB) {
            return num / 1024 + "KB";
            // return new DecimalFormat("#.00").format(num/1024)+"KB";
        } else if (num < GB) {
            return num / 1024 / 1024 + "MB";
            //return new DecimalFormat("#.00").format(num/1024/1024)+"MB";
        } else if (num < TB) {
            return num / 1024 / 1024 / 1024 + "GB";
            //return new DecimalFormat("#.00").format(num/1024/1024/1024)+"GB";
        }
        return "0.00MB";
    }

    /**
     * 输入流转String
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String msg = null;

        try {
            while ((msg = reader.readLine()) != null) {
                sb.append(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        // api >= 19
        if (sdkVersion >= 19) {
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /************************************手机网络相关*****************************************************/
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        /**
         * 没有连接网络
         */
        final int NETWORK_NONE = -1;
        /**
         * 移动网络
         */
        final int NETWORK_MOBILE = 0;
        /**
         * 无线网络
         */
        final int NETWORK_WIFI = 1;
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Object systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 判断一个单字符string是否为字母
     *
     * @param msg
     * @return
     */
    public static boolean isLetter(String msg) {
        if (msg.length() == 1) {
            int ascall = msg.getBytes()[0];
            return ascall >= 65 && ascall <= 90 || ascall == 35;
        } else {
            return false;
        }
    }

    /**
     * 设置TabLayout indicator的长度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }






    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}

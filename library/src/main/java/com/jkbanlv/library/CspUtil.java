package com.jkbanlv.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
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
        return  simpleDateFormat.format(date);
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
    private static Pattern NUMBER_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
    private static Pattern QQ_PATTERN = Pattern.compile("[1-9][0-9]{4,14}");
    private static Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    private static Pattern PASSWORD_PATTERN = Pattern.compile("^([a-z]|[0-9]|[A-Z]){6,16}$");

    /**
     * 正则表达式检测是否是手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isPhoneNumber(String mobile) {
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
    public static boolean isPassword(String password) {
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
     * @return px
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
     * @return px
     */
    public static int getPhoneHeight(Context context) {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;

        return heightPixels;
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
     *格式化文件大小
     *
     * @param num 文件大小 单位字节Byte
     * @return
     */
    public static String transFromFileSize(long num) {
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

    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * 60 * 1000;
    /**
     * 封装一个时间显示的方法(用于一般的发帖，评论功能时间显示)
     * @param time
     * @return
     */
    public static String CSPFormatTime(String time) {
        boolean isToday = false, isYesterday = false, isThisYear = false;
        //拿到时间的毫秒值
        long millions = transformStringToLong(time);
        String msg = "";
        //拿到当前时间的毫秒值
        long currentTime = System.currentTimeMillis();

        isToday = isToday(millions);
        isYesterday = isYesterday(millions);
        isThisYear = isThisYear(millions);

        Calendar calendar = Calendar.getInstance();
        Date date = new Date(millions);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        //求时间差
        long diffTime = currentTime - millions;
        if (diffTime < ONE_MINUTE) {
            msg = "刚刚";
        } else if (diffTime < ONE_HOUR) {
            int minu = (int) (diffTime / 60000);
            msg = minu + "分钟前";
        } else if (isToday) {
            String str_hour = "";
            if (hour <= 9) {
                str_hour = "0" + hour;
            } else {
                str_hour = hour + "";
            }

            String str_minute = "";
            if (minute <= 9) {
                str_minute = "0" + minute;
            } else {
                str_minute = minute + "";
            }
            msg = "今天 " + str_hour + ":" + str_minute;
        } else if (isYesterday) {
            String str_hour = "";
            if (hour <= 9) {
                str_hour = "0" + hour;
            } else {
                str_hour = hour + "";
            }

            String str_minute = "";
            if (minute <= 9) {
                str_minute = "0" + minute;
            } else {
                str_minute = minute + "";
            }
            msg = "昨天 " + str_hour + ":" + str_minute;
        } else if (isThisYear) {
            msg = (month + 1) + "月" + day + "日";
        } else {

            String str_day = "";
            if (day <= 9) {
                str_day = "0" + day;
            } else {
                str_day = day + "";
            }
            msg = year + "/" + (month + 1) + "/" + str_day;
        }
        return msg;
    }

    /**
     * 将字符串"yyyy-MM-dd HH:mm:ss"解析成毫秒值
     *
     * @param time 符合格式的时间字符串
     * @return
     */
    private static long transformStringToLong(String time) {
        long millions = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(time);
            millions = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millions;
    }

    /**
     * 判断当前时间是否是在今天
     * @param time 毫秒值
     * @return
     */
    public static boolean isToday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            return diffDay == 0;
        }
        return false;
    }

    /**
     * 判断当前时间是否是昨天
     * @param time  毫秒值
     * @return
     */
    public static boolean isYesterday(long time) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            return diffDay == -1;
        }
        return false;
    }

    /**
     * 判断当前时间是否是今年
     * @param time 毫秒值
     * @return
     */
    public static boolean isThisYear(long time) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR) - (pre.get(Calendar.YEAR));

        return year == 0;
    }
    /**
     * 2 * 获取版本号 1.0.1 * @return 当前应用的版本号 1.0.1
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            int versioncode = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *比较两个string类型的versionName
     * @param version  1.0.0
     * @param newVersion 1.0.1
     * @return
     */
    public static boolean versionNeedUpdate(String version, String newVersion) {
        Log.i("tag", "version: " + version + "\tnewVersion:" + newVersion);
        String[] versions = version.split("\\.");
        String[] newVersions = newVersion.split("\\.");
        Log.i("tag", "versions: " + versions.length + "\tnewVersions:" + newVersions.length);
        int v1 = Integer.parseInt(versions[0]);
        int v2 = Integer.parseInt(versions[1]);
        int v3 = Integer.parseInt(versions[2]);
        int n1 = Integer.parseInt(newVersions[0]);
        int n2 = Integer.parseInt(newVersions[1]);
        int n3 = Integer.parseInt(newVersions[2]);
        if (n1 > v1) {
            return true;
        } else if (n1 == v1) {
            if (n2 > v2) {
                return true;
            } else if (n2 == v2) {
                return n3 > v3;
            }
        }
        return false;
    }
    /**
     * 判断一个应用是否是启动状态
     *
     * @param context
     * @return
     */
    public static boolean getCurrentTask(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取当前所有存活task的信息
        List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(Integer.MAX_VALUE);
        //遍历，若task的name与当前task的name相同，则返回true，否则，返回false
        for (ActivityManager.RunningTaskInfo process : appProcessInfos) {
            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 高斯模糊图片处理
     *
     * @param context 上下文对象
     * @param source  bitmap源文件
     * @param radius  半径
     * @param scale   压缩的比例>1
     * @return
     */
    public static Bitmap toGaussianBlur(Context context, Bitmap source, int radius, float scale) {
        int width = Math.round(source.getWidth() / scale);
        int height = Math.round(source.getHeight() / scale);
        Bitmap inputBmp = Bitmap.createScaledBitmap(source, width, height, false);
        RenderScript renderScript = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBmp);
        renderScript.destroy();
        return inputBmp;
    }


    /**
     * 转换bitmap位图资源
     * @param source  bitmap资源
     * @param width   要转换的宽度px
     * @param height  要转换的高度px
     * @return
     */
    public static Bitmap transformBitmap(Bitmap source, int width, int height){
        int srcWidth=source.getWidth();
        int srcHeight=source.getHeight();

        float scaleWidth = (float) new BigDecimal((float)width/srcWidth).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        float scaleHeight = (float) new BigDecimal((float)height/srcHeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //缩放比例，千万不要用int接收
        float scale=scaleWidth>scaleHeight?scaleWidth:scaleHeight;
        // 取得想要缩放的matrix參數
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newBitmap = Bitmap.createBitmap(source, 0, 0, srcWidth, srcHeight, matrix, true);
        return newBitmap;
    }
    /**
     * 判断一个string字符串是否全部由汉字组成
     * @param msg
     * @return
     */
    public static boolean isAllChineseCharacters(String msg){
        String reg = "[\\u4e00-\\u9fa5]+";
        return msg.matches(reg);
    }
    /********************************************SDCardHelper***********************************************/
    /**
     * sd卡挂载
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    /**
     * 获取sd卡常用目录
     *
     * @return
     */
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }
    /**
     * 获取SD卡九大公有目录的路径
     *
     * @param type
     * @return
     */
    public static String getSDCardPublicDir(String type) {
        if (isSDCardMounted()) {
            return Environment.getExternalStoragePublicDirectory(type).toString();
        }
        return null;
    }
    /**
     * 获取SD卡私有Cache目录的路径
     *
     * @param context
     * @return
     */
    public static String getSDCardPrivateCacheDir(Context context) {
        if (isSDCardMounted()) {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取SD卡私有Files目录的路径
     *
     * @param context
     * @param type
     * @return
     */
    public static String getSDCardPrivateFilesDir(Context context, String type) {
        if (isSDCardMounted()) {
            return context.getExternalFilesDir(type).getAbsolutePath();
        }
        return null;
    }
    /**
     * 获取SD卡的完整空间大小，返回MB
     *
     * @return
     */
    public static long getSDCardSize() {
        if (isSDCardMounted()) {
            StatFs fs = new StatFs(getSDCardBaseDir());
            long count = 0;
            long size = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                count = fs.getBlockCountLong();
                size = fs.getBlockSizeLong();
            }
            return count * size / 1024 / 1024;
        }
        return 0;
    }
    /**
     * 往SD卡的自定义目录下保存文件
     *
     * @param data
     * @param dir
     * @param fileName
     * @return
     */
    public static boolean saveFileToSDCardCustomDir(byte[] data, String dir, String fileName) {
        BufferedOutputStream bos = null;
        if (isSDCardMounted()) {
            File file = new File(getSDCardBaseDir() + File.separator + dir);
            if (!file.exists()) {
                file.mkdirs();// 递归创建自定义目录
            }
            Log.i("tag", "saveFileToSDCardCustomDir: " + file.getAbsolutePath() + "\t文件是否存在：" + file.exists());
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));
                bos.write(data);
                bos.flush();
                Log.i("tag", "saveFileToSDCardCustomDir: 保存成功");
                return true;
            } catch (Exception e) {
                Log.i("tag", "saveFileToSDCardCustomDir: 捕获异常：" + e.toString());
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

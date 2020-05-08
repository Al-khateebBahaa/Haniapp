package com.ahmedMustafa.Hani.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import com.ahmedMustafa.Hani.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Common {

    public static boolean isConnected(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting() ? true : false;
    }

    public static void changLang(Context context, String lan) {
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("WrongConstant")
    public static void reviewApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    private static String getMonthName(Context context,int month){

        String month_name = "";
        String[] monthsName = context.getResources().getStringArray(R.array.month);
        if (month == 1) {

            month_name = monthsName[0];

        } else if (month == 2) {

            month_name = monthsName[1];

        } else if (month == 3) {

            month_name = monthsName[2];

        } else if (month == 4) {

            month_name = monthsName[3];

        } else if (month == 5) {

            month_name = monthsName[4];

        } else if (month == 6) {

            month_name = monthsName[5];

        } else if (month == 7) {

            month_name = monthsName[6];

        } else if (month == 8) {

            month_name = monthsName[7];

        } else if (month == 9) {

            month_name = monthsName[8];

        } else if (month == 10) {

            month_name = monthsName[9];

        } else if (month == 11) {

            month_name = monthsName[10];

        } else if (month == 12) {

            month_name = monthsName[11];

        }
        return month_name;
    }
    public static String getTime(String date, Context context) {


        try {
            Calendar time = Calendar.getInstance();
            time.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));

            Calendar cTime = Calendar.getInstance();
            cTime.setTime(new Date());
            boolean isDay = cTime.get(Calendar.DAY_OF_MONTH) == time.get(Calendar.DAY_OF_MONTH);
            boolean isMonth = cTime.get(Calendar.MONTH) == time.get(Calendar.MONTH);
            boolean isYear = cTime.get(Calendar.YEAR) == time.get(Calendar.YEAR);
            boolean isYasterDay = (cTime.get(Calendar.DAY_OF_MONTH) - time.get(Calendar.DAY_OF_MONTH)) == 1;
            String monthName = getMonthName(context, time.get(Calendar.MONTH));
            String day = time.get(Calendar.DAY_OF_MONTH) + "";

            if (isYear) {
                // current year
                if (isMonth) {

                    if (isDay) return context.getString(R.string.today);
                    else if (isYasterDay) return context.getString(R.string.yasterDay);
                    else return " منذ "+day + "  " +monthName;

                } else {
                    return " منذ "+day + "  " +monthName;
                }

            } else {
                return " منذ "+day + " - "+monthName+" - " + time.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }
}

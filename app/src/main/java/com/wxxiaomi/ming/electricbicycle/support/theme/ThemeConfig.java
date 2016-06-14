package com.wxxiaomi.ming.electricbicycle.support.theme;

import android.support.v4.content.ContextCompat;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;


/**
 * Created by kevinwu on 16-2-24.
 */
public class ThemeConfig {
    //颜色选择器颜色值
    public static final int themeColor[]={
            ContextCompat.getColor(EBApplication.applicationContext,R.color.colorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext, R.color.nightColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.brownColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.cyanColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.tealColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.lightGreenColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.blueColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.blueGreyColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.purpleColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.orangeColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.mklColorPrimary),
            ContextCompat.getColor(EBApplication.applicationContext,R.color.bgColorPrimary)
    };
    //主题样式值
    public static final int themeStyle[]={
            R.style.AppTheme,
            R.style.DarkTheme,
            R.style.BrownTheme,
            R.style.CyanTheme,
            R.style.TealTheme,
            R.style.LightgreenTheme,
            R.style.BlueTheme,
            R.style.BlueGreyTheme,
            R.style.PurpleTheme,
            R.style.OrangeTheme,
            R.style.MklTheme,
            R.style.BgTheme
    };
    //主题对话框样式值
    public static final int themeDialogStyle[]={
            R.style.DialogTheme,
            R.style.DarkDialogTheme,
            R.style.BrownDialogTheme,
            R.style.CyanDialogTheme,
            R.style.TealDialogTheme,
            R.style.LightgreenDialogTheme,
            R.style.BlueDialogTheme,
            R.style.BlueGreyDialogTheme,
            R.style.PurpleDialogTheme,
            R.style.OrangeDialogTheme,
            R.style.MklDialogTheme,
            R.style.BgDialogTheme
    };
}

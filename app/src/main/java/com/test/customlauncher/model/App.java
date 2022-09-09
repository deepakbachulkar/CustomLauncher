package com.test.customlauncher.model;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class App implements Comparator {
    private String name, packageName, launcherClassName, versionName;
    private int  versionCode, icon;
    private Drawable drawableIcon;
    private String activityName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLauncherClassName() {
        return launcherClassName;
    }

    public void setLauncherClassName(String launcherClassName) {
        this.launcherClassName = launcherClassName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    @Override
    public int compare(Object o, Object t1) {
        return ((App)o).name.compareTo(((App)t1).name);
    }
}

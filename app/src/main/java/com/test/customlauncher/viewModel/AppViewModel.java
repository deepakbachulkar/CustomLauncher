package com.test.customlauncher.viewModel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.customlauncher.model.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppViewModel extends ViewModel {

    public static MutableLiveData<List<App>> appsLD = new MutableLiveData<>();

    public static void getAllInstalledApkInfo(Context context){
        List<App> apps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent,0);
        for(ResolveInfo resolveInfo : resolveInfoList){
            App app = new App();
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            app.setPackageName(activityInfo.applicationInfo.packageName);
            app.setIcon(activityInfo.applicationInfo.icon);
            app.setActivityName(activityInfo.name);
            PackageInfo packageInfo;
            try {
                packageInfo = context.getPackageManager().getPackageInfo(activityInfo.applicationInfo.packageName, 0);
                app.setVersionCode(packageInfo.versionCode);
                app.setVersionName(packageInfo.versionName);
                Drawable drawable = context.getPackageManager().getApplicationIcon(activityInfo.applicationInfo.packageName);
                app.setDrawableIcon(drawable);

                Resources res = context.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo);
                // if activity label res is
                String name;
                if (activityInfo.labelRes != 0) {
                    name = res.getString(activityInfo.labelRes);
                } else {
                    name = activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                }

                app.setName(name);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            apps.add(app);
        }
        Log.d("App", "Apps Size: " + apps.size());
        Collections.sort(apps, new App());
        appsLD.setValue(apps);
    }

}

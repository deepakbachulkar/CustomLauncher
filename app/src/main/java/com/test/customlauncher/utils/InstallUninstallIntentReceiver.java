package com.test.customlauncher.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.test.customlauncher.viewModel.AppViewModel;

public class InstallUninstallIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "App install/uninstall", Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
            Utils.getInstance().sendNotification(context, "App Uninstall", intent.getData().toString().concat(" this app uninstall"));
        }else
            Utils.getInstance().sendNotification(context, "App Install", intent.getData().toString());
        AppViewModel.getAllInstalledApkInfo(context);
    }




}

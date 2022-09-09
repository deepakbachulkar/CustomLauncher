package com.test.customlauncher.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.test.customlauncher.model.App;
import com.test.customlauncher.R;
import com.test.customlauncher.adapter.AppAdapter;
import com.test.customlauncher.databinding.ActivityMainBinding;
import com.test.customlauncher.databinding.ItemAppDetailBinding;
import com.test.customlauncher.utils.InstallUninstallIntentReceiver;
import com.test.customlauncher.viewModel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<App> apps;
    private Context mContext;
    private InstallUninstallIntentReceiver receiver;
    public AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        try {
            AppViewModel.getAllInstalledApkInfo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addDataScheme("package");
        receiver = new InstallUninstallIntentReceiver();
        registerReceiver(receiver, intentFilter);

        AppViewModel.appsLD.observe(this, apps -> {
            if(adapter== null){
                setAdapter();
            }
            if(apps!=null) {
                adapter.setData(apps);
                this.apps= apps;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init(){
        Log.d("App", "init app");
        binding.search.setActivated(true);
        binding.search.onActionViewExpanded();
        binding.search.setIconified(false);
        binding.search.clearFocus();

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<App> list = new ArrayList<>();
                if(apps!=null) {
                    if(newText.equals("")){
                        adapter.setData(apps);
                    }else {
                        for (int i = 0; i < apps.size(); i++) {
                            if (apps.get(i).getName().toLowerCase().contains(newText)) {
                                list.add(apps.get(i));
                            }
                        }

                        adapter.setData(list);
                    }
                }

                return false;
            }
        });
    }

    private void setAdapter(){
        if(apps == null)
            apps = new ArrayList<>();
        GridLayoutManager layoutManager= new GridLayoutManager(this, 4);
        binding.rvApps.setLayoutManager(layoutManager);
        binding.rvApps.setItemAnimator(new DefaultItemAnimator());
        adapter = new AppAdapter(apps, new AppAdapter.ClickInterface() {
            @Override
            public void onSelect(App app) {
                startLauncherActivity(app.getPackageName());
            }

            @Override
            public void onLongPress(App app) {
                showAlertDialogAppDetails(app);
            }
        });
        binding.rvApps.setAdapter(adapter);
    }

    public void startLauncherActivity(String packageName) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void showAlertDialogAppDetails(App app) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mContext.getString(R.string.app_details));
        ItemAppDetailBinding binding = ItemAppDetailBinding.inflate(LayoutInflater.from(mContext));
        builder.setView(binding.getRoot());
        binding.name.setText(app.getName());
        binding.versionCode.setText(mContext.getResources().getString(R.string.version_code).concat(app.getVersionCode()+""));
        binding.versionName.setText(mContext.getResources().getString(R.string.version_name).concat(app.getVersionName()));
        binding.packageName.setText(app.getPackageName());
        binding.launcherActivity.setText(app.getActivityName());
        binding.icon.setBackgroundDrawable(app.getDrawableIcon());
        builder.setPositiveButton(mContext.getResources().getString(R.string.ok), (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
package com.elephantgroup.blog.ui.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.custom.AlwaysMarqueeTextView;
import com.elephantgroup.blog.custom.MyToast;
import com.elephantgroup.blog.ui.login.LoginUI;
import com.elephantgroup.blog.util.Constans;
import com.elephantgroup.blog.util.MySharedPrefs;
import com.elephantgroup.blog.util.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Activity基类
 * Created on 2016/11/10.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {
    protected ImageView mBack;
    protected AlwaysMarqueeTextView mTitle;
    public static List<Activity> activitys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.gc();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            initSystemBar(BaseFragmentActivity.this,R.color.colorPrimary);
        }
        activitys.add(this);
        checkPermission();
        setContentView();
        ButterKnife.bind(this);
        initPublicView();
        initData();
    }

    void checkPermission() {
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if ((checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if (permissionsList.size() != 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), Constans.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case Constans.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++){
                    perms.put(permissions[i], grantResults[i]);
                }
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                }
            }
            return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        activitys.remove(this);
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initPublicView() {
        mBack = (ImageView) findViewById(R.id.app_back);
        mTitle = (AlwaysMarqueeTextView) findViewById(R.id.app_title);
        if(mBack != null){
            mBack.setOnClickListener(this);
        }
    }

    protected void setTitle(String title) {
        if(mTitle != null){
            mTitle.setText(title);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_to_middle_in, R.anim.push_middle_to_right_out);
    }

    protected void showToast(String msg){
        MyToast.showToast(this, msg);
    }

    protected abstract void setContentView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_back:
                Utils.hiddenKeyBoard(this);
                Utils.exitActivityAndBackAnim(this,true);
                break;
        }
    }


    /**
     * 改变系统标题栏颜色
     * @param activity
     * @param color   color xml文件下的颜色
     */
    public static void initSystemBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            // 使用颜色资源
            tintManager.setStatusBarTintResource(color);
//			tintManager.setStatusBarTintColor(color);
        }
    }
    /**
     * 设置系统标题栏的透明度
     * @param activity
     * @param on
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 退出应用
     */
    public void exitApp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MySharedPrefs.clearUserInfo(BaseFragmentActivity.this);
                    exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(BaseFragmentActivity.this, LoginUI.class));
                Utils.openNewActivityAnim(BaseFragmentActivity.this, true);
            }
        }).start();
    }

    public static void exit() {
        if (activitys != null && !activitys.isEmpty()) {
            for (Activity act : activitys) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            }
            activitys.clear();
        }
    }
}

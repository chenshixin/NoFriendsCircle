package com.chenshixin.nofriendscircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenshixin.nofriendscircle.util.SettingUtil;
import com.chenshixin.nofriendscircle.util.SystemUtil;
import com.chenshixin.nofriendscircle.util.UmengUtil;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_TO_SYSTEM_SETTING = 0x1000;

    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private TextView mCountTV;
    private ImageView mWechartIV;
    private ImageView mGlassIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFabBg();
        refreshCount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TO_SYSTEM_SETTING:
                checkUserTurnOnSystemSwitch();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                onFabClicked(v);
                break;
        }
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mCountTV = (TextView) findViewById(R.id.tv_main_stop_times);
        mWechartIV = (ImageView) findViewById(R.id.iv_main_wechart);
        mGlassIV = (ImageView) findViewById(R.id.iv_main_glass);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void refreshFabBg() {
        if (SystemUtil.isAccessibilityEnabled(MainActivity.this) && SettingUtil.isServiceEnabled()) {
            Toast.makeText(MainActivity.this, getString(R.string.main_status_start), Toast.LENGTH_SHORT).show();
            mFab.setImageResource(android.R.drawable.ic_media_pause);
            showStartedAnimation();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.main_status_stop), Toast.LENGTH_SHORT).show();
            mFab.setImageResource(android.R.drawable.ic_media_play);
            showStoppedAnimation();
        }
    }

    private void setListener() {
        mFab.setOnClickListener(this);
    }

    private void refreshCount() {
        mCountTV.setText("" + SettingUtil.getStopCount());
    }

    private void onFabClicked(View view) {
        if (!SystemUtil.isAccessibilityEnabled(MainActivity.this)) {
            showSystemDisabledTip(view);
        } else {
            if (SettingUtil.isServiceEnabled()) {
                SettingUtil.setServiceEnable(false);
                MobclickAgent.onEvent(this, UmengUtil.MAIN_PRESS_TO_STOP);
            } else {
                SettingUtil.setServiceEnable(true);
                MobclickAgent.onEvent(this, UmengUtil.MAIN_PRESS_TO_START);
            }
            refreshFabBg();
        }
    }

    private void showSystemDisabledTip(View view) {
        Snackbar.make(view, getString(R.string.main_snackbar_title), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.main_snackbar_action), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS), REQUEST_TO_SYSTEM_SETTING);
                    }
                }).show();
    }

    public void checkUserTurnOnSystemSwitch() {
        if (!SystemUtil.isAccessibilityEnabled(MainActivity.this)) {
            Toast.makeText(MainActivity.this, getString(R.string.main_start_fail), Toast.LENGTH_SHORT).show();
        } else {
            SettingUtil.setServiceEnable(true);
        }
    }

    private void showStoppedAnimation() {
        ViewCompat.setRotation(mWechartIV, 0);
        mWechartIV.animate()
                .rotation(720)
                .setStartDelay(200)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        mGlassIV.animate().alpha(0).setDuration(300);
        mGlassIV.animate().scaleX(0).setDuration(500);
        mGlassIV.animate().scaleY(0).setDuration(500);
    }

    private void showStartedAnimation() {
        ViewCompat.setScaleX(mGlassIV, 0);
        ViewCompat.setScaleY(mGlassIV, 0);
        ViewCompat.setAlpha(mGlassIV, 1);
        mWechartIV.animate().cancel();
        mGlassIV.animate().scaleX(1).setDuration(500);
        mGlassIV.animate().scaleY(1).setDuration(500);
    }
}

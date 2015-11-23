package com.chenshixin.nofriendscircle.service;

import android.accessibilityservice.AccessibilityService;
import android.os.AsyncTask;
import android.view.accessibility.AccessibilityEvent;

import com.chenshixin.nofriendscircle.R;
import com.chenshixin.nofriendscircle.util.PreferenceUtil;
import com.chenshixin.nofriendscircle.util.SettingUtil;

import java.util.List;

/**
 * Created by csx.
 */
public class CloseCircleService extends AccessibilityService {

    public static final String SERVICE_NAME = "com.chenshixin.nofriendscircle/com.chenshixin.nofriendscircle.service.CloseCircleService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        //点击事件
        if (eventType != AccessibilityEvent.TYPE_VIEW_CLICKED) {
            return;
        }
        //不开启状态
        if (!SettingUtil.isServiceEnabled()) {
            return;
        }
        if (needToBack(event)) {
            performBack();
        }
    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 是否需要返回
     *
     * @param event AccessibilityEvent
     */
    private boolean needToBack(AccessibilityEvent event) {
        List<CharSequence> eventText = event.getText();
        return eventText != null &&
                (eventText.contains(getString(R.string.nodeInfo_text_friend_circle))
                        || eventText.contains(getString(R.string.nodeInfo_text_person_album)));
    }

    private void performBack() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                performGlobalAction(GLOBAL_ACTION_BACK);
                SettingUtil.increaseStopCount();
            }
        }.execute();
    }

}

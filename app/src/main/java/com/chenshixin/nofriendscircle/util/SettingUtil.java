package com.chenshixin.nofriendscircle.util;

/**
 * Created by csx.
 */
public class SettingUtil {

    public static void setServiceEnable(boolean isEnabled) {
        PreferenceUtil.saveBoolean(PreferenceUtil.KEY_SERVICE_START, isEnabled);
    }

    public static boolean isServiceEnabled() {
        return PreferenceUtil.getBoolean(PreferenceUtil.KEY_SERVICE_START, false);
    }

    /**
     * 获取屏蔽次数
     *
     * @return 屏蔽次数
     */
    public static int getStopCount() {
        return PreferenceUtil.getInt(PreferenceUtil.KEY_STOP_COUNT, 0);
    }

    /**
     * 屏蔽次数+1
     */
    public static void increaseStopCount() {
        PreferenceUtil.saveInt(PreferenceUtil.KEY_STOP_COUNT, getStopCount() + 1);
    }

}

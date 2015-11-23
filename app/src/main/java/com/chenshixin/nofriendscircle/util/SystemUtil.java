package com.chenshixin.nofriendscircle.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.chenshixin.nofriendscircle.service.CloseCircleService;

/**
 * Created by csx.
 */
public class SystemUtil {

    public static final String TAG = "SystemUtil";

    public static boolean isAccessibilityEnabled(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.d(TAG, "ACCESSIBILITY: " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.d(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }

        TextUtils.SimpleStringSplitter stringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            Log.d(TAG, "***ACCESSIBILIY IS ENABLED***: ");
            String settingValue = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            Log.d(TAG, "Setting: " + settingValue);
            if (settingValue != null) {
                stringColonSplitter.setString(settingValue);
                while (stringColonSplitter.hasNext()) {
                    String accessabilityService = stringColonSplitter.next();
                    Log.d(TAG, "Setting: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(CloseCircleService.SERVICE_NAME)) {
                        Log.d(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
            Log.d(TAG, "***END***");
        } else {
            Log.d(TAG, "***ACCESSIBILIY IS DISABLED***");
        }
        return false;
    }

}

package com.kiemtien.hotlist.config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private static AppConfig sAppConfig;
    private SharedPreferences mSharedPreference;
    private final String PREF_NAME = "hot_list_config";
    private final String PREF_MAX_NUMBER_DELAY_AD = "max_number_delay_ad";
    private final String PREF_MAX_TIME_DELAY_AD = "max_time_delay_ad";
    private final String SHOW_LINK_PRIVACY_POLICY = "show_privacy_policy_link";

    private int maxNumberDelayAd;
    private long maxTimeDelayAd;
    private boolean isPrivacyPolicyShortcutLink;

    private AppConfig() {
    }

    private AppConfig(Context context) {
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static AppConfig getInstance(Context context) {
        if (sAppConfig == null) {
            sAppConfig = new AppConfig(context);
            sAppConfig.init();
        }
        return sAppConfig;
    }

    public static AppConfig getInstance() {
        return sAppConfig;
    }

    private void init() {
        maxNumberDelayAd = mSharedPreference.getInt(PREF_MAX_NUMBER_DELAY_AD, 5);
        maxTimeDelayAd = mSharedPreference.getLong(PREF_MAX_TIME_DELAY_AD, 5 * 60 * 1000); //5 minutes
        isPrivacyPolicyShortcutLink = mSharedPreference.getBoolean(SHOW_LINK_PRIVACY_POLICY, true);
    }

    public void setMaxNumberDelayAd(int max) {
        maxNumberDelayAd = max;
        mSharedPreference.edit().putInt(PREF_MAX_NUMBER_DELAY_AD, max).apply();
    }

    public int getMaxNumberDelayAd() {
        return maxNumberDelayAd;
    }

    public long getMaxTimeDelayAd() {
        return maxTimeDelayAd;
    }

    public void setMaxTimeDelayAd(long maxTimeDelayAd) {
        this.maxTimeDelayAd = maxTimeDelayAd;
        mSharedPreference.edit().putLong(PREF_MAX_TIME_DELAY_AD, maxTimeDelayAd).apply();
    }

    public boolean isPrivacyPolicyShortcutLink() {
        return isPrivacyPolicyShortcutLink;
    }

    public void setPrivacyPolicyShortcutLink(boolean privacyPolicyShortcutLink) {
        isPrivacyPolicyShortcutLink = privacyPolicyShortcutLink;
        mSharedPreference.edit().putBoolean(SHOW_LINK_PRIVACY_POLICY, privacyPolicyShortcutLink).apply();
    }
}

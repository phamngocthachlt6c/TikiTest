package com.kiemtien.hotlist.config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private static AppConfig sAppConfig;
    private SharedPreferences mSharedPreference;
    private final String PREF_NAME = "hot_list_config";
    private final String PREF_MAX_NUMBER_DELAY_AD = "max_number_delay_ad";
    private final String PREF_MAX_TIME_DELAY_AD = "max_time_delay_ad";

    private int maxNumberDelayAd;
    private long maxTimeDelayAd;

    private AppConfig(){}
    private AppConfig(Context context) {
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static AppConfig getInstance(Context context) {
        if(sAppConfig == null) {
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
}

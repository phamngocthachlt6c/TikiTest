package com.kiemtien.hotlist.util;

import com.kiemtien.hotlist.config.AppConfig;

import java.util.Random;

public class AdsDecider {

    private Random random = new Random();

    private static int delayCounter = 0;
    private static long lastTimeShow = System.currentTimeMillis();

    public boolean canShowAds() {
        AppConfig appConfig = AppConfig.getInstance();
        if (appConfig == null) {
            return false;
        }
        delayCounter++;
        if (delayCounter > appConfig.getMaxNumberDelayAd()) {
            reset();
            return true;
        }
        if (System.currentTimeMillis() - lastTimeShow > appConfig.getMaxTimeDelayAd()) {
            reset();
            return true;
        }
        return false;
    }

    /**
     * @return 1 for video ad 0 for interstitial
     */
    public int showWhat() {
        return random.nextBoolean() ? 0 : 1;
    }

    private void reset() {
        delayCounter = 0;
        lastTimeShow = System.currentTimeMillis();
    }
}

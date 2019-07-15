package com.kiemtien.hotlist.model;

import com.google.gson.annotations.SerializedName;

public class CommonConfig {
    @SerializedName("max_number_delay")
    private int maxNumberDelay;
    @SerializedName("max_time_delay")
    private long maxTimeDelay;

    public int getMaxNumberDelay() {
        return maxNumberDelay;
    }

    public void setMaxNumberDelay(int maxNumberDelay) {
        this.maxNumberDelay = maxNumberDelay;
    }

    public long getMaxTimeDelay() {
        return maxTimeDelay;
    }

    public void setMaxTimeDelay(long maxTimeDelay) {
        this.maxTimeDelay = maxTimeDelay;
    }
}

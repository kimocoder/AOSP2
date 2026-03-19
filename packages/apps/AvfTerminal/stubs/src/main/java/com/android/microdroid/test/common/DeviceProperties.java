package com.android.microdroid.test.common;

public class DeviceProperties {
    public interface PropertyGetter {
        String get(String key);
    }

    public static DeviceProperties create(PropertyGetter getter) {
        return new DeviceProperties();
    }

    public boolean isCuttlefish() { return false; }
    public boolean isGoldfish() { return false; }
}

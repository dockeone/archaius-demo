package com.archaius.demo.configuration;

import java.io.Closeable;

public interface PropertyManagerComponent extends Closeable {

    public String getString(String key);

    public String getString(String key, String defaultValue);

    public int getInt(String key, int defaultValue);

    public long getLong(String key, int defaultValue);

    public boolean getBoolean(String key, boolean defaultValue);

    public void setOverrideProperty(String key, Object value);

    public void setAdditionalURLs(String additionalURLs);
}
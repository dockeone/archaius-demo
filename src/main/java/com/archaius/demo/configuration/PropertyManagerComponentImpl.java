package com.archaius.demo.configuration;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

public class PropertyManagerComponentImpl implements PropertyManagerComponent {

    private static final Logger LOGGER = Logger.getLogger(PropertyManagerComponentImpl.class);

    private String configPropName;
    private String additionalURLs;

    public PropertyManagerComponentImpl(String configPropName){
        this.configPropName = configPropName;

        String env = System.getProperty("archaius.deployment.environment");

        /* Checks if "env" property is correctly set up */
        if (Strings.isNullOrEmpty(env)) {
            String msg = "System property 'archaius.deployment.environment' is empty. " +
                    "Configuration may be invalid. " + 
                    "Set APP_ENV environment variable to populate the environment appropriately.";
            LOGGER.error(msg);
        }

        String defaultFileName = this.configPropName + "-" + env + ".properties";
        System.setProperty("archaius.configurationSource.defaultFileName", defaultFileName);

        if (this.additionalURLs != null) {
            System.setProperty("archaius.configurationSource.additionalUrls", this.additionalURLs);
        }

        /* Displays information about Archaius Configuration */
        LOGGER.debug("Configuration: archaius.deployment.environment = " + env);
        LOGGER.debug("Configuration: configPropName:  " + this.configPropName);
        LOGGER.debug("Configuration: additionalUrls:  " + this.additionalURLs);

        /* Load Properties from text File. */
        try {
            ConfigurationManager.loadCascadedPropertiesFromResources(this.configPropName + "-" + env);
        } catch (Exception ex) {
            String msg = "Error while loading Properties.";
            LOGGER.error(msg);
        }
    }

    public String getString(String key) {

        String prop = this.getString(key, null);

        if (prop == null) {
            String msg = String.format("Property not Found: %s in File: %s", key, System.getProperty("archaius.configurationSource.defaultFileName"));
            LOGGER.error(msg);
        }
        return prop;
    }

    public String getString(String key, String defaultValue) {
        final DynamicStringProperty property = DynamicPropertyFactory.getInstance().getStringProperty(key, defaultValue);
        return property.get();
    }

    public int getInt(String key, int defaultValue) {
        final DynamicIntProperty property = DynamicPropertyFactory.getInstance().getIntProperty(key, defaultValue);
        return property.get();
    }

    public long getLong(String key, int defaultValue) {
        final DynamicLongProperty property = DynamicPropertyFactory.getInstance().getLongProperty(key, defaultValue);
        return property.get();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        final DynamicBooleanProperty property = DynamicPropertyFactory.getInstance().getBooleanProperty(key, defaultValue);
        return property.get();
    }


    public void close() {
        LOGGER.debug("Close Archaius configuration files");
    }

    public void setConfigPropName(String configPropName) {
        this.configPropName = configPropName;
    }

    public void setAdditionalURLs(String additionalURLs) {
        this.additionalURLs = additionalURLs;
    }

    public void setOverrideProperty(String key, Object value) {
        // TODO Auto-generated method stub
    }

}

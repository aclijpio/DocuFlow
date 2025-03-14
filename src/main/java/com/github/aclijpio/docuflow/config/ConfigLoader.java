package com.github.aclijpio.docuflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.aclijpio.docuflow.config.source.AppConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.yaml";

    private AppConfig appConfig;


    public static AppConfig loadConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resourseUrl = classLoader.getResource(CONFIG_FILE);
        if (resourseUrl == null) throw new AppConfigException("Config file not found: " + CONFIG_FILE);
        File file = new File(resourseUrl.getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            return om.readValue(file, AppConfig.class);
        }catch (IOException e){
            throw new AppConfigException("Failed to find resource", e);
        }
    }
}

package com.fine.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件配置类
 * 与yml配置文件中file项对应
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    private String dir;

}

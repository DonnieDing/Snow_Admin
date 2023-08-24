package com.snow.dcl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FileConfig
 * (功能描述)
 * 文件存储配置
 * @Author Dcl_Snow
 * @Create 2023/8/23 11:15
 * @Version 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    private Long maxSize;

    private Long avatarMaxSize;

    private FileUse mac;

    private FileUse linux;

    private FileUse windows;

    public FileUse getOsFileUse(){
        String osProperty = System.getProperty("os.name");
        if(osProperty.toLowerCase().startsWith("windows")) {
            return windows;
        } else if(osProperty.toLowerCase().startsWith("mac")){
            return mac;
        }
        return linux;
    }

    @Data
    public static class FileUse{

        private String path;

        private String avatar;
    }
}

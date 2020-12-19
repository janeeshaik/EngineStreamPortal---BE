package com.ste.enginestreamportal.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Component
public class FileUploadProperties {
    private String uploadDir;
    private String homePageContentUpload;


	public String getHomePageContentUpload() {
		return homePageContentUpload;
	}

	public void setHomePageContentUpload(String homePageContentUpload) {
		this.homePageContentUpload = homePageContentUpload;
	}


	public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
    
}
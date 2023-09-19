package com.snow.dcl.service;

import com.snow.dcl.model.SysFile;
import org.springframework.web.multipart.MultipartFile;

public interface SysFileService {
    SysFile create(String name, MultipartFile file);

    SysFile getById();

    void analyze(String path);
}

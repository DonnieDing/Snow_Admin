package com.snow.dcl.service;

import com.snow.dcl.model.SysFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface SysFileService {
    SysFile create(String name, MultipartFile file);

    SysFile getById();

    void analyze(File file);

    String images(MultipartFile image);
}

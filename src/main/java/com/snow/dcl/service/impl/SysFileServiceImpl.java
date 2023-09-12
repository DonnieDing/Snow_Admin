package com.snow.dcl.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.snow.dcl.config.FileConfig;
import com.snow.dcl.dao.SysFileRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysFile;
import com.snow.dcl.service.SysFileService;
import com.snow.dcl.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @ClassName FileServiceImpl
 * (功能描述)
 * 文件Service实现
 * @Author Dcl_Snow
 * @Create 2023/8/21 11:06
 * @Version 1.0.0
 */
@Service
public class SysFileServiceImpl implements SysFileService {

    @Resource
    private SysFileRepository sysFileRepository;

    @Resource
    private FileConfig fileConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFile create(String name, MultipartFile multipartFile) {
        FileUtils.checkSize(fileConfig.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtils.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtils.getFileType(suffix);
        File file = FileUtils.upload(multipartFile, fileConfig.getOsFileUse().getPath() + type + File.separator);

        if (ObjectUtil.isNull(file)) {
            throw new CustomException("文件上传失败");
        }
        try {
            name = StrUtil.isBlank(name) ? FileUtils.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            SysFile sysFile = new SysFile(
                    file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtils.getSize(multipartFile.getSize())
            );
            return sysFileRepository.save(sysFile);
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
    }

    @Override
    public SysFile getById() {
        SysFile sysFile = sysFileRepository.findById(2L).get();
        return sysFile;
    }
}

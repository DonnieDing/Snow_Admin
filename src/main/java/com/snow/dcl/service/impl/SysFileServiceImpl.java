package com.snow.dcl.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.snow.dcl.config.FileConfig;
import com.snow.dcl.dao.PoetryAuthorRepository;
import com.snow.dcl.dao.PoetryCategoryRepository;
import com.snow.dcl.dao.PoetryContentRepository;
import com.snow.dcl.dao.SysFileRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.*;
import com.snow.dcl.model.dto.PoetryDto;
import com.snow.dcl.service.SysFileService;
import com.snow.dcl.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

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

    @Resource
    private PoetryAuthorRepository poetryAuthorRepository;

    @Resource
    private PoetryCategoryRepository poetryCategoryRepository;

    @Resource
    private PoetryContentRepository poetryContentRepository;

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

    @Override
    public void analyze(String path) {
        PoetryAuthor poetryAuthor = new PoetryAuthor();
        poetryAuthor.setName("曹操");
        poetryAuthor.setIntroduce("曹操(155年-220年)，字孟德，一名吉利，小字阿瞒，一说本姓夏侯，沛国谯县（今安徽省亳州市）人。中国古代杰出的政治家、军事家、文学家、书法家，东汉末年权臣，亦是曹魏政权的奠基者。");
        PoetryAuthor author = poetryAuthorRepository.save(poetryAuthor);

        PoetryCategory poetryCategory = new PoetryCategory();
        poetryCategory.setTitle("三国");
        PoetryCategory category = poetryCategoryRepository.save(poetryCategory);


        String txtFileContent = FileUtils.getTxtFileContentUtf8(path);
        List<PoetryDto> poetryDtoList = JSON.parseArray(txtFileContent, PoetryDto.class);
        for (PoetryDto poetryDto : poetryDtoList) {
            PoetryContent poetryContent = new PoetryContent();
            poetryContent.setAuthorId(author.getId());
            poetryContent.setCategoryId(category.getId());
            poetryContent.setTitle(poetryDto.getTitle());
            poetryContent.setContent(poetryDto.getParagraphs());
            poetryContentRepository.save(poetryContent);
        }
    }
}

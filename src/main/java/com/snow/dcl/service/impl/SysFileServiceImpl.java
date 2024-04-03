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
import com.snow.dcl.model.PoetryAuthor;
import com.snow.dcl.model.PoetryCategory;
import com.snow.dcl.model.PoetryContent;
import com.snow.dcl.model.SysFile;
import com.snow.dcl.model.dto.poetry.ChuCiDto;
import com.snow.dcl.service.SysFileService;
import com.snow.dcl.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName SysFileServiceImpl
 * (功能描述)
 * 文件Service实现
 * @Author Dcl_Snow
 * @Create 2023/8/21 11:06
 * @Version 1.0.0
 */
@Slf4j
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
    @Transactional(rollbackFor = Throwable.class)
    public void analyze(String path) {
        PoetryAuthor poetryAuthor = new PoetryAuthor();
        poetryAuthor.setName("屈原");
        poetryAuthor.setIntroduce("屈原（约公元前340年-公元前278年），芈姓（一作嬭姓），屈氏，名平，字原，又自云名正则，字灵均，出生于楚国丹阳秭归（今湖北宜昌），战国时期楚国诗人、政治家。");
        // poetryAuthor.setName("曹操");
        // poetryAuthor.setIntroduce("曹操（155年-220年3月15日），字孟德，一名吉利，小字阿瞒，一说本姓夏侯，沛国谯县（今安徽省亳州市）人。中国古代杰出的政治家、军事家、文学家、书法家，东汉末年权臣，亦是曹魏政权的奠基者。");
        //
        PoetryAuthor author = poetryAuthorRepository.save(poetryAuthor);
        PoetryCategory poetryCategory = new PoetryCategory();
        poetryCategory.setTitle("楚辞");
        PoetryCategory category = poetryCategoryRepository.save(poetryCategory);


        String txtFileContent = FileUtils.getTxtFileContentUtf8(path);
        //曹操诗集
        // List<CaocaoDto> poetryDtoList = JSON.parseArray(txtFileContent, CaocaoDto.class);
        // for (CaocaoDto caocaoDto : poetryDtoList) {
        //     PoetryContent poetryContent = new PoetryContent();
        //     poetryContent.setAuthorId(author.getId());
        //     poetryContent.setCategoryId(category.getId());
        //     poetryContent.setTitle(caocaoDto.getTitle());
        //     String[] paragraphs = caocaoDto.getParagraphs();
        //     String result = "";
        //     for (String paragraph : paragraphs) {
        //         String s = paragraph + "\n";
        //         result += s;
        //     }
        //     poetryContent.setContent(result);
        //楚辞
        List<ChuCiDto> chuCiDtoList = JSON.parseArray(txtFileContent, ChuCiDto.class);
        for (ChuCiDto chuCiDto : chuCiDtoList) {
            PoetryContent poetryContent = new PoetryContent();
            poetryContent.setAuthorId(author.getId());
            poetryContent.setCategoryId(category.getId());
            poetryContent.setTitle(chuCiDto.getSection() + "-" + chuCiDto.getTitle());
            String[] contents = chuCiDto.getContent();
            String result = "";
            for (String content : contents) {
                String s = content + "。";
                result += s;
            }
            poetryContent.setContent(result);
            poetryContentRepository.save(poetryContent);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String images(MultipartFile image) {
        String filePath = fileConfig.getOsFileUse().getImages();
        //服务器中文件不存在，就创建配置文件中的文件夹
        File[] files = new File(filePath).listFiles();
        if (files == null) {
            new File(filePath).mkdirs();
        }
        try {
            FileUtils.checkSize(fileConfig.getMaxSize(), image.getSize());
            String fileName = image.getOriginalFilename();
            File file = new File(filePath, fileName);
            String imagePath = file.getPath();
            InputStream is = image.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
            }
            is.close();
            fos.close();
            log.info("图片上传成功", fileName);
            return imagePath;
        } catch (Exception e) {
            log.error("上传图片异常：{}", e);
            throw new CustomException("上传图片异常");
        }
    }
}

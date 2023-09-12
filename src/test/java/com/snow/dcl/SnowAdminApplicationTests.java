package com.snow.dcl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONObject;
import com.snow.dcl.config.FileConfig;
import com.snow.dcl.dao.SysPermissionRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysLog;
import com.snow.dcl.model.SysPermission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class SnowAdminApplicationTests {

    @Resource
    SysPermissionRepository sysPermissionRepository;

    @Resource
    FileConfig fileConfig;

    @Test
    void contextLoadsSystemPath() {

        FileConfig.FileUse path = fileConfig.getOsFileUse();
        System.out.println(path);

    }
    @Test
    void contextLoadsFile() throws IOException {

        String dateStr1 = "2023-08-24 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);
        Date beginOfDay = DateUtil.beginOfDay(date1);

        String dateStr = "2023-08-25 22:33:23";
        Date date = DateUtil.parse(dateStr);
        // Date date = new Date();

        long between = DateUtil.between(date, date1, DateUnit.DAY);
        System.out.println(between);

        System.out.println(between >= 1);

        // String filePath = "C:\\snowAdmin\\file\\文档\\1.txt";
        // Object o = System.getProperties().get("sun.jnu.encoding");
        // System.out.println(o);
        // FileReader fileReader = new FileReader(filePath, Charset.forName(System.getProperties().get("sun.jnu.encoding").toString()));
        // String filePath = "C:\\snowAdmin\\file\\文档\\20230210-20230823034639342.txt";
        // FileReader fileReader = new FileReader(filePath, Charset.forName(System.getProperties().get("sun.jnu.encoding").toString()));
        // System.out.println(fileReader.readString());

        // String content = readTxt(filePath);
        // System.out.println(content);
        // //从路径获取文件
        // File file = FileUtils.file(filePath);
        // System.out.println(file);


        // String charsetStr = System.getProperties().get("sun.jnu.encoding").toString();
        // String readString = FileUtils.readString(filePath, Charset.forName(charsetStr));
        // String convertCharset = Convert.convertCharset(readString, charsetStr, CharsetUtil.UTF_8.name());
        // System.out.println(convertCharset);

    }

    public static String readTxt(String path) {
        StringBuilder content = new StringBuilder("");

        try {
            String fileCharsetName = getFileCharsetName(path);

            System.out.println("文件的编码格式为："+fileCharsetName);

            InputStream is = new FileInputStream(path);

            InputStreamReader isr = new InputStreamReader(is, fileCharsetName);

            BufferedReader br = new BufferedReader(isr);

            String str = "";

            boolean isFirst = true;

            while (null != (str = br.readLine())) {
                if (!isFirst)

                    content.append(System.lineSeparator());

//System.getProperty("line.separator");

                else

                    isFirst = false;

                content.append(str);

            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("读取文件:" + path + "失败!");

        }

        return content.toString();

    }

    public static String getFileCharsetName(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);

        byte[] head = new byte[3];

        inputStream.read(head);

        String charsetName = "GBK";//或GB2312，即ANSI

        if (head[0] == -1 && head[1] == -2 ) //0xFFFE

            charsetName = "UTF-16";

        else if (head[0] == -2 && head[1] == -1 ) //0xFEFF

            charsetName = "Unicode";//包含两种编码格式：UCS2-Big-Endian和UCS2-Little-Endian

        else if(head[0]==-27 && head[1]==-101 && head[2] ==-98)

            charsetName = "UTF-8"; //UTF-8(不含BOM)

        else if(head[0]==-17 && head[1]==-69 && head[2] ==-65)

            charsetName = "UTF-8"; //UTF-8-BOM

        inputStream.close();

//System.out.println(code);

        return charsetName;

    }

    @Test
    void contextLoads() {
        CustomException exception = new CustomException("自定义测试异常");

        String stackTrace = ExceptionUtil.stacktraceToString(exception, -1);
        System.out.println(stackTrace);

        String string = ExceptionUtil.getMessage(exception);
        System.out.println(string);

        SysLog sysLog = new SysLog();
        sysLog.setExceptionDetail(ExceptionUtil.getMessage(exception));
        System.out.println(sysLog);
    }

    @Test
    void contextLoads1() {
        List<SysPermission> all = sysPermissionRepository.findAll();
        // List<SysPermission> sysPermissions = new ArrayList<>();
        // for (SysPermission sysPermission : all) {
        //     if (sysPermission.getStatus().equals(1)){
        //         sysPermissions.add(sysPermission);
        //     }
        // }
        // Set<SysPermission> collect = sysPermissions.stream().collect(Collectors.toSet());
        // collect.stream().forEach(sysPermission -> System.out.println(sysPermission));
        // Set<SysPermission> collect = all.stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toList()).stream().collect(Collectors.toSet());
        Set<SysPermission> collect = all.stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());
        collect.stream().forEach(sysPermission -> System.out.println(sysPermission));
    }

}

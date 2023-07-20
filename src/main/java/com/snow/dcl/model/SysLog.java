package com.snow.dcl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * (功能描述)
 * 操作日志实体
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 10:42
 * @version: 1.0.0
 */
@Entity
@Data
@Table(name = "sys_log")
@NoArgsConstructor
@AllArgsConstructor
public class SysLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 操作用户 */
    private String username;

    /** 描述 */
    private String description;

    /** 方法名 */
    private String method;

    /** 参数 */
    private String params;

    /** 日志类型 */
    private String logType;

    /** 请求ip */
    private String requestIp;

    /** 地址 */
    private String address;

    /** 浏览器  */
    private String browser;

    /** 请求耗时 */
    private Long time;

    /** 异常详细  */
    private String exceptionDetail;

    /** 创建日期 */
    @CreationTimestamp
    private Date createTime;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}

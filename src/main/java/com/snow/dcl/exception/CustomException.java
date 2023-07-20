/**
 * Copyright (C), 2018-2020, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0.0
 * @ClassName CustomException
 * (功能描述)
 * 自定义异常处理
 * @Author Dcl_Snow
 * @Create 2021/8/24 14:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private Integer code;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(HttpStatus status, String message) {
        this.message = message;
        this.code = status.value();
    }
}

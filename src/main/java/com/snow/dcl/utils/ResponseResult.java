/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ResponseResult
 * (功能描述)
 * 数据返回对象
 * @Author Dcl_Snow
 * @Create 2021/8/16 10:42
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {
    private String code;

    private String message;

    private T data;

    public static <T> ResponseResult<T> success() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(HttpCodeEnum.SUCCESS.code);
        responseResult.setMessage(HttpCodeEnum.SUCCESS.message);
        return responseResult;
    }

    public static <T> ResponseResult<T> fail() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(HttpCodeEnum.FAIL.code);
        responseResult.setMessage(HttpCodeEnum.FAIL.message);
        return responseResult;
    }

    public ResponseResult code(String code) {
        this.setCode(code);
        return this;
    }

    public ResponseResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResponseResult data(T data) {
        this.setData(data);
        return this;
    }

}

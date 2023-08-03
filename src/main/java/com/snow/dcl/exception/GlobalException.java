/**
 * Copyright (C), 2018-2020, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.exception;

import com.snow.dcl.utils.HttpCodeEnum;
import com.snow.dcl.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * @ClassName GlobalException
 * (功能描述)
 * 全局异常处理
 * @Author Dcl_Snow
 * @Create 2021/8/24 14:02
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * 默认全局异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult error(Exception e) {
        log.error("全局异常信息:{}", e.getMessage());
        return ResponseResult.fail().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseResult accessDeniedException(Exception e) {
        ResponseResult responseResult = null;
        if (e instanceof AccessDeniedException) {
            responseResult = ResponseResult.fail().code(HttpCodeEnum.FORBIDDEN.getCode()).message(HttpCodeEnum.FORBIDDEN.getMessage());
        }
        return responseResult;
    }

    /**
     * 参数校验的异常处理
     */
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseResult validatedException(Exception e) {
        ResponseResult responseResult = null;

        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            responseResult = ResponseResult.fail().code(HttpCodeEnum.PARAMETER_VALIDATED_FAIL.getCode()).message(exception.getAllErrors().stream().
                    map(ObjectError::getDefaultMessage).
                    collect(Collectors.joining(";")));
        } else if (e instanceof ConstraintViolationException) {
            responseResult = ResponseResult.fail().code(HttpCodeEnum.PARAMETER_VALIDATED_FAIL.getCode()).message(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            responseResult = ResponseResult.fail().code(HttpCodeEnum.PARAMETER_VALIDATED_FAIL.getCode()).message(e.getMessage());
        }
        return responseResult;
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseResult deleteException(Exception e) {
        ResponseResult responseResult = null;
        if (e instanceof EmptyResultDataAccessException) {
            responseResult = ResponseResult.fail().code(HttpCodeEnum.DELETE_FAIL.getCode()).message(HttpCodeEnum.DELETE_FAIL.getMessage());
        }
        return responseResult;
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseResult constraintException(Exception e) {
        ResponseResult responseResult = null;
        if (e instanceof DataIntegrityViolationException) {
            responseResult = ResponseResult.fail().code(HttpCodeEnum.DELETE_FAIL.getCode()).message(HttpCodeEnum.CONSTRAINT_VIOLATION_FAIL.getMessage());
        }
        return responseResult;
    }

}

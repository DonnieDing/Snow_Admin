package com.snow.dcl.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StringEnumValidator
 * (功能描述)
 * StringEnumValidator
 * @Author Dcl_Snow
 * @Create 2021/8/24 23:50
 * @Version 1.0.0
 */
public class StringEnumValidator implements ConstraintValidator<StringEnum, String> {
    private List<String> stringEnumList;

    @Override
    public void initialize(StringEnum constraintAnnotation) {
        stringEnumList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }
        return stringEnumList.contains(value);
    }
}

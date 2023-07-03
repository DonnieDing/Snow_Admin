package com.snow.dcl.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @ClassName StringEnum
 * (功能描述)
 * StringEnum
 * @Author Dcl_Snow
 * @Create 2021/8/24 23:50
 * @Version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RUNTIME)
@Repeatable(StringEnum.List.class)
@Documented
@Constraint(validatedBy = StringEnumValidator.class)
public @interface StringEnum {
    String message() default "value not in enum values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value();

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        StringEnum[] value();
    }
}

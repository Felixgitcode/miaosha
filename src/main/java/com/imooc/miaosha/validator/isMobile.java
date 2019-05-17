package com.imooc.miaosha.validator;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * @author Felix
 * @date 2019/5/16 8:33
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class })
public @interface isMobile {

    boolean required() default true;

    java.lang.String message() default "手机号码格式错误";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};
}

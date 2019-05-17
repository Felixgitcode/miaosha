package com.imooc.miaosha.validator;

import com.imooc.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Felix
 * @date 2019/5/16 8:40
 */
public class IsMobileValidator implements ConstraintValidator<isMobile,String> {
    private boolean required = false;

    @Override
    public void initialize(isMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}

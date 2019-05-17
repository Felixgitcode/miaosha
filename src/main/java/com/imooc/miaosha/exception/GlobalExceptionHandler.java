package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Felix
 * @date 2019/5/16 9:02
 */

@ControllerAdvice
@ResponseBody
//全局异常处理拦截器,这里相当于一个controller
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)//拦截所有异常
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return Result.error(ex.getCm());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}

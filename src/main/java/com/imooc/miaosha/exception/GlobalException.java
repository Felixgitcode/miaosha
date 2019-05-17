package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;

/**
 * @author Felix
 * @date 2019/5/16 9:02
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}

package com.github.hitzaki.bloom.base.exception;
/**
 * 基础工具异常
 * @author hitzaki
 */
public class BaseUtilsException extends RuntimeException {

    private String errMessage;

    public BaseUtilsException() {
        super();
    }

    public BaseUtilsException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage(){
        return errMessage;
    }

    public static void cast(String errMessage){
        throw new BaseUtilsException(errMessage);
    }
}
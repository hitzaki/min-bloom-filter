package com.github.hitzaki.bloom.validation;

import com.github.hitzaki.bloom.base.exception.BaseUtilsException;

import java.util.Collection;

/**
 * 校验工具
 * @author hitzaki
 */
public class Validations {
    public static boolean isEmpty(Collection<?> collection){
        if (collection == null || collection.isEmpty()){
            return true;
        }
        return false;
    }

    public static void checkEmpty(Collection<?> collection){
        if (collection == null || collection.isEmpty()){
            throw new BaseUtilsException("运行失败, 集合为空");
        }
    }
}

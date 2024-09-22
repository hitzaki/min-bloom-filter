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

    public static Collection<?> checkEmpty(Collection<?> collection){
        if (collection == null || collection.isEmpty()){
            throw new BaseUtilsException("Validations: 集合为空");
        }
        return collection;
    }

    public static <T> T checkNotNull(T o){
        if (o == null){
            throw new BaseUtilsException("Validations: 对象为null");
        }
        return o;
    }

    public static void checkState(boolean bool, String position){
        if (bool){
            throw new BaseUtilsException("Validations.State: " + position);
        }
    }

}

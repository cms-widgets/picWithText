package com.huotu.hotcms.widget.common;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 字符串处理类
 * Created by slt on 2016/8/16.
 */
public class ValidHelper {
    /**
     *
     * 检查对象数组的每一个对象是否为null或为"",只要有对象符合就返回true,否则返回false
     * @param array 对象数组
     * @return      对象是否为空
     */
    public static boolean isAnyOneEmpty(Object[] array){
        if(ObjectUtils.isEmpty(array)){
            return true;
        }
        for(int i=0,size=array.length;i<size;i++){
            if(StringUtils.isEmpty(array[i])){
                return true;
            }
        }
        return false;
    }
}

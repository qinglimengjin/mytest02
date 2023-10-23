package com.liu.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class WebUtils {
    /**
     * @param bean
     */
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            System.out.println("注入之前：" + bean);
/**
 * 把所有请求的参数都注入到user 对象中
 */
            BeanUtils.populate(bean, value);
            System.out.println("注入之后：" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static int parseInt(String strInt, int defaultValue) {
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
            System.out.println("");
        }
        return defaultValue;
    }
    //删除原图片
    public static void deleteFile(String path){
        File dFile = new File("D:" + path);
        dFile.delete();
    }
}
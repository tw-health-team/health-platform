package com.theus.health.core.util;

/**
 * 字符串处理工具类
 * @author tangwei
 * @date 2020-03-08 13:52
 */
public class HStrUtil {

    /**
     * 删除字符串末尾的双0
     * @param str 字符串（如131000-廊坊市）
     * @return 字符串（如1310-廊坊市）
     */
    public static String removeSuffixDoubleZero(String str){
        return str.replaceAll("(00)+$", "");
    }
}

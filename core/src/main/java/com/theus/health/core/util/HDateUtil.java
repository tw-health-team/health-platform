package com.theus.health.core.util;

import java.util.Date;

/**
 * 日期工具类
 * @author tangwei
 * @date 2020-04-10 16:21
 */
public class HDateUtil {
    /**
     * util.Date转为sql.Date
     * @param date util.Date
     * @return sql.Date
     */
    public static java.sql.Date toSqlDate(Date date){
        return new java.sql.Date(date.getTime());
    }
}

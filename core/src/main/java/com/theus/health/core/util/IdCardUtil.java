package com.theus.health.core.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * 身份证号工具类
 *
 * @author tangwei
 * @date 2018-08-29 10:46
 */
public class IdCardUtil {

    /**
     * 旧身份证号长度
     */
    private static final int CHINA_ID_OLD_LENGTH = 15;

    /**
     * 新身份证号长度
     */
    private static final int CHINA_ID_NEW_LENGTH = 18;

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyy - MM - dd)
     */
    public static Date getBirthByIdCard(String idCard) {
        Date birthday = null;
        if (idCard != null) {
            String birth = "";
            if (idCard.length() == CHINA_ID_OLD_LENGTH) {
                birth = "19" + idCard.substring(6, 8) + "-"
                        + idCard.substring(8, 10) + "-"
                        + idCard.substring(10, 12);
            } else if (idCard.length() == CHINA_ID_NEW_LENGTH) {
                birth = idCard.substring(6, 10) + "-"
                        + idCard.substring(10, 12) + "-"
                        + idCard.substring(12, 14);
            }
            if (StrUtil.isNotBlank(birth)) {
                birthday = DateUtil.parse(birth, "yyyy-MM-dd");
            }
        }
        return birthday;
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(1 - 男 ， 2 - 女)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "";

        if (idCard != null) {
            if (idCard.length() == CHINA_ID_OLD_LENGTH) {
                sGender = idCard.substring(idCard.length() - 3, idCard.length());
            } else if (idCard.length() == CHINA_ID_NEW_LENGTH) {
                sGender = idCard.substring(idCard.length() - 4, idCard.length() - 1);
            }

            if (!sGender.isEmpty() && Integer.parseInt(sGender) % 2 != 0) {
                //男
                sGender = "1";
            } else {
                //女
                sGender = "2";
            }
        }
        return sGender;
    }
}

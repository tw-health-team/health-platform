package com.theus.health.base.common.constants;

/**
 * @author tangwei
 * @date 2019-09-22 18:30
 */
public interface SysConstants {

    /**
     * 超级管理员用户名
     */
    String SUPER_ADMIN = "admin";

    /**
     * 超级管理员角色ID
     */
    String SUPER_ROLE_ID = "999999999";

    /**
     * 超级管理员角色名称
     */
    String SUPER_ROLE_NAME = "超级管理员";

    /**
     * 顶级机构代码
     */
    String TOP_DEPT_CODE = "0";

    /**
     * 通用顶级代码
     */
    String TOP_COMMON_CODE = "0";

    /**
     * 真、假整数常量
     */
    interface TrueFalseInt {
        int TRUE = 1;

        int FALSE = 0;
    }

    /**
     * 字符串起始索引
     */
    int STRING_START_INDEX = 0;

    /**
     * 行政区划常量
     */
    class Area {

        /**
         * 区划顶级名称
         */
        public static final String CHINA = "中国";

        /**
         * 区划名称分隔符
         */
        public static final String NAME_SPLIT_CHAR = "/";

        /**
         * 区划编码分隔符
         */
        public static final String CODE_SPLIT_CHAR = ",";

        /**
         * 省（自治区、直辖市）编码长度
         */
        public static final int PROVINCE_CODE_LEN = 2;

        /**
         * 市（地区）编码长度
         */
        public static final int CITY_CODE_LEN = 4;

        /**
         * 县（区）编码长度
         */
        public static final int DISTRICT_CODE_LEN = 6;

        /**
         * 乡（镇、街道）编码长度
         */
        public static final int TOWN_CODE_LEN = 9;

        /**
         * 村（居委）编码长度
         */
        public static final int COMMITTEE_CODE_LEN = 12;

        /**
         * 存储居委json文件的文件夹名
         */
        public static final String COMMITTEE_FILE_DIRECTORY = "committee";

        /**
         * 存储街道json文件的文件夹名
         */
        public static final String TOWN_FILE_DIRECTORY = "town";

        /**
         * 存储省市区json文件的文件夹名
         */
        public static final String FILE_NAME = "data";

        /**
         * 存储区划的文件扩展名
         */
        public static final String FILE_EXTENSION = "json";
    }
}
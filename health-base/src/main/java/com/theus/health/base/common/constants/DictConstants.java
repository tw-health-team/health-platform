package com.theus.health.base.common.constants;

/**
 * 字典常量（对应数据库中字典项）
 *
 * @author tangwei
 * @date 2020-02-27 21:49
 */
public class DictConstants {

    /**
     * 行政区划级别（机构级别）
     *
     * @author tangwei
     * @date 2020-02-27
     */
    public enum AreaLevel {

        /**
         * 省（自治区、直辖市）级
         */
        PROVINCE("1", "省市级"),
        /**
         * 市（地区）级
         */
        CITY("2", "地市级"),
        /**
         * 县（区）级
         */
        DISTRICT("3", "区县级"),
        /**
         * 乡（镇、街道）级
         */
        TOWN("4", "街道(乡镇)级"),
        /**
         * 村（居委）级
         */
        COMMITTEE("5", "居委(村)级");

        AreaLevel(String value, String name) {
            this.value = value;
            this.name = name;
        }

        private final String value;
        private final String name;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 数据删除标志
     */
    public enum DelFlag {
        /**
         * 正常
         */
        NORMAL(0, "正常"),
        /**
         * 已删除
         */
        DELETED(1, "删除");

        DelFlag(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        private final Integer value;
        private final String name;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 是否标志
     */
    public enum YeaOrNot {
        /**
         * 否
         */
        NO("0", "否"),
        /**
         * 是
         */
        YES("1", "是");

        YeaOrNot(String value, String name) {
            this.value = value;
            this.name = name;
        }

        private final String value;
        private final String name;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }



    /**
     * 解锁状态
     */
    public enum UnLockStatus {
        /**
         * 锁定
         */
        NO(0, "锁定"),
        /**
         * 解锁
         */
        YES(1, "解锁");

        UnLockStatus(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        private final Integer value;
        private final String name;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}

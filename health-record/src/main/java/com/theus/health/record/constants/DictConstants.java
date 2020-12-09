package com.theus.health.record.constants;

/**
 * 档案字典常量类（对应数据库中字典项）
 *
 * @author tangwei
 * @date 2020-04-09 17:13
 */
public class DictConstants {
    /**
     * 证件类型
     *
     * @author tangwei
     * @date 2020-04-09
     */
    public enum CardType {

        /**
         * 身份证
         */
        ID_CARD("1", "身份证"),
        /**
         * 军人证
         */
        MILITARY_CARD("2", "军人证"),
        /**
         * 护照
         */
        PASSPORT("3", "护照"),
        /**
         * 回乡证(港澳同胞回乡证)
         */
        HOME_RETURN("4", "回乡证"),
        /**
         * 驾驶证
         */
        DRIVING_LICENCE("5", "驾驶证"),
        /**
         * 台胞证(台湾居民来往大陆通行证)
         */
        TAIWAN_COMPATRIOT("6", "台胞证"),
        /**
         * 其它
         */
        OTHER("7", "其它"),
        ;

        CardType(String value, String name) {
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
     * 管理状态
     *
     * @author tangwei
     * @date 2020-04-09
     */
    public enum ControlState {

        /**
         * 继续随访
         */
        CONTINUE_FOLLOWUP("1", "继续随访"),
        /**
         * 失访
         */
        LOSE_FOLLOWUP("2", "失访"),
        ;

        ControlState(String value, String name) {
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
}

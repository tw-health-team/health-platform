package com.theus.health.base.common.constants;

/**
 * @author tangwei
 * @date 2019-09-26 20:11
 */
public class ResourceConstants {

    /**
     * url分隔符
     */
    public static final String SPLIT_CHAR_URL = "/";
    /**
     * permission分隔符
     */
    public static final String SPLIT_CHAR_PER = ":";

    /**
     * 资源类型
     */
    public enum Type {
        /**
         * 目录
         */
        CATALOG,

        /**
         * 菜单
         */
        MENU,

        /**
         * 按钮
         */
        BUTTON
    }

    /**
     * 结果类型
     */
    public enum ResultType {
        /**
         * 包含按钮
         */
        WITH_BUTTON,
        /**
         * 不包含按钮
         */
        NO_BUTTON
    }

    /**
     * 按钮类型标识
     */
    public enum ButtonType {
        /**
         * 添加
         */
        ADD(0, "add", "添加"),

        /**
         * 更新
         */
        UPDATE(1, "update", "更新"),

        /**
         * 删除
         */
        REMOVE(2, "remove", "删除"),

        /**
         * 查询
         */
        QUERY(3, "query", "查询"),
        ;

        ButtonType(Integer value, String name, String text) {
            this.value = value;
            this.name = name;
            this.text = text;
        }

        private final Integer value;
        private final String name;
        private final String text;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public String getText() {
            return text;
        }
    }
}

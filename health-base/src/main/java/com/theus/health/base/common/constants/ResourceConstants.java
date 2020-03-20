package com.theus.health.base.common.constants;

/**
 * @author tangwei
 * @date 2019-09-26 20:11
 */
public class ResourceConstants {

    /**
     * 资源类型
     */
    public enum Type{
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
    public enum ResultType{
        /**
         * 包含按钮
         */
        WITH_BUTTON,
        /**
         * 不包含按钮
         */
        NO_BUTTON
    }

}

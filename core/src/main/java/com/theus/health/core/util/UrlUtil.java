package com.theus.health.core.util;

import cn.hutool.core.net.url.UrlBuilder;

import java.util.Map;

/**
 * @author tangwei
 * @date 2020-11-27 11:22
 */
public class UrlUtil {

    public static String createGetUrl(String url, Map<String, String> params) {
        String result = UrlBuilder.create()
                .setScheme("https")
                .setHost("www.hutool.cn")
                .addPath("/aaa").addPath("bbb")
                .addQuery("ie", "UTF-8")
                .addQuery("wd", "test")
                .build();
        return result;
    }
}

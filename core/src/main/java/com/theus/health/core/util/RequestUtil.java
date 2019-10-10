package com.theus.health.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tangwei
 * @date 2019-07-28 21:51
 */
public class RequestUtil {

    /**
     * 获取客户端IP
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = "";
        if (request != null) {
            ip = request.getHeader("X-Real-IP");
            if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if ("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)) {
                ip = "127.0.0.1";
            }
        }
        return ip;
    }

    /**
     * 判断请求是否是Ajax
     *
     * @param request 请求
     * @return bool
     */
    public static boolean isAjax(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }
}

package org.example.demo.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：IP地址获取工具类
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:13
 */
public class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * 获取客户端真实IP
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        // 依次从请求头中获取IP（兼容Nginx/反向代理）
        String ip = request.getHeader("x-forwarded-for");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 如果以上都获取不到，直接获取请求的远程IP
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
            // 处理本地回环地址
            if (LOCALHOST_IPV6.equals(ip)) {
                ip = LOCALHOST_IP;
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (StringUtils.hasLength(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return LOCALHOST_IP.equals(ip) ? ip : (StringUtils.hasLength(ip) ? ip : UNKNOWN);
    }

    /**
     * 判断IP是否无效
     */
    private static boolean isInvalidIp(String ip) {
        return !StringUtils.hasLength(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }
}

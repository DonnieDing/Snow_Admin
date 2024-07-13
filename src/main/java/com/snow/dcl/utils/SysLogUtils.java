package com.snow.dcl.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName LogInfoUtils
 * (功能描述)
 * 操作日志工具类
 * @Author Dcl_Snow
 * @Create 2023/7/19 15:38
 * @Version 1.0.0
 */
@Slf4j
public class SysLogUtils {

    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    private static Ip2regionSearcher IP_SEARCHER = (Ip2regionSearcher) SpringBeanUtil.getBean("ip2regionSearcher");

    /**
     * 获取浏览器名字+版本
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getBrowserInfo(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        String browserName = userAgent.getBrowser().toString();
        String browserVersion = userAgent.getVersion();
        return browserName + "-" + browserVersion;
    }

    /**
     * 获取操作系统名称
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getOsName(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getOs().toString();
    }


    /**
     * 得到ip addr
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getIp(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return "unknown";
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }

            if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }

            if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }

            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
            String localhost = "127.0.0.1";
            if (localhost.equals(ip)) {
                // 获取本机真正的ip地址
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                }
            }
            return ip;
        }
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getCityInfo(String ip) {
        if (true) {
            return getLocalCityInfo(ip);
        } else {
            return getHttpCityInfo(ip);
        }
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getHttpCityInfo(String ip) {
        String api = String.format(IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getLocalCityInfo(String ip) {
        IpInfo ipInfo = IP_SEARCHER.memorySearch(ip);
        if (ipInfo != null) {
            return ipInfo.getAddress();
        }
        return null;

    }

}

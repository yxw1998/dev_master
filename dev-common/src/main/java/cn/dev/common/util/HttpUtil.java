package cn.dev.common.util;

import cn.dev.common.constant.Constants;
import cn.dev.common.exception.ServiceException;
import cn.dev.common.util.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author YangXw
 * @date 2023/01/27 21:45
 * @description HTTP工具类，基于RestTemplate
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final RestTemplate restTemplate = SpringUtil.getBean("restTemplate");

    /**
     * 获取默认Headers 没有默认Content-Type
     *
     * @return
     */
    public static HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setConnection("Keep-Alive");
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return headers;
    }

    /**
     * 获取携带 Content-Type:application/json 的headers
     *
     * @return
     */
    private static HttpHeaders getJsonHeaders() {
        HttpHeaders headers = getDefaultHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 向指定URL发送GET请求
     * @param url 发送请求的URL
     * @return
     */
    public static String get(String url) {
        return get(url, Constants.EMP_STR);
    }

    /**
     * 向指定URL发送GET请求
     * @param url 发送请求的URL
     * @param param 请求参数 参数应为 k1=v1&k2=v2 的格式
     * @return
     */
    public static String get(String url, String param) {
        return get(url, param, getJsonHeaders());
    }

    public static String get(String url, String param, HttpHeaders httpHeaders) {
        if (StringUtils.hasText(url)) {
            try {
                url = StringUtils.hasText(param) ? url + "?" + param : url;
                return restTemplate
                        .exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class)
                        .getBody();
            }catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException("HTTP连接异常, 未知的地址");
        }
    }
}

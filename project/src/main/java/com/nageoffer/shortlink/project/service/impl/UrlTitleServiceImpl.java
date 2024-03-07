package com.nageoffer.shortlink.project.service.impl;

import com.nageoffer.shortlink.project.service.UrlTitleService;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: URL标题接口实现层
 **/
@Service
@RequiredArgsConstructor
public class UrlTitleServiceImpl implements UrlTitleService {
    private final RestTemplate restTemplate;

    @Override
    public String getTitleByUrl(String url) {
        // 发送HTTP GET请求获取网页内容
        String htmlContent = restTemplate.getForObject(url, String.class);
        if (StringUtil.isNotBlank(htmlContent)) {
            // 使用Jsoup解析HTML内容
            Document document = Jsoup.parse(htmlContent);
            // 提取网页标题
            return document.title();
        }
        return "Error while fetching title";
    }
}

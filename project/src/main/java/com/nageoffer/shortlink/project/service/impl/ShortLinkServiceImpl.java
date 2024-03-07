package com.nageoffer.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.project.common.convention.exception.ClientException;
import com.nageoffer.shortlink.project.common.convention.exception.ServiceException;
import com.nageoffer.shortlink.project.common.enums.ValidDateTypeEnum;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkGotoDO;
import com.nageoffer.shortlink.project.dao.mapper.ShortLinkGotoMapper;
import com.nageoffer.shortlink.project.dao.mapper.ShortLinkMapper;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkSaveRespDTO;
import com.nageoffer.shortlink.project.service.ShortLinkService;
import com.nageoffer.shortlink.project.util.HashUtil;
import com.nageoffer.shortlink.project.util.LinkUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.print.Doc;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.project.common.constant.RedisKeyConstant.*;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接接口实现层
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {
    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;
    private final StringRedisTemplate stringRedisTemplate;
    private final ShortLinkGotoMapper shortLinkGotoMapper;
    private final RedissonClient redissonClient;
    private final RestTemplate restTemplate;

    @Override
    public ShortLinkSaveRespDTO createShortLink(ShortLinkSaveReqDTO saveReqDTO) {
        ShortLinkDO shortLinkDO = BeanUtil.toBean(saveReqDTO, ShortLinkDO.class);
        String shortLinkSuffix = generateSuffix(saveReqDTO);
        String fullShortUrl = saveReqDTO.getDomain() + "/" + shortLinkSuffix;
        shortLinkDO.setShortUri(shortLinkSuffix);
        shortLinkDO.setEnableStatus(0);
        shortLinkDO.setFullShortUrl(fullShortUrl);
        shortLinkDO.setFavicon(getFaviconUrl(saveReqDTO.getOriginUrl()));
        ShortLinkGotoDO shortLinkGotoDO = ShortLinkGotoDO.builder()
                .fullShortUrl(fullShortUrl)
                .gid(saveReqDTO.getGid())
                .build();
        try {
            baseMapper.insert(shortLinkDO);
            shortLinkGotoMapper.insert(shortLinkGotoDO);
        } catch (DuplicateKeyException ex) {
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO hasShortLink = baseMapper.selectOne(queryWrapper);
            if (hasShortLink != null) {
                log.warn("短链接：{} 重复入库", fullShortUrl);
                throw new ServiceException("短链接生成重复");
            }
        }
        stringRedisTemplate.opsForValue().set(
                String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                saveReqDTO.getOriginUrl(),
                LinkUtil.getLinkCacheValidDate(saveReqDTO.getValidDate()), TimeUnit.MILLISECONDS);
        shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return ShortLinkSaveRespDTO.builder()
                .fullShortUrl("http://" + shortLinkDO.getFullShortUrl())
                .originUrl(saveReqDTO.getOriginUrl())
                .gid(saveReqDTO.getGid())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO pageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, pageReqDTO.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(pageReqDTO, queryWrapper);
        return resultPage.convert(each -> {
            ShortLinkPageRespDTO result = BeanUtil.toBean(each, ShortLinkPageRespDTO.class);
            result.setDomain("http://" + result.getDomain());
            return result;
        });
    }

    @Override
    public List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid, count(*) as shortLinkCount")
                .in("gid", gids)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkDOList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(shortLinkDOList, ShortLinkGroupCountQueryRespDTO.class);
    }

    @Override
    public void updateShortLink(String gid, ShortLinkUpdateReqDTO updateReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getFullShortUrl, updateReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getGid, gid)
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if (hasShortLinkDO != null) {
            if (!Objects.equals(gid, updateReqDTO.getGid())) {
                LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                        .eq(ShortLinkDO::getFullShortUrl, updateReqDTO.getFullShortUrl())
                        .eq(ShortLinkDO::getGid, gid)
                        .eq(ShortLinkDO::getDelFlag, 0)
                        .eq(ShortLinkDO::getEnableStatus, 0);
                ShortLinkDO delShortLink = new ShortLinkDO();
                delShortLink.setDelFlag(1);
                baseMapper.update(delShortLink, updateWrapper);
                ShortLinkSaveReqDTO saveReqDTO = ShortLinkSaveReqDTO.builder()
                        .domain(hasShortLinkDO.getDomain())
                        .gid(updateReqDTO.getGid())
                        .originUrl(updateReqDTO.getOriginUrl())
                        .description(updateReqDTO.getDescription())
                        .validDateType(updateReqDTO.getValidDateType())
                        .validDate(updateReqDTO.getValidDate())
                        .createType(hasShortLinkDO.getCreateType())
                        .build();
                createShortLink(saveReqDTO);
            } else {
                LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                        .eq(ShortLinkDO::getFullShortUrl, updateReqDTO.getFullShortUrl())
                        .eq(ShortLinkDO::getGid, updateReqDTO.getGid())
                        .eq(ShortLinkDO::getDelFlag, 0)
                        .eq(ShortLinkDO::getEnableStatus, 0)
                        .set(Objects.equals(updateReqDTO.getValidDateType(), ValidDateTypeEnum.PERMANENT), ShortLinkDO::getValidDate, null);
                ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                        .originUrl(updateReqDTO.getOriginUrl())
                        .fullShortUrl(updateReqDTO.getFullShortUrl())
                        .gid(updateReqDTO.getGid())
                        .description(updateReqDTO.getDescription())
                        .validDateType(updateReqDTO.getValidDateType())
                        .validDate(updateReqDTO.getValidDate())
                        .build();
                baseMapper.update(shortLinkDO, updateWrapper);
            }
        } else {
            throw new ClientException("该短连接不存在!");
        }

    }

    /**
     * 短链接跳转
     *
     * @param shortUri 短链接后缀
     * @param request  请求
     * @param response 响应
     */
    @Override
    public void restoreUrl(String shortUri, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serverName = request.getServerName();
        String fullShortUrl = serverName + "/" + shortUri;
        String originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
        if (StringUtil.isNotBlank(originalLink)) {
            response.sendRedirect(originalLink);
            return;
        }
        if (!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)) {
            response.sendRedirect("/page/notfound");
            return;
        }
        String isNullGotoLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl));
        if (StringUtil.isNotBlank(isNullGotoLink)) {
            response.sendRedirect("/page/notfound");
            return;
        }
        RLock lock = redissonClient.getLock(String.format(LOCK_GOTO_SHORT_LINK_KEY, fullShortUrl));
        lock.lock();
        try {
            originalLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
            if (StringUtil.isNotBlank(originalLink)) {
                response.sendRedirect(originalLink);
                return;
            }
            LambdaQueryWrapper<ShortLinkGotoDO> linkGotoDOLambdaQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                    .eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);
            ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(linkGotoDOLambdaQueryWrapper);
            if (shortLinkGotoDO == null) {
                stringRedisTemplate.opsForValue()
                        .set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.SECONDS);
                response.sendRedirect("/page/notfound");
                //严格来说此处需要进行风控
                return;
            }
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getGid, shortLinkGotoDO.getGid())
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .eq(ShortLinkDO::getDelFlag, 0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            if (shortLinkDO != null) {
                if (shortLinkDO.getValidDate() != null && shortLinkDO.getValidDate().before(new Date())) {
                    stringRedisTemplate.opsForValue()
                            .set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.SECONDS);
                    response.sendRedirect("/page/notfound");
                    return;
                }
                stringRedisTemplate.opsForValue().set(
                        String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                        shortLinkDO.getOriginUrl(),
                        LinkUtil.getLinkCacheValidDate(shortLinkDO.getValidDate()), TimeUnit.MILLISECONDS);
                response.sendRedirect(shortLinkDO.getOriginUrl());
            }
        } finally {
            lock.unlock();
        }
    }

    private String generateSuffix(ShortLinkSaveReqDTO saveReqDTO) {
        int customGenerateCount = 0;
        String shortUri;
        while (true) {
            if (customGenerateCount > 10) {
                throw new ServiceException("短链接频繁生成，请稍后再试");
            }
            String originUrl = saveReqDTO.getOriginUrl();
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            if (!shortUriCreateCachePenetrationBloomFilter.contains(saveReqDTO.getDomain() + "/" + shortUri)) {
                break;
            }
            customGenerateCount++;
        }
        return shortUri;
    }

    @SneakyThrows
    private String getFaviconUrl(String url) {
        URL tagetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) tagetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            Document document = Jsoup.connect(url).get();
            Element faviconUrl = document.select("link[rel~=(?i)^(shortcut )?icon]").first();
            if (faviconUrl != null) {
                return faviconUrl.attr("abs:href");
            }
        }
        return null;
    }
}

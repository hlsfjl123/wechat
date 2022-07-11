package com.hls.wechat.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hls.wechat.dto.wechat.*;
import com.hls.wechat.enu.RedisPrefixEnum;
import com.hls.wechat.peoperties.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @Author: User-XH251
 * @Date: 2022/7/8 14:29
 */
@Component
@Slf4j
public class WeChatUtil {

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplateUtils restTemplateUtils;


    /**
     * 获取AccessToken
     *
     * @return
     */
    public AccessToken getAccessToken() {
        AccessToken accessToken;
        if (redisUtil.hasKey(RedisPrefixEnum.ACCESSTOKEN.getDesc() + weChatProperties.getAppId())) {
            accessToken = JSON.parseObject((String) redisUtil.get(RedisPrefixEnum.ACCESSTOKEN.getDesc() + weChatProperties.getAppId()), AccessToken.class);
        } else {
            String accessTokenUrl = String.format(weChatProperties.getAccessTokenUrl(), weChatProperties.getAppId(), weChatProperties.getAppSecret());
            String jsonObject = restTemplateUtils.getByMap(accessTokenUrl);
            accessToken = JSON.parseObject(jsonObject, AccessToken.class);
            redisUtil.set(RedisPrefixEnum.ACCESSTOKEN.getDesc() + weChatProperties.getAppId(), accessToken, accessToken.getExpiresIn(), TimeUnit.SECONDS);
        }
        return accessToken;
    }

    /**
     * 获取二维码地址
     */
    public String getQrCodeUrl(String accessToken) throws UnsupportedEncodingException {
        String qrCodeUrl;
        String ticketUrl = String.format(weChatProperties.getTicketUrl(), accessToken);
        String uuid = IdUtil.fastSimpleUUID();
        WxOfficialTicketRequest request = new WxOfficialTicketRequest(uuid);
        String jsonObject = restTemplateUtils.postByMap(ticketUrl, request);
        WxOfficialTicketResponse response =JSON.parseObject(jsonObject, WxOfficialTicketResponse.class);
        qrCodeUrl = String.format(weChatProperties.getQrCodeUrl(), URLEncoder.encode(response.getTicket(), "UTF-8"));
        return qrCodeUrl;
    }

    /**
     * 根据openId获取Unionid
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public UnionIdResponse getUnionId(String accessToken, String openId) {
        String unionIdUrl = String.format(weChatProperties.getUnionIdUrl(), accessToken, openId);
        String jsonObject = restTemplateUtils.getByMap(unionIdUrl);
        UnionIdResponse unionIdResponse =JSON.parseObject(jsonObject, UnionIdResponse.class);
        return unionIdResponse;
    }

    /**
     * 获取微信授权地址
     *
     * @param redirectUri
     * @param authType
     * @return
     */
    public String getWeChatAuthUrl(String redirectUri, String authType) throws UnsupportedEncodingException {
        String authUrl;
        authUrl = String.format(weChatProperties.getAuthUrl(), weChatProperties.getAppId(), URLEncoder.encode(redirectUri, "UTF-8"), authType);
        return authUrl;
    }

    /**
     * 获取auth AuthAccessToken
     *
     * @param code
     * @return
     */
    public AuthAccessToken getAuthAccessToken(String code) {
        String authAccessTokenUrl = String.format(weChatProperties.getAuthAccessTokenUrl(), weChatProperties.getAppId(), weChatProperties.getAppSecret(), code);
        String jsonObject = restTemplateUtils.getByMap(authAccessTokenUrl);
        AuthAccessToken authAccessToken = JSONUtil.toBean(JSONUtil.toJsonStr(jsonObject), AuthAccessToken.class);
        return authAccessToken;
    }

    /**
     * 网页授权获取用户得个人信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public UserInfo getUserInfo(String accessToken, String openId) throws UnsupportedEncodingException {
        String userInfoUrl = String.format(weChatProperties.getUserInfoUrl(), accessToken, openId);
        String jsonObject = restTemplateUtils.getByMap(userInfoUrl);
        UserInfo userInfo = JSONUtil.toBean(JSONUtil.toJsonStr(jsonObject), UserInfo.class);
        //解决微信昵称汉字乱码
        userInfo.setNickname(new String(userInfo.getNickname().getBytes("ISO-8859-1"), "UTF-8"));
        return userInfo;
    }
}

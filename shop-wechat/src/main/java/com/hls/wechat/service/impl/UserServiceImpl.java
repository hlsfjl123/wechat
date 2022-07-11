package com.hls.wechat.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hls.alibaba.entity.User;
import com.hls.wechat.dto.wechat.AuthAccessToken;
import com.hls.wechat.dto.wechat.MessageEventInfo;
import com.hls.wechat.dto.wechat.UnionIdResponse;
import com.hls.wechat.dto.wechat.UserInfo;
import com.hls.wechat.enu.AuthorizationTypeEnum;
import com.hls.wechat.mapper.UserMapper;
import com.hls.wechat.service.UserService;
import com.hls.wechat.util.RedisUtil;
import com.hls.wechat.util.WeChatUtil;
import com.hls.wechat.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:36
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String wxOfficialCallback(HttpServletRequest request) throws IOException {
        //验证token
        request.setCharacterEncoding("UTF-8");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotEmpty(echostr)) {
            String[] str = new String[]{"weixin", timestamp, nonce};
            //排序
            Arrays.sort(str);
            //拼接字符串
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < str.length; i++) {
                buffer.append(str[i]);
            }
            String sha1Hex = DigestUtil.sha1Hex(buffer.toString());
            if (signature.equals(sha1Hex)) {
                log.info("接口验证成功");
                return echostr;
            }
            if (StringUtils.isEmpty(sha1Hex) || !signature.equals(sha1Hex)) {
                log.info("接口验证失败");
                return null;
            }
        }
        //推送事件
        Map<String, String> xmlMap = XmlUtil.xmlToMap(request.getInputStream());
        if ("event".equals(xmlMap.get("MsgType"))) {
            MessageEventInfo eventInfo = JSONUtil.toBean(JSONObject.toJSONString(xmlMap), MessageEventInfo.class);
            String openId = eventInfo.getFromUserName();
            //关注事件推送
            if (eventInfo.getEvent().equals("subscribe")) {
                log.info("用户{}关注了梅斯医学公众号", openId);
                String eventKey = eventInfo.getEventKey().replace("qrscene_", "");
                redisUtil.set("eventPush:" + eventKey, JSONUtil.toJsonStr(eventInfo), 10L, TimeUnit.MINUTES);
            }
            //已关注 推送扫描事件
            if (eventInfo.getEvent().equals("SCAN")) {
                log.info("用户{}扫描了梅斯医学公众号二维码", openId);
                String eventKey = eventInfo.getEventKey();
                redisUtil.set("eventPush:" + eventKey, JSONUtil.toJsonStr(eventInfo), 10L, TimeUnit.MINUTES);
            }
            //取关事件推送 删除用户关联微信数据
            if (eventInfo.getEvent().equals("unsubscribe")) {
                log.info("用户{}取关了梅斯医学公众号", openId);

            }
        }
        return null;
    }

    @Override
    public UserInfo invoke(String code) throws UnsupportedEncodingException {
        AuthAccessToken authAccessToken = weChatUtil.getAuthAccessToken(code);
        UserInfo userInfo = weChatUtil.getUserInfo(authAccessToken.getAccessToken(), authAccessToken.getOpenid());
        return userInfo;
    }

    @Override
    public String weChatAuthUrl(String redirectUri) throws UnsupportedEncodingException {
        String weChatAuthUrl = weChatUtil.getWeChatAuthUrl(redirectUri, AuthorizationTypeEnum.SNSAPI_USERINFO.getDesc());
        return weChatAuthUrl;
    }

    @Override
    public String getQrCode() throws UnsupportedEncodingException {
        String qrCodeUrl = weChatUtil.getQrCodeUrl(weChatUtil.getAccessToken().getAccessToken());
        return qrCodeUrl;
    }

    private String getUnionId(String eventKey) {
        MessageEventInfo messageEventInfo = JSON.parseObject((String) redisUtil.get("eventPush:" + eventKey), MessageEventInfo.class);
        UnionIdResponse unionId = weChatUtil.getUnionId(weChatUtil.getAccessToken().getAccessToken(), messageEventInfo.getFromUserName());
        return unionId.getUnionid();
    }
}

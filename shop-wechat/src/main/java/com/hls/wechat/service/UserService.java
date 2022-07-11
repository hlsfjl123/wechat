package com.hls.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hls.alibaba.entity.User;
import com.hls.wechat.dto.wechat.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: User-XH251
 * @Date: 2022/3/7 15:35
 */
public interface UserService extends IService<User> {
    /**
     * 公众号验证接口（事件推送）
     * @param request
     * @return
     */
    String wxOfficialCallback(HttpServletRequest request) throws IOException;

    /**
     * 回调方法获取用户信息
     * @param code
     * @return
     */
    UserInfo invoke(String code) throws UnsupportedEncodingException;

    /**
     * 网页授权地址
     * @return
     */
    String weChatAuthUrl(String redirectUri) throws UnsupportedEncodingException;

    String getQrCode() throws UnsupportedEncodingException;
}

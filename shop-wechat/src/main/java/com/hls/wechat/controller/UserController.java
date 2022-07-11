package com.hls.wechat.controller;

import com.hls.wechat.dto.wechat.UserInfo;
import com.hls.wechat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: User-XH251
 * @Date: 2022/7/8 16:16
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 接口验证加事件推送
     * @param request
     * @return
     */
    @RequestMapping(value = "wxOfficialCallback")
    public String wxOfficialCallback(HttpServletRequest request) {
        try {
            return userService.wxOfficialCallback(request);
        } catch (IOException e) {
            log.info("事件推送失败", e);
            return "FALSE";
        }
    }

    /**
     * 获取二维码图片地址（带场景值）
     * @return
     */
    @RequestMapping(value = "qrCode")
    public String getQrCode() {
        try {
            return userService.getQrCode();
        } catch (UnsupportedEncodingException e) {
            log.info("获取二维码图片地址", e);
            return null;
        }
    }

    /**
     * 授权地址
     * @return
     */
    @GetMapping(value = "weChatAuthUrl")
    public String weChatAuthUrl() {
        String redirectUri="http://rbtsrp.natappfree.cc/user/invoke";
        try {
            return userService.weChatAuthUrl(redirectUri);
        } catch (Exception e) {
            log.info("获取授权地址失败", e);
            return "";
        }
    }

    /**
     * 回调方法获取用户信息
     * @param code
     * @param state
     * @return
     */
    @GetMapping(value = "invoke")
    public UserInfo invoke(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        UserInfo userInfo = null;
        try {
             userInfo = userService.invoke(code);
        } catch (Exception e) {
            log.info("回调方法获取用户信息失败", e);
        }
        return userInfo;
    }
}

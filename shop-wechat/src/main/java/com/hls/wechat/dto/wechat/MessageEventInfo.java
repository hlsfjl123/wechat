package com.hls.wechat.dto.wechat;

import lombok.Data;

/**
 * @Author: User-XH251
 * @Date: 2022/6/23 17:12
 */
@Data
public class MessageEventInfo {

    /**
     * 开发者微信号
     */
    private String ToUserName;

    /**
     * 用户openid
     */
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private Integer CreateTime;
    /**
     * 消息类型
     */
    private String MsgType;
    /**
     * 事件类型
     */
    private String Event;
    /**
     * 事件KEY值，获取二维码时的scene_id
     */
    private String EventKey;
    /**
     * 二维码的Ticket
     */
    private String Ticket;

}

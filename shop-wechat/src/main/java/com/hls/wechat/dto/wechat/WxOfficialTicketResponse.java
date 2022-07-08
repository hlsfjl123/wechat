package com.hls.wechat.dto.wechat;

import lombok.Data;

/**
 * @Author: User-XH251
 * @Date: 2022/6/23 16:09
 */
@Data
public class WxOfficialTicketResponse {

    //@ApiModelProperty(value = "获取的二维码ticket，凭借此 ticket 可以在有效时间内换取二维码。")
    private String ticket;

    //@ApiModelProperty(value = "该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。")
    private Integer expire_seconds;

    //@ApiModelProperty(value = "二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片")
    private String url;
}

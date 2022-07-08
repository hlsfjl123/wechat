package com.hls.wechat.dto.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: User-XH251
 * @Date: 2022/6/23 15:37
 */
@Data
public class WxOfficialTicketRequest {

    //@ApiModelProperty(value = "该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为60秒。")
    private Integer expire_seconds;
    //@ApiModelProperty(value = "二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值")
    private String action_name;
    //@ApiModelProperty(value = "二维码详细信息")
    private Info action_info;

    public WxOfficialTicketRequest(String uuid) {
        this.expire_seconds = 120;
        this.action_name = "QR_STR_SCENE";
        this.action_info = new Info(new SceneParam(uuid));
    }
   }

@Data
@AllArgsConstructor
class Info {

    private SceneParam scene;

}
    @Data
    @AllArgsConstructor
    class SceneParam {

        //@ApiModelProperty(value = "场景值ID（字符串形式的ID），字符串类型，长度限制为1到64")
        private String scene_str;
    }

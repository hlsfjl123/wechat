package com.hls.wechat.lamada;

import com.hls.wechat.entity.User;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 10:32
 */
public interface IUserFactory <P extends User>{

    P create(String username,String mobile);
}

package com.hls.alibaba.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: User-XH251
 * @Date: 2022/3/15 10:23
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("start insert fill ....");
        //this.strictInsertFill(metaObject,"createTime",()-> LocalDateTime.now(),LocalDateTime.class);
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        //this.strictUpdateFill(metaObject,"updateTime",()-> LocalDateTime.now(),LocalDateTime.class);
        this.strictUpdateFill(metaObject,"updateTime", Date.class,new Date());
    }
}

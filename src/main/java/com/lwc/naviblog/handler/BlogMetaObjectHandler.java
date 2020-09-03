package com.lwc.naviblog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 自定义公共字段填充处理器
 * 对创建和更新时间进行填充
 */
@Component
@Slf4j
public class BlogMetaObjectHandler implements MetaObjectHandler {

    @Value("${server.servlet.context-path}")
    private String rootPath;

    /**
     * 插入操作 自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //获取到需要被填充的字段的值
        Object createTime = metaObject.getValue("createTime");
        if (ObjectUtils.isEmpty(createTime)) {
            log.debug("========插入操作填充字段========");
            metaObject.setValue("createTime", new Date());
        }

        String avatar = (String) metaObject.getValue("avatar");
        if (StringUtils.isEmpty(avatar)) {
            log.debug("========插入操作填充字段========");
            metaObject.setValue("avatar", rootPath+"/images/avatar.jpg");
        }
    }

    /**
     * 修改操作 自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("=========更新操作填充字段========");
        metaObject.setValue("updateTime", new Date());
    }
}

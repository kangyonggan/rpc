package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.util.SpringUtils;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 应用
 *
 * @author kangyonggan
 * @since 2019-02-13
 */
@Data
public class Application implements ApplicationContextAware {

    private String id;

    private String name;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.setApplicationContext(applicationContext);
    }
}

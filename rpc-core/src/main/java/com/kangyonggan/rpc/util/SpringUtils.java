package com.kangyonggan.rpc.util;

import org.springframework.context.ApplicationContext;

/**
 * @author kangyonggan
 * @since 2019-02-14
 */
public final class SpringUtils {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }
}

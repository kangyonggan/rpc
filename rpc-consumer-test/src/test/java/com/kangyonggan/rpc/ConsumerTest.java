package com.kangyonggan.rpc;

import com.kangyonggan.rpc.service.UserService;
import com.kangyonggan.rpc.util.SpringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kangyonggan
 * @since 2019/2/16 0016
 */
public class ConsumerTest {

    private ClassPathXmlApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("rpc-consumer.xml");
    }

    /**
     * 远程调用
     *
     * @throws Exception
     */
    @Test
    public void testRemoteCall() throws Throwable {
        UserService userService = SpringUtils.getApplicationContext().getBean("userService", UserService.class);
        boolean exists = userService.existsMobileNo("18516690317");
        Assert.assertTrue(exists);
        System.in.read();
    }

}

package com.kangyonggan.rpc;

import com.kangyonggan.rpc.service.UserService;
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
    public void testRemoveCall() throws Exception {
        UserService userService = (UserService) context.getBean("userService");
        boolean exists = userService.existsMobileNo("18516690317");
        Assert.assertTrue(exists);
    }

}

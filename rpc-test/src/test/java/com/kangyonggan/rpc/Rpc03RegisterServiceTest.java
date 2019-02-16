package com.kangyonggan.rpc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author kangyonggan
 * @since 2019-02-13
 */
public class Rpc03RegisterServiceTest {

    private ClassPathXmlApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("test.xml");
    }

    /**
     * 发布服务
     *
     * @throws Exception
     */
    @Test
    public void testPublish() throws Exception {
        System.in.read();
    }

}

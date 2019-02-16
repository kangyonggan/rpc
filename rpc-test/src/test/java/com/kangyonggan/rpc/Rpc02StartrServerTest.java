package com.kangyonggan.rpc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author kangyonggan
 * @since 2019-02-13
 */
public class Rpc02StartrServerTest {

    private ClassPathXmlApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("test.xml");
    }

    /**
     * 启动服务端
     *
     * @throws Exception
     */
    @Test
    public void testStartServer() throws Exception {
        System.in.read();
    }

}

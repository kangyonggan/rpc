package com.kangyonggan.rpc;

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
        System.in.read();
    }

}

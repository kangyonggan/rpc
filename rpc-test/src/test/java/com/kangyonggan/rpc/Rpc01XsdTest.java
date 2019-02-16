package com.kangyonggan.rpc;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.pojo.Application;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author kangyonggan
 * @since 2019-02-13
 */
public class Rpc01XsdTest {

    private Logger logger = Logger.getLogger(Rpc01XsdTest.class);

    private ClassPathXmlApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("test.xml");
    }

    /**
     * 解析自定义的xml标签
     */
    @Test
    public void testParse() {
        Application application = (Application) context.getBean(RpcPojo.application.name());
        logger.info(application);
    }

}

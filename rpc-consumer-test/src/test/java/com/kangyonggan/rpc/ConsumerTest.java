package com.kangyonggan.rpc;

import com.kangyonggan.rpc.constants.FaultPolicy;
import com.kangyonggan.rpc.core.GeneralService;
import com.kangyonggan.rpc.pojo.Refrence;
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
     * @throws Throwable
     */
    @Test
    public void testRemoveCall() throws Throwable {
        Object result = GeneralService.invoke("com.kangyonggan.rpc.service.UserService",
                "existsMobileNo", new Class[]{String.class}, new Object[]{"18516690317"});
        boolean exists = (boolean) result;
        Assert.assertTrue(exists);
    }

    /**
     * 远程调用
     *
     * @throws Throwable
     */
    @Test
    public void testRemoveCall2() throws Throwable {
        boolean exists = GeneralService.invoke("com.kangyonggan.rpc.service.UserService",
                "existsMobileNo", new Class[]{String.class}, new Object[]{"18516690317"}, Boolean.class);
        Assert.assertTrue(exists);
    }

    /**
     * 远程调用(全参)
     *
     * @throws Throwable
     */
    @Test
    public void testRemoveCall3() throws Throwable {
        Refrence refrence = new Refrence();
        refrence.setName("com.kangyonggan.rpc.service.UserService");
        // 因为服务版本不存在，所以失败
        refrence.setVersion("1.0.0");
        // 使用失败安全策略，返回boolean的默认值false
        refrence.setFault(FaultPolicy.FAIL_SAFE.getName());

        Object result = GeneralService.invoke(refrence,
                "existsMobileNo", new Class[]{String.class}, new Object[]{"18516690317"}, Boolean.class);
        boolean exists = (boolean) result;
        Assert.assertFalse(exists);
    }

}

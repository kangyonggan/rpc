package com.kangyonggan.rpc.service;

/**
 * @author kangyonggan
 * @since 2019/2/16 0016
 */
public interface UserService {

    /**
     * 判断用户手机号是否存在
     *
     * @param mobileNo
     * @return
     */
    boolean existsMobileNo(String mobileNo);

}

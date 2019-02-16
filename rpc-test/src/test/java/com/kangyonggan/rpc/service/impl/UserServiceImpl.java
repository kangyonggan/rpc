package com.kangyonggan.rpc.service.impl;

import com.kangyonggan.rpc.service.UserService;

/**
 * @author kangyonggan
 * @since 2019/2/16 0016
 */
public class UserServiceImpl implements UserService {

    @Override
    public boolean existsMobileNo(String mobileNo) {
        return "18516690317".equals(mobileNo);
    }
}

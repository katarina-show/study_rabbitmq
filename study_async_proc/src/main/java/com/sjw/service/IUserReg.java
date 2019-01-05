package com.sjw.service;

import com.sjw.vo.User;

/**
 * 用户注册接口
 * 无论是串行注册、并行注册、或是使用rabbitmq都要实现该接口
 */
public interface IUserReg {

    boolean userRegister(User user);

}

package com.sjw.service.impl;

import com.sjw.service.IUserReg;
import com.sjw.service.busi.SaveUser;
import com.sjw.service.busi.SendEmail;
import com.sjw.service.busi.SendSms;
import com.sjw.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 串行的用户注册
 */
@Service
@Qualifier("serial")
public class SerialProcess implements IUserReg {

    @Autowired
    private SaveUser saveUser;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private SendSms sendSms;

    public boolean userRegister(User user) {
        try {
            //串行1步1步来
            saveUser.saveUser(user);
            sendEmail.sendEmail(user.getEmail());
            sendSms.sendSms(user.getPhoneNumber());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

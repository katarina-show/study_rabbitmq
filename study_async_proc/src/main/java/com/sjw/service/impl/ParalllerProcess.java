package com.sjw.service.impl;

import com.sjw.service.IUserReg;
import com.sjw.service.busi.SaveUser;
import com.sjw.service.busi.SendEmail;
import com.sjw.service.busi.SendSms;
import com.sjw.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 并行模式，此处用多线程实现
 */
@Service
@Qualifier("para")
public class ParalllerProcess implements IUserReg {

    private static Logger logger = LoggerFactory.getLogger(ParalllerProcess.class);

    @Autowired
    private SaveUser saveUser;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private SendSms sendSms;

    //发送邮件的线程，使用Callable是为了使用FutureTask异步获取返回值
    private static class SendEmailThread implements Callable<Boolean>{

        private SendEmail sendEmail;
        private String email;

        public SendEmailThread(SendEmail sendEmail, String email) {
            this.sendEmail = sendEmail;
            this.email = email;
        }

        public Boolean call() throws Exception {
            sendEmail.sendEmail(email);
            logger.info("SendEmailThread send email to" + email);
            return true;
        }
    }

    //发送短信的线程，使用Callable是为了使用FutureTask异步获取返回值
    private static class SendSmsThread implements Callable<Boolean>{

        private SendSms sendSms;
        private String phoneNumber;

        public SendSmsThread(SendSms sendSms, String phoneNumber) {
            this.sendSms = sendSms;
            this.phoneNumber = phoneNumber;
        }

        public Boolean call() throws Exception {
            sendSms.sendSms(phoneNumber);
            logger.info("SendSmsThread send sms to" + phoneNumber);
            return true;
        }
    }

    public boolean userRegister(User user) {

        FutureTask<Boolean> sendEmailFuture =
                new FutureTask<Boolean>(new SendEmailThread(sendEmail,user.getEmail()));
        FutureTask<Boolean> sendSmsFuture =
                new FutureTask<Boolean>(new SendSmsThread(sendSms,user.getPhoneNumber()));
        try {

            saveUser.saveUser(user);
            //开启2个线程
            new Thread(sendEmailFuture).start();
            new Thread(sendSmsFuture).start();
            sendEmailFuture.get();//获取邮件发送的结果 call执行完了才有返回
            sendSmsFuture.get();//获取短信发送的结果
            return true;
        } catch (Exception e) {
            logger.error(e.toString());
            return  false;
        }

    }
}

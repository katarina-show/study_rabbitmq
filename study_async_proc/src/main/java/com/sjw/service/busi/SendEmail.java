package com.sjw.service.busi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 发送邮件的服务
 */
@Service
public class SendEmail {

    private Logger logger = LoggerFactory.getLogger(SendEmail.class);

    public void sendEmail(String email){
        try {
            //模拟发送邮件时间耗时100ms
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("-------------Already Send email to "+email);
    }

}

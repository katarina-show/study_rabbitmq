package com.sjw.service.busi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 发送短信的服务
 */
@Service
public class SendSms {

    private Logger logger = LoggerFactory.getLogger(SendSms.class);

    public void sendSms(String phoneNumber){
        try {
            //模拟发送短信耗时100ms
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("-------------Already Send Sms to "+phoneNumber);
    }

}

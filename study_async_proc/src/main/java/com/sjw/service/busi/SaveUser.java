package com.sjw.service.busi;

import com.sjw.vo.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟存入数据库
 */
@Service
public class SaveUser {

    //用这个map保存数据来模拟存入数据库的操作
    private ConcurrentHashMap<String,User> userData =
            new ConcurrentHashMap<String, User>();

    public void saveUser(User user){
        try {
            //模拟存入DB耗时50ms
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userData.putIfAbsent(user.getUserId(),user);
    }

    public User getUser(String userId){
        return userData.get(userId);
    }


}

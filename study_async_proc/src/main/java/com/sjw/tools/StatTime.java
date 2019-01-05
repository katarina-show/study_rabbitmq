package com.sjw.tools;

import com.sjw.vo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 为了做时间统计的一个AOP
 */
@Aspect
@Service
public class StatTime {

    private Logger logger = LoggerFactory.getLogger(StatTime.class);

    private User user;

    public StatTime() {
        logger.info("************Aop开启************");
    }

    //定义切面
    @Pointcut("execution(* com.sjw.service.impl.*.*Register(..))")
    public void stat(){}

    //环绕增强，增强stat方法并且参数是user的，这里的args(user)中的user可以任意命名
    //实际类型为该方法中的参数定义，如此处的User
    //args表达式 有如下两个作用：
    //1.提供了一种简单的方式来访问目标方法的参数
    //2.可用于对切入表达式增加额外的限制
    @Around("stat() && args(user)")
    public Object statTime(ProceedingJoinPoint proceedingJoinPoint,User user){

        //方法前置增强
        this.user = user;
        long start = System.currentTimeMillis();
        Object result = null;
        //前置增强over

        try {
            //调用目标方法
            result = proceedingJoinPoint.proceed(new Object[]{this.user});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //方法后置增强
        logger.info("************spend time : "+(System.currentTimeMillis()-start)+"ms");
        //后置增强over

        return result;

    }

}

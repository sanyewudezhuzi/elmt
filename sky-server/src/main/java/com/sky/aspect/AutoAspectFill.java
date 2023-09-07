package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

// 1. 自定义注解AutoFill用于标识需要进行公共字段自动填充的方法
// 2. 自定义切面类AutoFillAspect统一拦截加入了AutoFill注解的方法，通过反射为公共字段赋值
// 3. 在Mapper的方法上加入AutoFill注解

@Aspect
@Component
@Slf4j
public class AutoAspectFill {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper..*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoPointcutFill() {
    }

    /**
     * 前置增强
     */
    @Before("autoPointcutFill()")
    public void beforeAspect(JoinPoint joinPoint) {
        log.info("beforeAspect start...");

        // 获取到当前被拦截的方法上的数据库操作类型
        // 获得方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获得方法上的注解对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        // 获得方法操作类型
        OperationType operationType = autoFill.value();

        // 获取传入参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object info = args[0];

        // 准备赋值的数据
        LocalDateTime ldt = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        // 根据方法操作类型分类处理
        switch (operationType) {
            case INSERT:
                try {
                    // 通过反射获取需要操作4个方法
                    Method setCreateTime = info.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    setCreateTime.setAccessible(true);
                    Method setCreateUser = info.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                    setCreateUser.setAccessible(true);
                    Method setUpdateTime = info.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    setUpdateTime.setAccessible(true);
                    Method setUpdateUser = info.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                    setUpdateUser.setAccessible(true);

                    // 赋值
                    setCreateTime.invoke(info, ldt);
                    setCreateUser.invoke(info, id);
                    setUpdateTime.invoke(info, ldt);
                    setUpdateUser.invoke(info, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case UPDATE:
                try {
                    // 通过反射获取需要操作2个方法
                    Method setUpdateTime = info.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                    setUpdateTime.setAccessible(true);
                    Method setUpdateUser = info.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                    setUpdateUser.setAccessible(true);

                    // 赋值
                    setUpdateTime.invoke(info, ldt);
                    setUpdateUser.invoke(info, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        log.info("auto fill success...");
    }

}

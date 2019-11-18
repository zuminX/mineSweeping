package com.aspect;

import com.dao.GamePropertiesDao;
import com.service.GameViewService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫雷游戏的拦截器
 * 对异常信息进行拦截
 * 对未开启记录数据进行拦截
 */
@Component("mineSweepingInterceptorProcess")
@Aspect
@Slf4j
public class MineSweepingInterceptorProcess {
    /**
     * 处理扫雷游戏数据的业务层对象
     */
    private final GameViewService gameViewService;
    /**
     * dao层对象
     */
    private final GamePropertiesDao mineDao;

    /**
     * 注入成员变量
     */
    @Autowired
    public MineSweepingInterceptorProcess(GameViewService gameViewService, GamePropertiesDao mineDao) {
        this.gameViewService = gameViewService;
        this.mineDao = mineDao;
    }

    /**
     * 对异常进行拦截处理
     *
     * @param proceedingJoinPoint 连接点对象
     * @return 有异常->null 无异常->执行方法的返回值
     */
    @Around("execution(public * com.service.impl.*ServiceImpl.*(..))")
    public Object ErrorInformationProcess(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        try {
            //执行被增强的方法
            returnValue = executeSourceMethod(proceedingJoinPoint);
        } catch (Throwable throwable) {
            //捕捉异常，记录异常，显示异常信息给用户
            log.info(throwable.toString());
            gameViewService.showErrorInformation(throwable.getMessage());
        }
        return returnValue;
    }

    /**
     * 判断当前是否开启记录数据的功能
     *
     * @param proceedingJoinPoint 连接点对象
     * @return 有异常->null 无异常->执行方法的返回值
     * @throws Throwable 方法执行时发生的异常
     */
    @Around("execution(public * com.service.impl.MineSweepingGameDataServiceImpl.*(..))")
    public Object OpenRecordInterceptorProcess(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (mineDao.findNowOpenRecordStatus()) {
            //执行被增强的方法
            return executeSourceMethod(proceedingJoinPoint);
        }
        return null;
    }

    /**
     * 执行被增强的方法
     *
     * @param proceedingJoinPoint 连接点对象
     * @return 执行方法的返回值
     * @throws Throwable 方法执行时发生的异常
     */
    private Object executeSourceMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}

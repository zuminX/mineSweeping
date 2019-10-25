package com.aspect;

import com.dao.MineDao;
import com.service.ViewService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫雷游戏的拦截器
 * 对异常信息进行拦截
 * 对未开启记录数据进行拦截
 */
@Component
@Aspect
public class MineSweepingInterceptorProcess {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MineSweepingInterceptorProcess.class);

    @Autowired
    private ViewService viewService;

    @Autowired
    private MineDao mineDao;

    /**
     * 对异常进行拦截处理
     *
     * @param proceedingJoinPoint 连接点对象
     *
     * @return 有异常->null 无异常->执行方法的返回值
     */
    @Around("execution(public * com.service.impl.*ServiceImpl.*(..))")
    public Object ErrorInformationProcess(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        try {
            //获取执行方法的参数
            final Object[] args = proceedingJoinPoint.getArgs();
            //执行被增强的方法
            returnValue = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            //捕捉异常，记录异常，显示异常信息给用户
            LOGGER.error("", throwable);
            viewService.showErrorInformation(throwable.getMessage());
        }
        return returnValue;
    }

    /**
     * 判断当前是否开启记录数据的功能
     *
     * @param proceedingJoinPoint 连接点对象
     *
     * @return 有异常->null 无异常->执行方法的返回值
     */
    @Around("execution(public * com.service.impl.MineSweepingGameDataServiceImpl.*(..))")
    public Object OpenRecordInterceptorProcess(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = null;
        if (mineDao.findNowOpenRecordStatus()) {
            //执行被增强的方法
            returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }
        return returnValue;
    }
}

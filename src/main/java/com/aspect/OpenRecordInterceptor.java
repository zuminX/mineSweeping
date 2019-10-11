package com.aspect;

import com.dao.MineDao;
import com.service.ViewService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class OpenRecordInterceptor {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenRecordInterceptor.class);

    @Autowired
    private ViewService viewService;

    @Autowired
    private MineDao mineDao;

    /**
     * 对进行操纵service层方法进行代理增强
     *
     * @param proceedingJoinPoint 连接点对象
     *
     * @return 有异常->true 无异常->false
     */
    @Around("execution(public * com.service.impl.MineSweepingGameDataServiceImpl.*(..))")
    public Object OpenRecordInterceptorProcess(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        try {
            if (mineDao.findNowOpenRecordStatus()) {
                //执行被增强的方法
                returnValue = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            }
        } catch (Throwable throwable) {
            //捕捉异常，记录异常，显示异常信息给用户
            LOGGER.error("", throwable);
            viewService.showErrorInformation(throwable.getMessage());
        }
        return returnValue;
    }
}

package org.sluja.searcher.webapp.aspect.log.noobject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodEndLog;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodStartLog;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class NoObjectMethodLoggingAspect {

    private final LoggerMessageUtils loggerMessageUtils;

    @Before("@annotation(methodStartLog)")
    public void logMethodStart(final JoinPoint joinPoint, final MethodStartLog methodStartLog) {
        final String methodName = joinPoint.getSignature().getName();
        final String classNameWithPackage = joinPoint.getTarget().getClass().getName();
        final String className = classNameWithPackage.substring(classNameWithPackage.lastIndexOf(".") + 1);
        log.info(loggerMessageUtils.getInfoLogMessage(className, methodName,"info.method.start"));
    }

    @After("@annotation(methodEndLog)")
    public void logMethodEnd(final JoinPoint joinPoint, final MethodEndLog methodEndLog) {
        final String methodName = joinPoint.getSignature().getName();
        final String classNameWithPackage = joinPoint.getTarget().getClass().getName();
        final String className = classNameWithPackage.substring(classNameWithPackage.lastIndexOf(".") + 1);
        log.info(loggerMessageUtils.getInfoLogMessage(className, methodName,"info.method.end"));
    }
}

package org.sluja.searcher.webapp.aspect.log.object;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ObjectMethodLoggingAspect {

    private final LoggerMessageUtils loggerMessageUtils;

    @Pointcut("execution(@org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog * org.sluja.searcher.webapp..*.*(..)))")
    public void atExecutionOfObjectMethodStartLog() {}

    @Pointcut("execution(@org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog * org.sluja.searcher.webapp..*.*(..)))")
    public void atExecutionOfObjectMethodEndLog() {}

    @Pointcut("execution(@org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartAndEndLog * org.sluja.searcher.webapp..*.*(..)))")
    public void atExecutionOfObjectMethodStartAndEndLog() {}

    @Before("@annotation(objectMethodStartLog)")
    public void logObjectMethodStart(final JoinPoint joinPoint, final ObjectMethodStartLog objectMethodStartLog) {
        final String methodName = joinPoint.getSignature().getName();
        final String classNameWithPackage = joinPoint.getTarget().getClass().getName();
        final String className = classNameWithPackage.substring(classNameWithPackage.lastIndexOf(".") + 1);
        final Object[] inputs = joinPoint.getArgs();
        log.info(loggerMessageUtils.getInfoLogMessageWithObjects(className, methodName,"info.method.start.with.object",inputs));
    }

    @AfterReturning(pointcut = "@annotation(objectMethodEndLog)", returning = "result")
    public void logObjectMethodEnd(final JoinPoint joinPoint, final Object result, final ObjectMethodEndLog objectMethodEndLog) {
        final String methodName = joinPoint.getSignature().getName();
        final String classNameWithPackage = joinPoint.getTarget().getClass().getName();
        final String className = classNameWithPackage.substring(classNameWithPackage.lastIndexOf(".") + 1);
        log.info(loggerMessageUtils.getInfoLogMessageWithObjects(className, methodName,"info.method.end.with.object", new Object[]{result}));
    }

    @AfterThrowing(pointcut = "@annotation(objectMethodEndLog)", throwing = "exception")
    public void logObjectMethodEndOnException(final JoinPoint joinPoint, final Throwable exception, final ObjectMethodEndLog objectMethodEndLog) {
        final String methodName = joinPoint.getSignature().getName();
        final String className = joinPoint.getTarget().getClass().getName();
        log.info(loggerMessageUtils.getInfoLogMessage(className, methodName, InformationMessageBuilder.buildParametrizedMessage("error.method.end.with.object.exception", List.of(exception.getMessage()))));
    }
}

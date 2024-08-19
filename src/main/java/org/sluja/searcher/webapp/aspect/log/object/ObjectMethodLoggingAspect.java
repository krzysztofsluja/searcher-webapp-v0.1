package org.sluja.searcher.webapp.aspect.log.object;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessageList;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.sluja.searcher.webapp.utils.message.implementation.ErrorMessageReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ObjectMethodLoggingAspect {

    private final LoggerMessageUtils loggerMessageUtils;
    private final ErrorMessageReader errorMessageReader;

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
        String messageText = exception.getMessage();
        List<String> errorsList = new ArrayList<>();
        if(exception instanceof ExceptionWithErrorCodeAndMessage e) {
            messageText = errorMessageReader.getPropertyValueOrGeneralMessageOnDefault(e.getMessageCode());
            errorsList = List.of(messageText);
        } else if (exception instanceof ExceptionWithErrorCodeAndMessageList e) {
            errorsList = e.getMessageCodes()
                    .stream()
                    .filter(StringUtils::isNotEmpty)
                    .map(errorMessageReader::getPropertyValueOrGeneralMessageOnDefault)
                    .toList();
        }
        log.info(loggerMessageUtils.getInfoLogMessage(className, methodName, InformationMessageBuilder.buildParametrizedMessage("error.method.end.with.object.exception", errorsList)));
        //log.info(loggerMessageUtils.getInfoLogMessage(className, methodName, InformationMessageBuilder.buildParametrizedMessage("error.method.end.with.object.exception", List.of(exception.getMessage()))));
    }
}

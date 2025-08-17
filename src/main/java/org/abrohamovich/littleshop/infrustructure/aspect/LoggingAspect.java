package org.abrohamovich.littleshop.infrustructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* org.abrohamovich.littleshop.application.usecase..*(..))")
    public void applicationLayerMethods() {
    }

    @Pointcut("execution(* org.abrohamovich.littleshop.adapter.persistence..*(..))")
    public void persistenceLayerMethods() {
    }

    @Around("applicationLayerMethods() || persistenceLayerMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        log.info("Entering method: {}.{} with args: {}", className, methodName, args);

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("Exiting method: {}.{} (Execution time: {}ms) with result: {}", className, methodName, (endTime - startTime), result);
            return result;
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument in {}.{}: {}", className, methodName, e.getMessage());
            throw e;
        } catch (Throwable e) {
            log.error("Exception in {}.{}: {}", className, methodName, e.getMessage(), e);
            throw e;
        }
    }
}

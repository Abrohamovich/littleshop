package org.abrohamovich.littleshop.infrustructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;
import org.abrohamovich.littleshop.domain.exception.ModelValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {
    @Pointcut("execution(* org.abrohamovich.littleshop.application.usecase..*(..))")
    public void useCaseMethods() {}

    @Pointcut("execution(* org.abrohamovich.littleshop.adapter.persistence..*(..))")
    public void persistenceAdapterMethods() {}

    @Around("useCaseMethods() || persistenceAdapterMethods()")
    public Object handleServiceExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ModelNotFoundException e) {
            log.warn("Model not found error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage());
            throw e;
        } catch (ModelValidationException e) {
            log.warn("Model validation error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage());
            throw e;
        } catch (DuplicateEntryException e) {
            log.warn("Duplicate entry error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage());
            throw e;
        } catch (DataPersistenceException e) {
            log.error("Persistence error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage(), e);
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error in {}: {}", joinPoint.getSignature().toShortString(), e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred during operation.", e);
        }
    }
}

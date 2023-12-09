package com.vn.investion.audit;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Aspect
@Component
public class AuditAspect {

    @Around("@annotation(com.vn.investion.audit.AutoAppendAuditInfo)")
    public Object aroundAccountAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var inputter = getUserName(authentication);
        var now = OffsetDateTime.now();
        if (args.length > 0 && args[0] instanceof AuditEntity auditEntity) {
            if (auditEntity.getCreatedAt() == null) {
                auditEntity.setCreatedAt(now);
                auditEntity.setCreatedBy(inputter);
            }
            auditEntity.setUpdatedAt(now);
            auditEntity.setUpdatedBy(inputter);
        }
        return joinPoint.proceed();
    }

    private String getUserName(Authentication authentication){
        var data = authentication.getPrincipal();
        if(data instanceof User user){
            return user.getUsername();
        }
        return null;
    }
}
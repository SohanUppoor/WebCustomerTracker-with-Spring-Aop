package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	private Logger myLogger=Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		String theMethod=theJoinPoint.getSignature().toShortString();
		myLogger.info("=====> @before calling method: "+theMethod);
		
		Object Args[]=theJoinPoint.getArgs();
		for (Object tempArg : Args) {
			myLogger.info("====>argument: "+tempArg);
		}
		
	}
	
	
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning ="theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		String theMethod= theJoinPoint.getSignature().toShortString();
		myLogger.info("=====> @After calling method: "+theMethod);
		myLogger.info("====>Result: "+theResult);
	}
	
	
}

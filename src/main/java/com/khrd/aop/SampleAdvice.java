package com.khrd.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleAdvice {
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.khrd.service.ReplyServiceImpl.listCri(..))") //* 패키지경로.클래스명(매개변수)
	public void startLog(JoinPoint jp) {
		logger.info("*********************************************");
		logger.info("**************** [Start Log] ****************");
		logger.info(Arrays.toString(jp.getArgs()));
		logger.info("*********************************************");
	}
	
	@Around("execution(* com.khrd.controller.ReplyController.listCri(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("*********************************************");
		logger.info("**************** [Time Log] START ****************");
		logger.info(Arrays.toString(pjp.getArgs()));
		logger.info("*********************************************");
		
		Object result = pjp.proceed(); //service의 listAll 함수 호출
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		logger.info("*********************************************");
		logger.info("*************** [Time Log] END ***************");
		logger.info("time ::: " + time); //처리하는데 걸린 시간
		logger.info("*********************************************");
		return result;
	}
}

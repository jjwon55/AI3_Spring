package com.aloha.spring.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.aloha.spring.dto.Board;

@EnableAspectJAutoProxy
@Component
@Aspect
public class LoggingAspect {

	private static final Logger logger
				= LoggerFactory.getLogger(LoggingAspect.class);
	/*
	 * 어드바이스 유형
	 * - Around
	 * - Before
	 * - After
	 * - AfterReturning
	 * - AfterThrowing 
	*/ 
	/* 포인트컷 표현요소
	 * - 지시자 : execution
	 * - 반환값
	 * - 패키지
	 * - 클래스
	 * - 메소드 
	 * - 인수
	 * 
	 */
	// execution(접근제한자 반환타입 패키지.클래스.메서드(파라미터)
	@Before("execution(* com.aloha.spring.service.BoardService*.*(..))") // 포인트 컷
	public void before(JoinPoint jp) {
		// jp.getSingature() : 타겟 메소드이 시그니처 정보(반환타입, 패키지.클래스.메소드) 반환
		// jp.getArgs()		 : 타겟 메소드의 매개변수를 반환
		logger.info("===============================================");
		logger.info("[@Before] ###################################");
		logger.info("target : " + jp.getTarget().toString());
		logger.info("sinature : " + jp.getSignature());
		logger.info("args : " + Arrays.toString(jp.getArgs()));
		// 파라미터 출력
		printParam(jp);
		logger.info("=============================================");
	}
	@After("execution(* com.aloha.spring.service.BoardService*.*(..))") // 포인트 컷
	public void after(JoinPoint jp) {
		// jp.getSingature() : 타겟 메소드이 시그니처 정보(반환타입, 패키지.클래스.메소드) 반환
		// jp.getArgs()		 : 타겟 메소드의 매개변수를 반환
		logger.info("===============================================");
		logger.info("[@After] ###################################");
		logger.info("target : " + jp.getTarget().toString());
		logger.info("sinature : " + jp.getSignature());
		logger.info("args : " + Arrays.toString(jp.getArgs()));
		// 파라미터 출력
		printParam(jp);
		logger.info("=============================================");
	}
	/*
	 * @Around 유형을 적용하면, @After 어드바이스는 실행되지 않는다.
	 * (Around 에서 직접 after 를 호출하여 실행시킨다.)
	 * 
	 * ProceedingJoinPoint 	: 어드바이스에서 타겟 메서드의 실행을 제어하고 호출하는 객체
	 * - proceed(); 		: 타겟 메소드 호출
	 */
	@Around("execution(* com.aloha.spring.service.BoardService*.*(..))") // 포인트 컷
	public Object around(ProceedingJoinPoint jp) {
		// jp.getSingature() : 타겟 메소드이 시그니처 정보(반환타입, 패키지.클래스.메소드) 반환
		// jp.getArgs()		 : 타겟 메소드의 매개변수를 반환
		logger.info("===============================================");
		logger.info("[@Around] ###################################");
		logger.info("target : " + jp.getTarget().toString());
		logger.info("sinature : " + jp.getSignature());
		logger.info("args : " + Arrays.toString(jp.getArgs()));
		LocalDateTime time = LocalDateTime.now();
		logger.info("현재 시간 : " + time);
	
		
		Object result = null;
		try {
			result = jp.proceed();
			if(result != null )
				logger.info("반환값 : " + result.toString());
		} catch (Throwable e) {
			logger.error("예외가 발생하였습니다.");
			e.printStackTrace();
		}
		after(jp);
		logger.info("=============================================");
		return result;
	}
	
	// pointcut : 포인트 컷 표현식
	// returning : 타겟 메소드이 반환값을 저장하는 매개변수명 지정
	@AfterReturning(pointcut = "execution(* com.aloha.spring.service.BoardService*.*(..))"
					, returning = "result")
	public void afterReturning(JoinPoint jp, Object result) {
		logger.info("===============================================");
		logger.info("[@AfterReturning] ###################################");
		logger.info("target : " + jp.getTarget().toString());
		logger.info("sinature : " + jp.getSignature());
		logger.info("args : " + Arrays.toString(jp.getArgs()));
		// 파라미터 출력
		printParam(jp);
		
		// 반환갑 출ㄹ겨
		if (result != null) 
			logger.info("반환값 : " + result.toString());
		if (result instanceof Board) {
			result = (Board) result;
			logger.info("반환값 : " + result);
			
		}
		logger.info("=============================================");
	}
	
	@AfterThrowing(pointcut = "execution(* com.aloha.spring.service.BoardService*.*(..))"
			, throwing = "exception")
	public void afterThrowing(JoinPoint jp, Exception exception) {
		logger.info("===============================================");
		logger.info("[@AfterThrowing] ###################################");
		logger.info("target : " + jp.getTarget().toString());
		logger.info("sinature : " + jp.getSignature());
		logger.info( exception.toString());
		logger.info("=============================================");
	}
	
	public void printParam(JoinPoint jp) {
		Signature signature = jp.getSignature();
		
		String[] parameterNames = ((MethodSignature) signature).getParameterNames();
		
		Object[] args = jp.getArgs();
		
		if (parameterNames != null) {
			for (int i = 0; i < parameterNames.length; i++) {
				String paramName = parameterNames[i];
				Object paramValue = args[i];
				logger.info("파라미터명 : " + paramName + ", 값: " + paramValue);
				
			}
			
			
		}
	}
}

package com.aloha.string.service;

import org.springframework.stereotype.Service;

@Service		// 서비스 빈으로 등록
				// 빈이름 지정하지 않으면, "BServiceImpl"으로 빈이름 지정됨
public class BServiceImpl implements MyService {

	@Override
	public void test() {
		System.out.println("BServiceImpl...");
	}

}

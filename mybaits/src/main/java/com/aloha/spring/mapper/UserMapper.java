package com.aloha.spring.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.spring.dto.User;

@Mapper
public interface UserMapper {

	public int join(User user) throws Exception;

}

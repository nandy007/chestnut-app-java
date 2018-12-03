package com.nandy007.web.dao;

import org.apache.ibatis.annotations.Param;

import com.nandy007.web.model.UserAuthority;

public interface UserAuthorityMapper {
	
	UserAuthority getByUserId(@Param("userId") Integer userId);
	UserAuthority getByUserName(@Param("userName") String userName);
}
package com.nandy007.web.service.impl;

import com.nandy007.web.dao.UserAuthorityMapper;
import com.nandy007.web.model.UserAuthority;
import com.nandy007.web.service.UserAuthorityService;
// import com.nandy007.web.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/10/27.
 */
@Service
@Transactional
public class UserAuthorityServiceImpl implements UserAuthorityService {
    @Resource
    private UserAuthorityMapper userAuthorityMapper;

    public UserAuthority getByUserId(Integer userId){
    	return userAuthorityMapper.getByUserId(userId);
    }
}

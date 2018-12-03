package com.nandy007.web.service;
import com.nandy007.web.model.User;
import com.nandy007.web.core.Service;


/**
 * Created by CodeGenerator on 2017/10/26.
 */
public interface UserService extends Service<User> {
	
	User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}

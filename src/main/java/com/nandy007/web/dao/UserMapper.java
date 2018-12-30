package com.nandy007.web.dao;

import java.util.List;

import com.nandy007.web.core.Mapper;
import com.nandy007.web.model.User;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends Mapper<User> {

    //如果实例对象中的属性名和数据表中字段名不一致，用@Result注解进行说明映射关系，我在这里只是告诉你怎么写
    @Select("SELECT * FROM user")
    // @Results({
    //         @Result(property = "username", column = "username"),
    //         @Result(property = "sex",  column = "sex"),
    //         @Result(property = "address",column = "address")
    // })
    List<User> selectAll();
}
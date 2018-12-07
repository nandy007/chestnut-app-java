package com.nandy007.web.controller;

import com.nandy007.web.core.Result;
import com.nandy007.web.core.ResultGenerator;
import com.nandy007.web.model.User;
// import com.nandy007.web.model.UserAuthority;
import com.nandy007.web.service.UserAuthorityService;
import com.nandy007.web.service.UserService;
import com.nandy007.web.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Created by CodeGenerator on 2017/10/26.
*/
@RestController
@RequestMapping("/user")
@Api(value = "UserController" ,description="用户相关API")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;
    
    @Resource
    private UserAuthorityService userAuthorityService;
   
    
    @PostMapping
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }
    

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path",  required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user",  paramType = "body" , required = true, dataType = "User")
    })
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @ApiOperation(value="获取用户列表", notes="")
    @GetMapping
    // public Result list(@RequestAttribute String username, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        logger.info("sessionId:" + SessionUtil.get("token"));
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

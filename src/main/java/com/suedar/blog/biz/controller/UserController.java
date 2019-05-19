package com.suedar.blog.biz.controller;

import com.suedar.blog.api.domain.UserBaseInfo;
import com.suedar.blog.biz.base.BaseResult;
import com.suedar.blog.biz.constants.UserConstant;
import com.suedar.blog.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/selectUserInfo")
    public BaseResult<UserBaseInfo> selectByUserId() {
        UserBaseInfo userBaseInfo = userService.selectByUserId(UserConstant.USER_ID);
        if (userBaseInfo == null) {
            return BaseResult.errorReturn("用户不存在");
        }
        return BaseResult.rightReturn(userBaseInfo);
    }

    @PostMapping("/personalMsg")
    public BaseResult<Integer> updateByUserId(@RequestBody UserBaseInfo userBaseInfo) {
        userBaseInfo.setUserId(UserConstant.USER_ID);
        int res = userService.updateByUserId(userBaseInfo);
        if (res < 1) {
            return BaseResult.errorReturn("更新用户信息失败");
        }
        return BaseResult.rightReturn(res);
    }

    @PostMapping("/login")
    public BaseResult<Void> login(@RequestBody UserBaseInfo userBaseInfo) {
        UserBaseInfo dbUser = userService.selectByUserName(userBaseInfo.getUserName());
        if (dbUser == null) {
            return BaseResult.errorReturn("用户不存在");
        }

        String dbPassword = dbUser.getPassword();
        if (dbPassword.equals(userBaseInfo.getPassword())) {
            return BaseResult.rightReturn(null);
        }

        return BaseResult.errorReturn("密码不正确");
    }

}

package com.suedar.blog.biz.controller;

import com.suedar.blog.api.domain.UserBaseInfo;
import com.suedar.blog.biz.base.BaseResult;
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

    @GetMapping("/selectByUserId")
    public BaseResult<UserBaseInfo> selectByUserId(@RequestParam("userId") Integer userId) {
        UserBaseInfo userBaseInfo = userService.selectByUserId(userId);
        if (userBaseInfo == null) {
            return BaseResult.errorReturn("用户不存在");
        }
        return BaseResult.rightReturn(userBaseInfo);
    }

    @PostMapping("/personalMsg")
    public BaseResult<Integer> updateByUserId(@RequestBody UserBaseInfo userBaseInfo) {
        int res = userService.updateByUserId(userBaseInfo);
        if (res < 1) {
            return BaseResult.errorReturn("更新用户信息失败");
        }
        return BaseResult.rightReturn(res);
    }
}

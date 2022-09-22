package com.zhichanzaixian.trademarkapi.module.login.controller;


import com.zhichanzaixian.trademarkapi.comm.result.Result;
import com.zhichanzaixian.trademarkapi.module.login.entity.UserLogin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author syl
 * @since 2019-12-05
 */
@RestController
@RequestMapping("/userProfile")
public class UserLoginController {

    @GetMapping("/info")
    public Object test(@AuthenticationPrincipal UserLogin userLogin) {
        return Result.success(userLogin);
    }

}

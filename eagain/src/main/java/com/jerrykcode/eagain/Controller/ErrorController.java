package com.jerrykcode.eagain.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @RequestMapping("/error/403")
    public String error() {
        return "您没有权限访问该接口:(";
    }
}

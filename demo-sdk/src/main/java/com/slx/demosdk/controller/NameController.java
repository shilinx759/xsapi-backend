package com.slx.demosdk.controller;

import com.slx.xsapiclientsdk.model.User;
import com.slx.xsapiclientsdk.util.SignUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author slx
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {

        return "GET 你的名字是:" + name;
    }
    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "RequestParam 你的名字是:" + name;
    }
    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        final String accessKey = request.getHeader("accessKey");
        final String nonce = request.getHeader("nonce");
        final String timestamp = request.getHeader("timestamp");
        final String body = request.getHeader("body");
        final String sign = request.getHeader("sign");
        //todo 从数据库中查出 accessKey 是否一致
        if (!"slx".equals(accessKey)) {
            throw new RuntimeException("无权限");
        }
        //todo 校验nonce,从数据库中查是否一致
        if (Long.parseLong(nonce)>10000) {
            throw new RuntimeException("无权限");
        }
        //todo 校验时间在 5 分钟之内
//        if (timestamp) {
//            throw new RuntimeException("无权限");
//        }
        //根据参数 body 和 secretKey(从数据库中查出的) 使用跟客户端一致的加密算法进行加密
        final String serverSign = SignUtil.getSign(body, "abcdefgh");
        //服务器和客户端生成的签名不一致,则不匹配
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }

        return "RequestBody 你的用户名是:" + user.getUsername();
    }
}

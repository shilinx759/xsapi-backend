package com.slx.demosdk;

import com.slx.xsapiclientsdk.client.XsApiClient;
import com.slx.xsapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoSdkApplicationTests {
    @Resource
    XsApiClient xsApiClient;

    @Test
    void contextLoads() {
        String result = xsApiClient.getNameByGet("slx");
        User user = new User();
        user.setUsername("shilinx");
        String shilinx = xsApiClient.getUsernameByPost(user);
        System.out.println("shilinx = " + shilinx);
        System.out.println("result = " + result);
    }
}

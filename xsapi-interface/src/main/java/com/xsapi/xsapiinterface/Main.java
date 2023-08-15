package com.xsapi.xsapiinterface;

import com.xsapi.xsapiinterface.client.XsApiClient;
import com.xsapi.xsapiinterface.model.User;

/**
 * @author slx
 * @time 16:25
 */
public class Main {
    public static void main(String[] args) {
         String accessKey="slx";
        String secretKey = "abcdefgh";
        XsApiClient client = new XsApiClient(accessKey,secretKey);
        final String result1 = client.getNameByGet("shilinx");
        final String result2 = client.getNameByPost("post shlinx");
        User user = new User();
        user.setUsername("username shilinx");
        final String result3 = client.getUsernameByPost(user);
        System.out.println("1:"+result1);
        System.out.println("2:"+result2);
        System.out.println("3:"+result3);
    }
}

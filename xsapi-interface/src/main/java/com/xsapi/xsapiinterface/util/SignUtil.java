package com.xsapi.xsapiinterface.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;


/**
 * @author slx
 * @time 20:45
 */
public class SignUtil {

    public static String getSign(String body,String secretKey) {
        //签名生成算法
        final Digester digester = new Digester(DigestAlgorithm.SHA256);
        //使用密钥作为加密成分
        String content=body.toString()+"."+secretKey;
        return digester.digestHex(content);
    }
}

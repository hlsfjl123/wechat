package com.hls.alibaba.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * @Author: User-XH251
 * @Date: 2022/3/18 13:45
 */
public class JwtUtil {

    /**
     * 公钥解析token
     *
     * @return
     */
    public static Jws<Claims> parserToken(String token, String publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }
}

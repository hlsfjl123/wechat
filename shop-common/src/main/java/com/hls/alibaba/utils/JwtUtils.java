package com.hls.alibaba.utils;

import com.hls.alibaba.entity.JwtInfo;
import com.hls.alibaba.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @Author: User-XH251
 * @Date: 2022/3/16 13:48
 */
public class JwtUtils {
    /**
     * 创建token
     *
     * @param user
     * @return
     */
    public static String createToken(User user,Integer expire,String secret) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, expire);
        String compact = Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getId())
                .claim("username", user.getUserName())
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return compact;
    }

    /**
     * 公钥解析token
     *
     * @return
     */
    public static Jws<Claims> parserToken(String token,String publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token 返回 Claims
     *
     * @return
     */
    public static Claims getClaims(String token,String publicKey) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 从token获取用户信息
     *
     * @param token
     * @return
     */
    public  static JwtInfo getInfoFromToken(String token,String publicKey) {
        Jws<Claims> claimsJws = parserToken(token,publicKey);
        Claims body = claimsJws.getBody();
        return new JwtInfo(Integer.valueOf(body.get("userId").toString()), body.getSubject());
    }


}

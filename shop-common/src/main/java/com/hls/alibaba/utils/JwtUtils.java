package com.hls.alibaba.utils;

import com.hls.alibaba.config.JwtConfig;
import com.hls.alibaba.entity.JwtInfo;
import com.hls.alibaba.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @Author: User-XH251
 * @Date: 2022/3/16 13:48
 */
public class JwtUtils {
    @Autowired
    private JwtConfig jwtConfig;


    /**
     * 创建token
     *
     * @param user
     * @return
     */
    public String createToken(User user) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, jwtConfig.getExpire());
        String compact = Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getId())
                .claim("username", user.getUserName())
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getPrivateKey())
                .compact();
        return compact;
    }

    /**
     * 公钥解析token
     *
     * @return
     */
    public Jws<Claims> parserToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getPublicKey()).parseClaimsJws(token);
    }

    /**
     * 公钥解析token 返回 Claims
     *
     * @return
     */
    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwtConfig.getPublicKey()).parseClaimsJws(token).getBody();
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
    public JwtInfo getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        return new JwtInfo(Integer.valueOf(body.get("userId").toString()), body.getSubject());
    }


}

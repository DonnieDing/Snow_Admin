package com.snow.dcl.utils;

import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //传入用户信息，生成token
    public String generateToken(SysUser sysUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", sysUser.getId());
        map.put("created", new Date());
        return this.generateJwt(map);
    }

    //根据荷载信息生成token
    private String generateJwt(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                // 设置签发时间
                // .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    //获取token中的信息(获取token中的荷载信息)
    public Claims getTokenBody(String token) {

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }

        return claims;
    }

    //从token中获取用户ID
    public Long getUserIdByToken(String token) {
        Claims claims = this.getTokenBody(token);
        if (null != claims) {
            return Long.parseLong(claims.get("userId").toString());
        } else {
            throw new CustomException("Token无效！");
        }

    }

    //判断token是否过期
    public boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }


    // 刷新token
    public String refreshToken(String token) {
        Claims claims = getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }

}

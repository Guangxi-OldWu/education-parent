package com.wuzhangze.commonutil;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
/**
 * @author OldWu
 * @date 2021/9/4 14:42
 */
public class JwtUtils {
    // token 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 密钥
    public static final String APP_SECRET = "gdfahdfjWdazGHDsWHFGH2Wx";

    /**
     * 生成 token字符串
     * @param id
     * @param nickname
     * @return
     */
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                // 生成 JWT 头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // jwt所面向的用户
                .setSubject("lz-user")
                // jwt签发时间
                .setIssuedAt(new Date())
                // jwt的过期时间，这个过期时间必须要大于签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                // 自定义 token主体部分，里面可以存储信息
                .claim("id", id)
                .claim("nickname", nickname)

                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    public static String getJwtToken(String username){
        String JwtToken = Jwts.builder()
                // 生成 JWT 头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // jwt所面向的用户
                .setSubject("lz-user")
                // jwt签发时间
                .setIssuedAt(new Date())
                // jwt的过期时间，这个过期时间必须要大于签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                // 自定义 token主体部分，里面可以存储信息
                .claim("username", username)

                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效，解析时若过期会抛出ExpiredJwtException异常
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效 ,解析时若过期会抛出ExpiredJwtException异常
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id ,解析时若过期会抛出ExpiredJwtException异常
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        if(StringUtils.isEmpty(request.getHeader("token"))) {
            throw new ExpiredJwtException(null,null,"请先登录");
        }
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        // 通过key 可以获取 头、主体、密钥信息
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    public static String getUsernameByJwtToken(HttpServletRequest request) {
        if(StringUtils.isEmpty(request.getHeader("token"))) {
            throw new ExpiredJwtException(null,null,"请先登录");
        }
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        // 通过key 可以获取 头、主体、密钥信息
        Claims claims = claimsJws.getBody();
        return (String)claims.get("username");
    }

    /**
     * 根据token获取会员id ,解析时若过期会抛出ExpiredJwtException异常
     * @param token
     * @return
     */
    public static String getMemberIdByJwtToken(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        // 通过key 可以获取 头、主体、密钥信息
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    public static void removeToken(String token) {

    }
}

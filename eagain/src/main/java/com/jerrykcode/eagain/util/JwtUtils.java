package com.jerrykcode.eagain.util;

import com.alibaba.fastjson.JSONArray;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static String jwtSecret = "7d8g32t7kgdfedwkde*Y#(@@udg3";

    private static long jwtExpirationMs = 3600000;

    private static final String ROLE_CLAIMS = "roles";

    public static String generateJwtToken(UserDetailsImpl user) {

        String token = Jwts
                .builder()
                .claim(ROLE_CLAIMS, user.getAuthorities())
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return token;
    }

    public static Claims validateJwtToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        finally {
            return claims;
        }
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        Claims claims = validateJwtToken(token);
        if (claims != null)
            return claims.get("username").toString();
        else return null;
    }

    /**
     * 获取用户角色
     *
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserRole(String token) {
        Claims claims = validateJwtToken(token);
        if (claims == null)
            return null;
        List roles = (List) claims.get(ROLE_CLAIMS);
        String json = JSONArray.toJSONString(roles);
        List<SimpleGrantedAuthority>
                grantedAuthorityList =
                JSONArray.parseArray(json, SimpleGrantedAuthority.class);

        return grantedAuthorityList;
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

}
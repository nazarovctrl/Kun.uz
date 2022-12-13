package com.example.kunuz.util;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exp.JwtNotValidException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "topsecretKey!123";
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("kunuz test portali");
        return jwtBuilder.compact();
    }

    public static String encode(Integer profileId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);


        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (3600*100000)));
        jwtBuilder.setIssuer("kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            Integer id = (Integer) claims.get("id");

            String role = (String) claims.get("role");
            ProfileRole profileRole = ProfileRole.valueOf(role);

            return new JwtDTO(id, profileRole);
        } catch (JwtException e) {
            throw new JwtNotValidException("Jwt not valid");
        }
    }


    public static Integer decodeId(String id) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(id);

            Claims claims = jws.getBody();

            return (Integer) claims.get("id");

        } catch (JwtException e) {
            throw new JwtNotValidException("Jwt not valid");
        }
    }

//    public static JwtDTO getJwtDTOFromAuthToken(String authToken) {
//        if (authToken.isBlank()) {
//            throw new JwtNotValidException("Jwt Not Found");
//        }
//        String token = authToken.substring(7);
//        return JwtUtil.decode(token);
//    }



   /* public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            Integer id = (Integer) claims.get("id");

            String role = (String) claims.get("role");
            ProfileRole profileRole = ProfileRole.valueOf(role);

            return new JwtDTO(id, profileRole);

        } catch (SignatureException e) {
            // so("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            //  logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (JwtException e) {
            //
        }
        return null;
    }*/
}

package com.provider_oidc_laowu.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-11-30 19:34
 */
public class JwtUtils {
    /**
     * 有效期 60 * 60 *1000 一个小时
     */
    public static final long JWT_TTL = 60 * 60 * 1000L;

    /**
     * 设置秘钥明文
     */
    public static final String JWT_KEY = "key";

    /**
     * 生成加密后的秘钥
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建token
     *
     * JWT由三部分组成：头部、载荷与签名。
     *
     * 头部（Header）
     * 头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个JSON对象。
     * {"typ":"JWT","alg":"HS256"}
     * 在头部指明了签名算法是HS256算法。 我们进行BASE64编码（编码不是加密）
     *
     * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
     *
     * 载荷（playload）
     * 载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分
     * 标准中注册的声明（建议但不强制使用）
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * {"sub":"1234567890","name":"John Doe","admin":true}
     *
     * 然后将其进行base64编码，得到Jwt的第二部分。
     * eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
     *
     * 签证（signature）
     * jwt的第三部分是一个签证信息，这个签证信息由三部分组成：header (base64后的)、payload (base64后的)、secret(盐，加盐加密)
     * 这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。
     * secret:
     * TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
     *
     * 将这三部分用.连接成一个完整的字符串,构成了最终的jwt:
     * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
     * 注意：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     *
     * @param id
     * @param subject
     * @param issuer
     * @param claims
     * @return
     */
    public static String signJwt(String id, String subject, String issuer, Map<String, Object> claims) {
        //定义jwt签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //当前时间
        long nowMillis = System.currentTimeMillis();
        //将当前时间转换日期类型
        Date now = new Date(nowMillis);
        //将当前时间+超时时间
        long expMillis = nowMillis + JWT_TTL;
        //将时间定义为date类型
        Date expDate = new Date(expMillis);

        //获取签名时候使用的密钥
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                                // 唯一的ID
                                .setId(id)
                                // 主题可以是JSON数据
                                .setSubject(subject)
                                // 签发者
                                .setIssuer(issuer)
                                // 签发时间
                                .setIssuedAt(now)
                                // 使用HS256对称加密算法签名, 第二个参数为秘钥
                                .signWith(signatureAlgorithm, secretKey)
                                // 设置过期时间
                                .setExpiration(expDate)
                                // 自定义添加信息
                                .setClaims(claims);
                                //.claim("role","admin");

        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jwt
     */
    public static Claims parserJwt(String jwt) {
        return Jwts.parser()
                // 设置公匙
                .setSigningKey(generalKey())
                // 解析jwt
                .parseClaimsJws(jwt)
                // 获取解析类
                .getBody();
    }

    /**
     * 通过密匙文件获取公匙key
     * @param file
     * @param kid
     * @return
     */
    public static RsaJsonWebKey generalKeyFromFile(String file, String kid) {
        JsonWebKeySet jsonWebKeySet;
        try (InputStream is = JwtUtils.class.getClassLoader().getResourceAsStream(file)) {
            if(is != null) {
                String keyJson = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
                jsonWebKeySet = new JsonWebKeySet(keyJson);
                return (RsaJsonWebKey) jsonWebKeySet.findJsonWebKey(kid, RsaKeyUtil.RSA, "sig", "RS256");
            }

            return null;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    /**
     * 通过密匙地址获取公匙key
     * @param uri
     * @param kid
     * @return
     */
    public static RsaJsonWebKey generalKeyFromUri(String uri, String kid) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String keyJson = restTemplate.getForObject(uri, String.class);
            JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(keyJson);
            return (RsaJsonWebKey) jsonWebKeySet.findJsonWebKey(kid, RsaKeyUtil.RSA, "sig", "RS256");
        } catch (RestClientException | JoseException e) {
            System.out.println(e.toString());
        }

        return null;
    }

    /**
     * 创建token
     *
     * JWT由三部分组成：头部、载荷与签名。
     *
     * 头部（Header）
     * 头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个JSON对象。
     * {"typ":"JWT","alg":"HS256"}
     * 在头部指明了签名算法是HS256算法。 我们进行BASE64编码（编码不是加密）
     *
     * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
     *
     * 载荷（playload）
     * 载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分
     * 标准中注册的声明（建议但不强制使用）
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * {"sub":"1234567890","name":"John Doe","admin":true}
     *
     * 然后将其进行base64编码，得到Jwt的第二部分。
     * eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
     *
     * 签证（signature）
     * jwt的第三部分是一个签证信息，这个签证信息由三部分组成：header (base64后的)、payload (base64后的)、secret(盐，加盐加密)
     * 这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。
     * secret:
     * TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
     *
     * 将这三部分用.连接成一个完整的字符串,构成了最终的jwt:
     * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
     * 注意：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     *
     * @param key
     * @param id
     * @param subject
     * @param issuer
     * @param claims
     * @return
     */
    public static String signJwt(Key key, String id, String subject, String issuer, Map<String, Object> claims) {
        //定义jwt签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //当前时间
        long nowMillis = System.currentTimeMillis();
        //将当前时间转换日期类型
        Date now = new Date(nowMillis);
        //将当前时间+超时时间
        long expMillis = nowMillis + JWT_TTL;
        //将时间定义为date类型
        Date expDate = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                // 唯一的ID
                .setId(id)
                // 主题可以是JSON数据
                .setSubject(subject)
                // 签发者
                .setIssuer(issuer)
                // 签发时间
                .setIssuedAt(now)
                // 使用HS256对称加密算法签名, 第二个参数为秘钥
                .signWith(signatureAlgorithm, key)
                // 设置过期时间
                .setExpiration(expDate)
                // 自定义添加信息
                .setClaims(claims);
        //.claim("role","admin");

        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param key
     * @param jwt
     */
    public static Claims parserJwt(Key key, String jwt) {
        return Jwts.parser()
                // 设置公匙
                .setSigningKey(key)
                // 解析jwt
                .parseClaimsJws(jwt)
                // 获取解析类
                .getBody();
    }

    /**
     * 生成签名
     *
     * @param username
     * @param password
     * @return
     */
    public static String signJwt(String username, String password) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + JWT_TTL);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName", username)
                    .withClaim("pwd", password)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

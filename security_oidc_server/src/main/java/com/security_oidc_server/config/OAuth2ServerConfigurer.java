package com.security_oidc_server.config;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.security_oidc_server.service.SecurityService;
import com.security_oidc_server.infrastructure.oidc.OIDCAccessTokenConverter;
import com.security_oidc_server.infrastructure.oidc.OIDCJwtAccessTokenConverter;
import com.security_oidc_server.infrastructure.oidc.OIDCUserAuthenticationConverter;
import com.security_oidc_server.service.OauthService;
import com.security_oidc_server.service.oauth.CustomJdbcClientDetailsService;
import com.security_oidc_server.service.oauth.OauthUserApprovalHandler;
import com.security_oidc_server.service.oauth.SOSAuthorizationCodeServices;
import com.security_oidc_server.Constants;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.keys.RsaKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * OAuth2 config
 *
 * @author ??????????????????-??????
 * @site http://www.cr121.com/
 * @company ?????????????????????????????????????????????
 * @create 2021-03-02 9:09
 */
@Configuration
public class OAuth2ServerConfigurer implements Constants {


    /**
     * // unity resource
     * unity ?????? ???????????????
     */
    @Configuration
    @EnableResourceServer
    protected static class UnityResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().antMatchers("/unity/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/unity/**").access("#oauth2.hasScope('openid') and hasRole('ROLE_UNITY')");

        }

    }


    /**
     * // mobile resource
     * mobile ?????? ???????????????
     */
    @Configuration
    @EnableResourceServer
    protected static class MobileResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().antMatchers("/mobile/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/mobile/**").access("#oauth2.hasScope('openid') and hasRole('ROLE_MOBILE')");

        }

    }

    /**
     * // crcc resource
     * test ?????? ???????????????
     */
    @Configuration
    @EnableResourceServer
    protected static class TestOidcResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().antMatchers("/crcc/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/crcc/**").access("#oauth2.hasScope('openid') and hasRole('ROLE_CRCC')");

        }

    }

    /**
     * ???????????????????????? ?????? ????????????
     */
    @Configuration
    @EnableResourceServer
    protected static class DefaultResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().antMatchers("/api/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/**")
                    .access("#oauth2.hasScope('openid') or #oauth2.hasScope('read') and hasRole('ROLE_USER')");

        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


        @Autowired
        private TokenStore tokenStore;


        @Autowired
        private ClientDetailsService clientDetailsService;


        @Autowired
        private OauthService oauthService;


        @Autowired
        private AuthorizationCodeServices authorizationCodeServices;


        @Autowired
        private SecurityService userDetailsService;


        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            clients.withClientDetails(clientDetailsService);
        }


        /**
         * JwtAccessTokenConverter config
         *
         * @return JwtAccessTokenConverter
         * @throws Exception e
         * @since 1.1.0
         */
        @Bean
        public JwtAccessTokenConverter accessTokenConverter() throws Exception {
            PublicJsonWebKey publicJsonWebKey = publicJsonWebKey();
            OIDCJwtAccessTokenConverter accessTokenConverter = new OIDCJwtAccessTokenConverter(publicJsonWebKey);
//            System.out.println("Key:\n" + accessTokenConverter.getKey());

            OIDCAccessTokenConverter tokenConverter = new OIDCAccessTokenConverter();
            OIDCUserAuthenticationConverter userTokenConverter = new OIDCUserAuthenticationConverter();
            userTokenConverter.setUserDetailsService(this.userDetailsService);
            tokenConverter.setUserTokenConverter(userTokenConverter);
            accessTokenConverter.setAccessTokenConverter(tokenConverter);

            return accessTokenConverter;
        }


        /**
         * JWK set
         *
         * @return JsonWebKeySet
         * @throws Exception e
         * @since 1.1.0
         */
        @Bean
        public JsonWebKeySet jsonWebKeySet() throws Exception {
            //?????? keystore????????????
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(KEYSTORE_NAME)) {
                String keyJson = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
                return new JsonWebKeySet(keyJson);
            }
        }

        /**
         * Only  use one key
         *
         * @return RsaJsonWebKey
         * @throws Exception e
         * @since 1.1.0
         */
        @Bean
        public RsaJsonWebKey publicJsonWebKey() throws Exception {
            JsonWebKeySet jsonWebKeySet = jsonWebKeySet();
            return (RsaJsonWebKey) jsonWebKeySet.findJsonWebKey(DEFAULT_KEY_ID, RsaKeyUtil.RSA, USE_SIG, OIDC_ALG);
        }
//
        @Bean
        public TokenStore tokenStore(DataSource dataSource) throws Exception {
//            return new CustomJdbcTokenStore(dataSource);
            return new JwtTokenStore(accessTokenConverter());
        }

        /**
         * Redis TokenStore (???Redis???????????????)
         */
//        @Bean
//        public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
//            final RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
//            //prefix
//            redisTokenStore.setPrefix(RESOURCE_ID);
//            return redisTokenStore;
//        }
        @Bean
        public ClientDetailsService clientDetailsService(DataSource dataSource) {
            return new CustomJdbcClientDetailsService(dataSource);
        }


        @Bean
        public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
            return new SOSAuthorizationCodeServices(dataSource);
        }


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.accessTokenConverter(accessTokenConverter());

            endpoints.tokenStore(tokenStore)
                    .authorizationCodeServices(authorizationCodeServices)
                    .userDetailsService(userDetailsService)
                    .userApprovalHandler(userApprovalHandler())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //?????????SSL????????????????????????
//            oauthServer.sslOnly();
            // /oauth/token_key ??? /oauth/check_token ?????????????????????
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");

            oauthServer.realm("OIDC")
                    // ?????? client_credentials ?????????
                    .allowFormAuthenticationForClients();

            //????????? clientSecret  passwordEncoder ??????
            //oauthServer.passwordEncoder(new xxx);
        }

        @Bean
        public OAuth2RequestFactory oAuth2RequestFactory() {
            return new DefaultOAuth2RequestFactory(clientDetailsService);
        }


        @Bean
        public UserApprovalHandler userApprovalHandler() {
            OauthUserApprovalHandler userApprovalHandler = new OauthUserApprovalHandler();
            userApprovalHandler.setOauthService(oauthService);
            userApprovalHandler.setTokenStore(tokenStore);
            userApprovalHandler.setClientDetailsService(this.clientDetailsService);
            userApprovalHandler.setRequestFactory(oAuth2RequestFactory());
            return userApprovalHandler;
        }

    }


}

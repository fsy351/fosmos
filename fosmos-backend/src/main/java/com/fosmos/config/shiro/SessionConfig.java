package com.fosmos.config.shiro;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SessionConfig {

    @Bean
    public JavaUuidSessionIdGenerator uuidSessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public EnterpriseCacheSessionDAO cacheSessionDAO(JavaUuidSessionIdGenerator generator ){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-session-cache");
        sessionDAO.setSessionIdGenerator(generator);
        return sessionDAO;
    }

    @Bean
    public SimpleCookie jSessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("JSESSIONID");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        return cookie;
    }

    @Bean
    public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager){
        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
        scheduler.setSessionValidationInterval(100000);
        scheduler.setSessionManager(sessionManager);
        return scheduler;
    }

    @Bean
    public DefaultWebSessionManager defaultSessionManager(EnterpriseCacheSessionDAO sessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000000);
        sessionManager.setDeleteInvalidSessions(true);
        //sessionManager.setSessionValidationScheduler(scheduler);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(jSessionIdCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

}

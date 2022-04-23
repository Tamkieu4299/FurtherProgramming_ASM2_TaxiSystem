package com.asm2.taxisys.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

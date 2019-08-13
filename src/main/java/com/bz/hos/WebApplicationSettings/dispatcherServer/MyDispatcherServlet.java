package com.bz.hos.WebApplicationSettings.dispatcherServer;

import com.bz.hos.WebApplicationSettings.config.MvcConfiguration;
import com.bz.hos.WebApplicationSettings.config.RootConfiguration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class MyDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings()  {
        return new String[] { "/", "/list", "/newOrder", "/report", "/account-management", "/support" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new CharacterEncodingFilter("UTF-8", true),
        };
    }

}

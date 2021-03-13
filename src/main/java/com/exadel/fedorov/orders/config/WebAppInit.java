package com.exadel.fedorov.orders.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInit implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApiConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}


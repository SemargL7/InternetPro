package com.finalproject.internetpro.listener;

import com.finalproject.internetpro.controller.MyServlet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MyHttpSessionAttributeListener  implements HttpSessionAttributeListener {
    private static final Logger logger = Logger.getLogger(MyServlet.class);
    public MyHttpSessionAttributeListener() {}

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        String attributeName = httpSessionBindingEvent.getName();
        Object attributeValue = httpSessionBindingEvent.getValue();
        logger.info("Attribute has been added"+"|Attribute Name ::" + attributeName + "|Attribute Value ::" + attributeValue.toString());
    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        String attributeName = httpSessionBindingEvent.getName();
        Object attributeValue = httpSessionBindingEvent.getValue();
        logger.info("Attribute has been removed"+"|Attribute Name ::" + attributeName + "|Attribute Value ::" + attributeValue.toString());
    }
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        String attributeName = httpSessionBindingEvent.getName();
        Object attributeValue = httpSessionBindingEvent.getValue();
        logger.info("Attribute has been replaced"+"|Attribute Name ::" + attributeName + "|Attribute Value ::" + attributeValue.toString());
    }
}
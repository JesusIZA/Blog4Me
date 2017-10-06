package ua.jrc.db.domain;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class StartDB {
    public static ClassPathXmlApplicationContext getContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        return context;
    }
}

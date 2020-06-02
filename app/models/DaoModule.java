package models;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class DaoModule extends AbstractModule {
    protected void configure() {
        bind(UserDao.class).annotatedWith(Names.named("userDao")).to(MySQLUserDao.class);
    }
}
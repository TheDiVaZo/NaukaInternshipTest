package me.thedivazo.nauka.internship.config;

import java.util.Locale;

/**
 * @author TheDiVaZo
 * created on 22.10.2024
 */
public class EnvConfig implements DBConfig {
    public static final String BASE_TYPE_PATH = "basetype";
    public static final String HOST_PATH = "host";
    public static final String PORT_PATH = "port";
    public static final String DATABASE_PATH = "database";
    public static final String USERNAME_PATH = "dbusername";
    public static final String PASSWORD_PATH = "password";

    public EnvConfig() {}

    @Override
    public BaseType getBaseType() {
        return BaseType.valueOf(System.getenv(BASE_TYPE_PATH).toUpperCase(Locale.ROOT));
    }

    @Override
    public String host() {
        return System.getenv(HOST_PATH);
    }

    @Override
    public String port() {
        return System.getenv(PORT_PATH);
    }

    @Override
    public String database() {
        return System.getenv(DATABASE_PATH);
    }

    @Override
    public String username() {
        return System.getenv(USERNAME_PATH);
    }

    @Override
    public String password() {
        return System.getenv(PASSWORD_PATH);
    }
}

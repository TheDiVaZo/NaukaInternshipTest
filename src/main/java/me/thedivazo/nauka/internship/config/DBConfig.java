package me.thedivazo.nauka.internship.config;

import java.util.Locale;

public interface DBConfig {
    BaseType getBaseType();
    String host();
    String port();
    String database();
    String username();
    String password();

    enum BaseType {
        MYSQL, LOCAL
    }
}

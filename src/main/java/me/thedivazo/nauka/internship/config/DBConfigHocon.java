package me.thedivazo.nauka.internship.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Locale;

/**
 * @author TheDiVaZo
 * created on 15.10.2024
 */
public final class DBConfigHocon implements DBConfig {
    private final String sqlbasetypePath = "sqlbasetype";
    private final String hostPath = "host";
    private final String portPath = "port";
    private final String databasePath = "database";
    private final String usernamePath = "username";
    private final String passwordPath = "password";

    private final Config config;

    public DBConfigHocon(String name) {
        this.config = ConfigFactory.load(name);
    }


    @Override
    public BaseType getBaseType() {
        return DBConfig.BaseType.valueOf(config.getString(sqlbasetypePath));
    }

    @Override
    public String host() {
        return config.getString(hostPath);
    }

    @Override
    public String port() {
        return config.getString(portPath);
    }

    @Override
    public String database() {
        return config.getString(databasePath);
    }

    @Override
    public String username() {
        return config.getString(usernamePath);
    }

    @Override
    public String password() {
        return config.getString(passwordPath);
    }
}

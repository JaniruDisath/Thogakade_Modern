package util;


import db.DbLogin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = DbConfig.class.getResourceAsStream("/config/db.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                DbLogin.askForCredentials();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String URL = props.getProperty("db.url");
    public static final String DB_NAME = props.getProperty("db.name");
    public static final String USER = props.getProperty("db.user");
    public static final String PASSWORD = props.getProperty("db.password");
}

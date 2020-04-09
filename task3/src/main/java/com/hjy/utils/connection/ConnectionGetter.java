package com.hjy.utils.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author huangjinyong
 */
public class ConnectionGetter {
    private static Connection connection;
    private static String url;
    private static String username;
    private static String password;
    static {
        InputStream is = ConnectionGetter.class.getClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            String driver = prop.getProperty("Driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            Class.forName(driver);

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            connection=DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

package db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DbLogin {

    private static final String CONFIG_FILE = "src/main/resources/config/db.properties";
    private static final Properties props = new Properties();

    // Load existing properties if available
    static {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            System.out.println("No saved db.properties found. Will ask for credentials.");
        }
    }

    public static Connection getDatabaseConnection() throws SQLException {
        String url = props.getProperty("db.url")+ props.getProperty("db.name");

        while (true) {
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // If no saved credentials, ask the user
            if (user == null || password == null) {
                //askForCredentials();
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            }

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Database connection successful!");
                return conn;
            } catch (SQLException ex) {
                System.out.println("❌ Connection failed! Wrong username/password?");
                askForCredentials();
            }
        }
    }

    public static Connection getServerConnection() throws SQLException {
        String url = "";

        while (true) {
            url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // If no saved credentials, ask the user
            if (user == null || password == null ||url==null) {
                askForCredentials();
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            }

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Database connection successful!");
                return conn;
            } catch (SQLException ex) {
                System.out.println("❌ Connection failed! Wrong username/password?");
                askForCredentials();
            }
        }
    }

    public static void askForCredentials() {
        String url = props.getProperty("db.url");
        if (url==null){
            url = getUrl();
        }
        String name = props.getProperty("db.name");
        if (name==null){
            name=getUser();
        }
        String password = props.getProperty("db.name");
        if (password==null){
            password=getPassword();
        }

        props.setProperty("db.url", url);
        props.setProperty("db.name", "thogakade_modern");
        props.setProperty("db.user", name);
        props.setProperty("db.password", password);

        // Path inside resources/config/
        String CONFIG_FILE = "src/main/resources/config/db.properties";

        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "Database credentials");
            System.out.println("✅ Credentials saved to " + CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUrl(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL Port Number : ");
        return "jdbc:mysql://localhost:"+scanner.nextLine()+"/";
    }
    private static String getUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL username: ");
        return scanner.nextLine();
    }
    private static String getPassword(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL password: ");
        return scanner.nextLine();
    }

}

package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static Connection connection = null;
    private static Util instance = null;
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    private Util() {
        try {
            Properties props = getProps();
            connection = DriverManager.getConnection(
                    props.getProperty("db.url"),      // Use correct key
                    props.getProperty("db.username"), // Use correct key
                    props.getProperty("db.password")  // Use correct key
            );
        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
        }
    }

    public static synchronized Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing connection", e);
            }
        }
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))) {
            props.load(in);
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
        return props;
    }
}

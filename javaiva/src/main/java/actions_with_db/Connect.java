package actions_with_db;

import java.sql.*;

public class Connect {
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "12345pdf";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private static Connection connection;

    public Connect() throws SQLException, ClassNotFoundException {
        connection = createConnection();
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        return connection;
    }

    public void dropAndCreateTable(String request) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(request);

    }
    
    public void update(String request) throws SQLException {
        System.out.println(request);
        connection.createStatement().executeUpdate(request);
    }

    
    public static ResultSet getResultSet(String request) throws SQLException {
        return connection.createStatement().executeQuery(request);
    }

}

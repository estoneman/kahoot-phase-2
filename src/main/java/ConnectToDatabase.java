//https://www.tutorialspoint.com/jdbc/jdbc-create-database.htm

import java.sql.Connection;
import java.sql.DriverManager;

class ConnectToDatabase {

//    private static final String CONNECTION_ESTABLISHED = "Connection established . . .";
//    private static final String CONNECTON_FAILED = "Connection failed";
    private static final String JDBC_PACKAGE = "com.mysql.cj.jdbc.Driver";
    private static final String MySQLPollURL = "jdbc:mysql://192.168.0.21:3306/poll";
    private static final String USER = "MySQLAdmin2";
    private static final String PASS = "19Soccer99)";

    static Connection connectToPollDB() {
        Connection connection = null;

        try {
            //Registering the Driver
            Class.forName(JDBC_PACKAGE);
            //Getting the connection
            connection = DriverManager.getConnection(MySQLPollURL, USER, PASS);
        }
        catch (Exception e) {
//            System.out.println(CONNECTON_FAILED);
            e.printStackTrace();
        }

//        System.out.println(CONNECTION_ESTABLISHED);
        return connection;
    }

}

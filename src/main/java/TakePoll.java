import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.Scanner;

public class TakePoll {

    private static Connection findDB() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;

        String input;

        try {
            connection = ConnectToDatabase.connectToPollDB();
            ResultSet rs;

            System.out.println("Enter the name of the poll creator to search for poll");
            input = keyboard.nextLine().toLowerCase().trim();

            rs = connection.getMetaData().getCatalogs();
            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (input.equals(catalogs)) {
                    connection = ConnectToDatabase.connectToDB(input);
                    return connection;
                } else {
                    System.out.println("Poll creator not found.");
                }
            }
        } catch (SQLException sQLE) {
            sQLE.printStackTrace();
        }
        return connection;
    }

    private static String findPoll() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection;

        String pollName = "";
        String input;

        if (findDB() != null) {
            try {
                connection = findDB();
                while (true) {
                    System.out.println("Please enter the poll identifier:");
                    input = keyboard.nextLine().toLowerCase().trim();

                    ResultSet tables = connection.getMetaData().getTables(null, null, input, null);
                    if (tables.next()) {
                        System.out.println("Poll found.");
                        pollName = input;
                        return pollName;
                    } else {
                        System.out.println("Poll does not exist.");
                    }
                }
            } catch (SQLException sQLE) {
                sQLE.printStackTrace();
            }
        }
        return pollName;
    }

    public static void takePoll() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;
        Statement statement = null;

        String pollName;
        String pollTaker;
        String input;

        if (!findPoll().equals("")) {
            try {
                connection = findDB();
                pollName = findPoll();

                System.out.println("Enter your name to start " + pollName);
                pollTaker = keyboard.nextLine();

                statement = connection.createStatement();

                //Create table to store individual results of poll
                //Referenced https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm
                String sql = "CREATE TABLE " + pollName + "_" + pollTaker + " " +
                        "(QuestionNumber INTEGER, " +
                        "Response, varchar(100), " +
                        "PRIMARY KEY ( QuestionNumber ))";
                statement.executeUpdate(sql);

            } catch (SQLException sQLE) {
                sQLE.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null) {
                        connection.close();
                    } } catch (SQLException se) {
                    }
                try {
                    if (connection != null) {
                        connection.close();
                    } } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
}
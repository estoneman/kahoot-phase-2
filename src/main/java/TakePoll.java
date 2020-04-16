import java.sql.*;
import java.util.Scanner;

public class TakePoll {

    private static Connection findDB() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;

        String input;

        try {
            connection = SQLInstructions.connectToPollDB();
            ResultSet rs;

            System.out.println("Enter the name of the poll creator to search for poll");
            input = keyboard.nextLine().toLowerCase().trim();

            //Check if database exists
            //Adapted from https://www.javaartifacts.com/check-if-database-exists-using-java/
            rs = connection.getMetaData().getCatalogs();
            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (input.equals(catalogs)) {
                    connection = SQLInstructions.connectToDB(input);
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

                    //Check if poll exists in selected database
                    //Adapted from https://www.rgagnon.com/javadetails/java-0485.html
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
        String individualResults;

        if (!findPoll().equals("")) {
            try {
                connection = findDB();
                pollName = findPoll();

                System.out.println("Enter your name to start " + pollName);
                pollTaker = keyboard.nextLine();
                individualResults = pollName + "_" + pollTaker;

                statement = connection.createStatement();

                //Create table to store individual results of poll
                //Referenced https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm
                String sql = "CREATE TABLE " + individualResults + " " +
                        "(QuestionNumber INTEGER, " +
                        "Response, varchar(100), " +
                        "PRIMARY KEY ( QuestionNumber ))";
                statement.executeUpdate(sql);

                String readQuestions = ("SELECT * FROM " + pollName);

                ResultSet rs = statement.executeQuery(readQuestions);

                while (rs.next()) {

                    int number = rs.getInt("num");
                    String type = rs.getString("qtype");
                    String options = rs.getString("options");
                    String question = rs.getString("question");

                    System.out.println("Number: " + number + "\nType: " + type + "\nOptions: " + options + "\nQuestion: " + question);
                    System.out.println("*------------------*");

                    String answer = keyboard.nextLine();
                    sql =   "INSERT INTO " + individualResults +
                            " VALUES(" + number + ", '" + answer + "')";
                    statement.executeUpdate(sql);
                }

                System.out.println("Your answers were recorded.");
                
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
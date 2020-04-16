import java.sql.*;
import java.util.Scanner;

public class TakePoll {

    public static void main(String[] args) {
        takePoll();
    }

    private static String findDB() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;

        String input;

        try {
            connection = SQLInstructions.connectToSQL();
            ResultSet rs;

            System.out.println("Enter the name of the poll creator to search for poll");
            input = keyboard.nextLine().toLowerCase().trim();

            //Check if database exists
            //Adapted from https://www.javaartifacts.com/check-if-database-exists-using-java/
            rs = connection.getMetaData().getCatalogs();
            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (input.equals(catalogs)) {
                    return input;
                } else {
                    System.out.println("Poll creator not found.");
                }
            }
        } catch (SQLException sQLE) {
            sQLE.printStackTrace();
        }
        return "";
    }

    private static String findPoll(String dBName) {
        Scanner keyboard = new Scanner(System.in);
        Connection connection;

        String pollName = "";
        String input;

        try {
            connection = SQLInstructions.connectToPollDB(dBName);
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

        return pollName;
    }

    public static void takePoll() {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;
        Statement connectionStatement = null;

        String dBName = findDB();
        String pollName = findPoll(dBName);
        String pollTaker;
        String individualResults;

        if (!pollName.equals("")) {
            try {
                connection = SQLInstructions.connectToPollDB(dBName);

                System.out.println("Enter your name to start " + pollName);
                pollTaker = keyboard.nextLine();
                individualResults = pollName + "_" + pollTaker;

                connectionStatement = connection.createStatement();

                //Create table to store individual results of poll
                //Referenced https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm
                String sql = "CREATE TABLE " + individualResults + " " +
                        "(QuestionNumber INTEGER, " +
                        "Response varchar(100), " +
                        "PRIMARY KEY ( QuestionNumber ))";
                connectionStatement.executeUpdate(sql);

                String readQuestions = ("SELECT * FROM " + pollName);

                connectionStatement = connection.createStatement();

                ResultSet resultSet = connectionStatement.executeQuery(readQuestions);

                while (resultSet.next()) {

                    connectionStatement = connection.createStatement();

                    int number = resultSet.getInt("QuestionNumber");
                    String options = resultSet.getString("Options");
                    String question = resultSet.getString("Question");

                    System.out.println("Number: " + number + "\nOptions: " + options + "\nQuestion: " + question);
                    System.out.println("*------------------*");

                    System.out.println("enter answer here: ");
                    String answer = keyboard.nextLine();
                    sql =   "INSERT INTO " + individualResults +
                            " VALUES(" + number + ", '" + answer + "')";
                    connectionStatement.executeUpdate(sql);
                }

                System.out.println("Your answers were recorded.");
                
            } catch (SQLException sQLE) {
                sQLE.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (connectionStatement != null) {
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
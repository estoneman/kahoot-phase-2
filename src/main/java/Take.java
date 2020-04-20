import java.sql.*;
import java.util.Scanner;

public class Take extends ReadSQLServer {

    public static void main(String[] args) {
        take("Quiz"); //qp is quiz or poll. Just changes the printed statements to match
    }

    public static void take(String qp) {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;
        Statement connectionStatement = null;

        String dBName = ReadSQLServer.findDB(qp);
        String tableName = findTable(dBName, qp);
        String taker;
        String individualResults;
        boolean tableExists = false;
        String sql;

        if (!tableName.equals("")) {
            try {
                connection = SQLInstructions.connectToDB(dBName);

                System.out.println("Enter your name to start " + tableName);
                taker = keyboard.nextLine();
                individualResults = tableName + "_" + taker;

                connectionStatement = connection.createStatement();

                if (Check.tableExists(individualResults,dBName)) {
                    tableExists = true;
                } else {
                    //Create table to store individual results of poll
                    //Referenced https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm
                    sql = "CREATE TABLE " + individualResults + " " +
                            "(QuestionNumber INTEGER, " +
                            "Response varchar(100), " +
                            "PRIMARY KEY ( QuestionNumber ))";
                    connectionStatement.executeUpdate(sql);
                }

                String readQuestions = ("SELECT * FROM " + tableName);

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
                    if (!tableExists) {
                        sql = "INSERT INTO " + individualResults +
                                " VALUES(" + number + ", '" + answer + "')";
                    } else {
                        sql = "UPDATE " + individualResults +
                                "SET Response = " + answer +
                                "WHERE QuestionNumber = " + number;
                    }
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
//https://www.tutorialspoint.com/jdbc/jdbc-select-records.htm

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class ReadSQLServer {

    protected static String findDB(String qp) {
        Scanner keyboard = new Scanner(System.in);
        Connection connection = null;

        String input;

        try {
            connection = SQLInstructions.connectToSQL();
            ResultSet rs;

            System.out.println("Enter the name of the " + qp.toLowerCase() + " creator to search for " + qp.toLowerCase());
            input = keyboard.nextLine().toLowerCase().trim();

            //Check if database exists
            //Adapted from https://www.javaartifacts.com/check-if-database-exists-using-java/
            if (Check.dBExists(input)) {
                return input;
            }
            else {
                System.out.println("Creator not found");
            }

            /*rs = connection.getMetaData().getCatalogs();
            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (input.equals(catalogs)) {
                    return input;
                } else {
                    System.out.println("Creator not found.");
                }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String findTable(String dBName, String qp) {
        Scanner keyboard = new Scanner(System.in);
        Connection connection;

        String tableName = "";
        String input;

        try {
            connection = SQLInstructions.connectToDB(dBName);
            while (true) {
                System.out.println("Please enter the " + qp.toLowerCase() + " identifier:");
                input = keyboard.nextLine().toLowerCase().trim();

                if (Check.tableExists(input, dBName)) {
                    tableName = input;
                    return tableName;
                }

                //Check if table exists in selected database
                //Adapted from https://www.rgagnon.com/javadetails/java-0485.html
               /* ResultSet tables = connection.getMetaData().getTables(null, null, input, null);
                if (tables.next()) {
                    System.out.println(qp + " found.");
                    tableName = input;
                    return tableName;
                } else {
                    System.out.println(qp + " does not exist.");
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableName;
    }

    public static String get(String s, String dBName, String table, int number ) {
        String genericString = "";

        try {
            Connection connection = null;
            Statement stmt = null;
            ResultSet rs;

            connection = SQLInstructions.connectToDB(dBName);

            String sql = "SELECT " + s + " FROM " + table + "WHERE " + " QuestionNumber = " + number;

            rs = stmt.executeQuery(sql);
            s = rs.getString(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return genericString;
    }

    public static String getQuestion(String dBName, String table, int number) {
        return get("Question", dBName, table, number);
    }

    public static String getQuestionType(String dBName, String table, int number) {
        return get("QuestionType", dBName, table, number);
    }

    public static String[] getOptions(String dBName, String table, int number) {
        return get("Options", dBName, table, number).split(":");
    }

    public static String getAnswer(String dBName, String table, int number) {
        return get("Answer", dBName, table, number);
    }
}
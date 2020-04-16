//https://www.tutorialspoint.com/jdbc/jdbc-select-records.htm

//import java.sql.Connection;
//import java.sql.Statement;
//import java.sql.ResultSet;
//import java.util.Scanner;
//
//class ReadSQLServer {
//
//    private static final String READ_SUCCESS = "Read successfully";
//    private static final String READ_FAILURE = "Read failed";
//
//    static void run() {
//        try {
//            readPollQuestions();
//            readPollResults();
//        }
//        catch (Exception e) {
//            System.out.println(READ_FAILURE);
//            e.printStackTrace();
//        }
//    }
//
//    private static void readPollResults() {
//        Statement statement;
//        Connection connection;
//
//        try {
//
//            System.out.println("Reading polling results . . .");
//
//            connection = SQLInstructions.connectToPollDB();
//
//            //change to connect to specific database
//            connection = SQLInstructions.connectToSQL();
//
//            statement = connection.createStatement();
//
//            Scanner keyboard = new Scanner(System.in);
//
//            System.out.println("Enter a name to search for their results");
//            String name = keyboard.nextLine().toLowerCase().trim();
//
//            String sqlInstructions = ("SELECT * FROM poll_results " +
//                    "WHERE taker = \"" + name + "\";");
//            ResultSet rs = statement.executeQuery(sqlInstructions);
//
//            boolean found = false;
//
//            while (rs.next()) {
//                found = true;
//                int number = rs.getInt("num");
//                String type = rs.getString("question");
//                String options = rs.getString("response");
//
//                System.out.println("Number: " + number + "\nType: " + type + "\nOptions: " + options);
//                System.out.println("*------------------*");
//            }
//
//            if (!found)
//                System.out.println(name + " is not in this poll results database");
//        }
//
//        catch (Exception e) {
//            System.out.println(READ_FAILURE);
//            e.printStackTrace();
//        }
//
//        System.out.println(READ_SUCCESS);
//    }
//
//    private static void readPollQuestions() {
//        Statement statement;
//        Connection connection;
//
//        try {
//
//            System.out.println("Reading poll questions . . .");
//
//<<<<<<< HEAD
//            connection = SQLInstructions.connectToPollDB();
//=======
//            //change to connect to specific database
//            connection = SQLInstructions.connectToSQL();
//>>>>>>> upstream/master
//
//            Scanner keyboard = new Scanner(System.in);
//
//            System.out.println("Enter a name to search for in the database: ");
//            String name = keyboard.nextLine().toLowerCase().trim();
//
//            statement = connection.createStatement();
//            String sqlInstructions = ("SELECT * FROM poll_questions " +
//                    "WHERE creator = \"" + name + "\";");
//
//            ResultSet rs = statement.executeQuery(sqlInstructions);
//
//            boolean found = false;
//
//            while (rs.next()) {
//                found = true;
//
//                int number = rs.getInt("num");
//                String type = rs.getString("qtype");
//                String options = rs.getString("options");
//                String question = rs.getString("question");
//
//                System.out.println("Number: " + number + "\nType: " + type + "\nOptions: " + options + "\nQuestion: " + question);
//                System.out.println("*------------------*");
//            }
//
//            if (!found)
//                System.out.println(name + " is not in poll questions database");
//
//        }
//
//        catch (Exception e) {
//            System.out.println(READ_FAILURE);
//            e.printStackTrace();
//        }
//        System.out.println(READ_SUCCESS);
//    }
//
////    private static void readDefaultPoll() {
////        Statement statement;
////        Connection connection;
////
////        try {
////            Class.forName(JDBC_PACKAGE);
////            connection = DriverManager.getConnection(MySQLURL, USER, PASS);
////
////            statement = connection.createStatement();
////            String sql = ("SELECT * FROM defaultPoll");
////            ResultSet rs = statement.executeQuery(sql);
////
////            while (rs.next()) {
////                int number = rs.getInt("Num");
////                String type = rs.getString("qType");
////                String options = rs.getString("Op");
////
////                System.out.println("Number: " + number + "\nType: " + type + "\nOptions: " + options);
////                System.out.println("*------------------*");
////            }
////        }
////        catch (Exception e) {
////            e.printStackTrace();
////        }
////        System.out.println("Goodbye!");
////    }
//}
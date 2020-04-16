
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;

class WriteToSQLServer {

    private static final String WRITTEN_SUCCESS = "Written successfully";
    private static final String WRITTEN_FAILURE = "Write failed";

    static void writePollQuestions(String pollName, String dBName) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Scanner keyboard = new Scanner(System.in);

            //connect to database
            connection = SQLInstructions.connectToPollDB(dBName);

            int number = 1;//keeps track of question number

            //continuously adds question to the poll until user stops
            while (true) {
                System.out.println("Enter question " + number + " or enter 'done' when you are finished adding questions: ");
                String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

                if (question.equals("done"))
                    break;//loop condition

                //non-empty, non fully numerical input
                while (!Check.isValidNonNumericalInput(question)) {
                    System.out.println("invalid input");
                    question = keyboard.nextLine().toLowerCase().trim();
                }

                int optionNumber = 1;
                String optionsString = "";

                while (true) {
                    System.out.println("Enter option " + optionNumber + " or enter 'done' to stop adding options: ");
                    String option = keyboard.nextLine().toLowerCase().trim();

                    if (option.equals("done"))
                        break;

                    //non-empty, non fully numerical input
                    while (!Check.isValidNonNumericalInput(option)) {
                        System.out.println("invalid input, try again: ");
                        option = keyboard.nextLine().toLowerCase().trim();
                    }

                    //system for adding a string of all options delimited by a ":"
                    optionsString += option + ":";
                    optionNumber++;
                }

                optionsString = optionsString.substring(0, optionsString.length() - 1);

                String sql = "INSERT INTO " + dBName + "." + pollName + " (Question, `Options`) VALUES (?, ?)";

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, question);
                preparedStatement.setString(2, optionsString);

                preparedStatement.executeUpdate();

                number++;

            }



            //success message to be output to terminal
            System.out.println("POLL QUESTIONS " + WRITTEN_SUCCESS);
        }
        //for catching any issues with connectivity with MySQL server
        catch (SQLException sQLE) {
            System.out.println(WRITTEN_FAILURE);
            sQLE.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    //closes connection
                    connection.close();
                }
                if (preparedStatement != null) {
                    //closes statement
                    preparedStatement.close();
                }
            }
            catch (SQLException sQLE) {
                sQLE.printStackTrace();
            }
        }

    }

}

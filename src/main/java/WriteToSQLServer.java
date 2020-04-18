
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
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
            connection = SQLInstructions.connectToDB(dBName);

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

    @SuppressWarnings("unchecked")
    static void writeMultipleChoice(String pollName, String dBName, String type) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner keyboard = new Scanner(System.in);

        try {
            //connect to given database
            connection = SQLInstructions.connectToDB(dBName);

            //question number
            int number = 1;

            //continuously adds question to quiz until user says so
            while (true) {
                System.out.println("Enter question " + number + " or enter 'done' when you are finished adding questions: ");
                String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

                if (question.equals("done"))
                    break;

                while (!Check.isValidNonNumericalInput(question)) {
                    System.out.println("invalid input, try again: ");
                    question = keyboard.nextLine().toLowerCase().trim();
                }

                //option number
                int optionNumber = 1;

                //string builder
                String optionsString = "";

                while (true) {
                    System.out.println("Enter option " + optionNumber + " or enter 'done' to stop adding options: ");
                    String option = keyboard.nextLine().toLowerCase().trim();

                    if (option.equals("done"))
                        break;

                    while (option.isEmpty()) {
                        System.out.println("options cannot be empty: ");
                        option = keyboard.nextLine().toLowerCase().trim();
                    }

                    optionsString += option + ":";
                    optionNumber++;

                }

                //string that will be added to MySQL database
                optionsString = optionsString.substring(0, optionsString.length() - 1);

                ArrayList<String> optionsList = new ArrayList();

                //adds strings to an array list which will be used to check if provided answer is in the options set
                StringBuilder resultString = new StringBuilder();
                for (int i = 0; i < optionsString.length(); i++) {
                    if (!(optionsString.charAt(i) == ':'))
                        resultString.append(optionsString.charAt(i));
                    else {
                        optionsList.add(resultString.toString());
                        resultString = new StringBuilder();
                    }
                }

                System.out.println("Enter the answer for question " + number + ": ");
                String answer = keyboard.nextLine().toLowerCase().trim();

                //checks if the provided answer is in the options set
                while (!optionsList.contains(answer)) {
                    System.out.println(answer + " is not in the option set, try again: ");
                    answer = keyboard.nextLine().toLowerCase().trim();
                }

                String sqlInstructions = "INSERT INTO " + dBName + "." + pollName + " (QuestionType, question, `Options`, Answer) VALUES (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(sqlInstructions);

                preparedStatement.setString(1, type);
                preparedStatement.setString(2, question);
                preparedStatement.setString(3, optionsString);
                preparedStatement.setString(4, answer);

                preparedStatement.executeUpdate();

                number++;

            }

        }
        catch (SQLException sQLE) {
            System.out.println(WRITTEN_FAILURE);
            sQLE.printStackTrace();
        }
        //closes connection which closes the statement and also handles closing the keyboard input object
        finally {
            try {
                if (connection != null)
                    connection.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException sQLE) {
                System.out.println("cannot close stream properly");
                sQLE.printStackTrace();
            }
        }

    }

    static void writeTrueFalse(String pollName, String dBName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner keyboard = new Scanner(System.in);

        try {
            //connect to given database
            connection = SQLInstructions.connectToDB(dBName);

            //question number
            int number = 1;

            //continuously adds question to quiz until user says so
            while (true) {
                System.out.println("Enter question " + number + " or enter 'done' when you are finished adding questions: ");
                String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

                if (question.equals("done"))
                    break;

                while (!Check.isValidNonNumericalInput(question)) {
                    System.out.println("invalid input, try again: ");
                    question = keyboard.nextLine().toLowerCase().trim();
                }

                System.out.println("Enter the answer for question " + number + ": ");
                String answer = keyboard.nextLine().toLowerCase().trim();

                while (!Check.isValidNonNumericalInput(answer)) {
                    System.out.println("Invalid input, try again: ");
                    answer = keyboard.nextLine().toLowerCase().trim();
                }

                while (!(answer.equals("true") || answer.equals("t")) && !(answer.equals("false") || answer.equals("f"))) {
                    System.out.println("Please type 'true' or 'false', not " + answer + ": ");
                    answer = keyboard.nextLine().toLowerCase().trim();
                }

                String sqlInstructions = "INSERT INTO " + dBName + "." + pollName + " (QuestionType, question, `Options`, Answer) VALUES (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(sqlInstructions);

                preparedStatement.setString(1, "tf");
                preparedStatement.setString(2, question);
                preparedStatement.setString(3, "true:false");
                preparedStatement.setString(4, answer);

                preparedStatement.executeUpdate();

                number++;

            }

        }
        catch (SQLException sQLE) {
            System.out.println(WRITTEN_FAILURE);
            sQLE.printStackTrace();
        }
        //closes connection which closes the statement and also handles closing the keyboard input object
        finally {
            try {
                if (connection != null)
                    connection.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException sQLE) {
                System.out.println("cannot close stream properly");
                sQLE.printStackTrace();
            }
        }
    }

    static void writeMatching(String pollName, String dBName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner keyboard = new Scanner(System.in);

        try {
            //connect to given database
            connection = SQLInstructions.connectToDB(dBName);

            //question number
            int number = 1;

            //continuously adds question to quiz until user says so
            while (true) {
                System.out.println("Enter instructions for question " + number + " or enter 'done' when you are finished adding questions: ");
                String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

                if (question.equals("done"))
                    break;

                while (!Check.isValidNonNumericalInput(question)) {
                    System.out.println("invalid input, try again: ");
                    question = keyboard.nextLine().toLowerCase().trim();
                }

                //option number
                int termNumber = 1;

                //string builder
                String optionsString = "";

                while (true) {
                    System.out.println("Enter term " + termNumber + " or enter 'done' to stop adding options: ");
                    String option = keyboard.nextLine().toLowerCase().trim();

                    if (option.equals("done"))
                        break;

                    while (!Check.isValidNonNumericalInput(option)) {
                        System.out.println("invalid input, try again: ");
                        option = keyboard.nextLine().toLowerCase().trim();
                    }

                    optionsString += option + ":";
                    termNumber++;

                }

                optionsString = optionsString.substring(0, optionsString.length() - 1);

                String[] optionsList = optionsString.split(":");

                String answerString = "";

                for (int i = 0; i < optionsList.length; i++) {
                    System.out.println("Enter match for " + optionsList[i] + ": ");
                    String answer = keyboard.nextLine().toLowerCase().trim();

                    while (!Check.isValidNonNumericalInput(answer)) {
                        System.out.println("invalid input, try again: ");
                        answer = keyboard.nextLine().toLowerCase().trim();
                    }

                    answerString += answer + ":";
                }

                answerString = answerString.substring(0, answerString.length() - 1);

                String sqlInstructions = "INSERT INTO " + dBName + "." + pollName + " (QuestionType, Question, `Options`, Answer) VALUES (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(sqlInstructions);

                preparedStatement.setString(1, "m");
                preparedStatement.setString(2, question);
                preparedStatement.setString(3, optionsString);
                preparedStatement.setString(4, answerString);

                preparedStatement.executeUpdate();

                number++;

            }

        }
        catch (SQLException sQLE) {
            System.out.println(WRITTEN_FAILURE);
            sQLE.printStackTrace();
        }
        //closes connection which closes the statement
        finally {
            try {
                if (connection != null)
                    connection.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException sQLE) {
                System.out.println("cannot close stream properly");
                sQLE.printStackTrace();
            }
        }
    }

}

import java.util.Scanner;

class CreateQuiz {

    private static final Scanner keyboard = new Scanner(System.in);



        //connect to db (name = username)
            //create new if non-existent
        //connect to db.table regardless of question type there will only be one table per quiz which is a collection of all question types (name = quiz name)
            //the user will decide what type to add to the single table with name of the quiz they will create
        //ask which type they want to enter
            //go to the method that handles each question type (tf, mc, fb, m)
            //keep looping until they are done adding questions

            /*
            * fields for true/false, multiple choice: question number, question, options (true/false)
            * fields for fill in the blank: question number, question, options (possible answers)
            * fields for matching: question number, question/instructions, options
            *
            *       options for matching questions
            *           for writing to sql server, write the term and matched term in the following form "option1:match1,option2:match2,...,option(n):match(n)"
            *           in the options field for easy parsing
            *           read each option and match as a key, value pair into a hash map called answerHashMap or some other name to that effect
            *           in order to display only the options create a map iterator to only get the keys which represent the terms that need to be matched
            *           in order to check if the answers are correct, get the key, value pair and compare the values to the user input
            *               if they are equal, they got it correct; else, they got it wrong
            *
            */

            //create new if non-existent (in correct model)

    public static void main(String[] args) {
        generateQuiz();
    }

    static void generateQuiz() {
        System.out.println("Enter your name or any unique identifier: ");
        String unique_identifier = keyboard.nextLine().toLowerCase().trim();

        //check if input is actually valid
        while (!Check.isValidNonNumericalInput(unique_identifier)) {
            System.out.println(unique_identifier + " is an invalid name for database, try again: ");
            unique_identifier = keyboard.nextLine().toLowerCase().trim();
        }

        //if database does not already exist, create a new one
        if (!Check.dBExists(unique_identifier))
            SQLInstructions.createDatabase(unique_identifier);

        System.out.println("Enter the name of the quiz you would like to create: ");
        String tableName = keyboard.nextLine().toLowerCase().trim();

        while (!Check.isValidNonNumericalInput(tableName)) {
            System.out.println(tableName + " is an invalid input, try again: ");
            tableName = keyboard.nextLine().toLowerCase().trim();
        }

        //if poll already exists, continue. if not, create a new one
        if (!Check.tableExists(tableName, unique_identifier))
            SQLInstructions.createQuestionsTable(tableName, unique_identifier);

        while (true) {
            System.out.println("What question type would you like to enter?\ntf = true/false\nmc = multiple choice\nm = matching\nfb = fill in the blank: ");
            System.out.println("Enter 'done' when you are finished adding questions: ");
            String type = keyboard.nextLine().toLowerCase().trim();

            if (type.equals("done"))
                break;

            else {
                if (type.equals("tf"))
                    WriteToSQLServer.writeTrueFalse(tableName, unique_identifier);
                if (type.equals("mc") || type.equals("fb"))
                    WriteToSQLServer.writeMultipleChoice(tableName, unique_identifier, type);
                else if (type.equals("m"))
                    WriteToSQLServer.writeMatching(tableName, unique_identifier);
                else {
                    System.out.println("no question type selected, pick one of the given options (tf, mc, m, fb");
                }
            }

        }
    }

}


import java.util.Scanner;

class CreatePoll {

    //how the user interacts with our program (for now)
    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        generatePoll();
    }

    static void generatePoll() {
        System.out.println("Enter your name or any unique identifier: ");
        String unique_identifier = keyboard.nextLine().toLowerCase().trim();

        //if database does not already exist, create a new one
        if (!Check.dBExists(unique_identifier))
            SQLInstructions.createDatabase(unique_identifier);

        System.out.println("Enter the name of the poll you would like to create: ");
        String pollName = keyboard.nextLine().toLowerCase().trim();

        //if poll already exists, continue. if not, create a new one
        if (!Check.tableExists(pollName, unique_identifier))
            SQLInstructions.createQuestionsTable(pollName, unique_identifier);

        //make poll questionnaire and write to sql server
        WriteToSQLServer.writePollQuestions(pollName, unique_identifier);

    }

}

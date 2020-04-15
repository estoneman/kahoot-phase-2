import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;

public class Client {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);

        String input;

        System.out.println("Would you like to create a quiz or a poll?\nEnter 'q' for quiz and 'p' for polling: ");
        String pollOrQuiz = sc.nextLine().toLowerCase().trim();

        while (!Check.isValidNonNumericalInput(pollOrQuiz)) {
            pollOrQuiz = sc.nextLine().toLowerCase().trim();
        }

        if (pollOrQuiz.equals("p")) {

            CreatePoll.generatePoll();

            System.out.println("Would you like to take the created poll(y/n)? If not, enter 'done' or 'no' to exit: ");
            input = sc.nextLine().toLowerCase().trim();

            while (!Check.isValidNonNumericalInput(input)) {
                input = sc.nextLine().toLowerCase().trim();
            }

            if (input.equals("yes") || input.equals("y")) {
                System.out.println("Enter your name: ");
                String name = sc.nextLine().toLowerCase().trim();

                JSONArray pollResults = Poll.takePoll(name);
                Poll.printJSONArray(pollResults);
            }

        }
        else {
            //Write the quiz
            QuizGenerator.generateQuiz();

            System.out.println("\nEnter your name to take the quiz or enter done to exit:\n");
            input = sc.nextLine();

            if (!input.equals("done")) {

                //If user wants to take the quiz, calls Quiz method
                JSONArray results = Quiz.takeQuiz(input);

                //Calls printResults method in Quiz.java to record user's results
                Quiz.printResults(results, input);
            }
        }
    }
}
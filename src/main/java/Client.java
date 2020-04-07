import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;

public class Client {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);

        String input;

        System.out.println("Would you like to create a quiz or a poll?\n Enter 'q' for quiz and 'p' for polling: ");
        String pollOrQuiz = sc.nextLine().toLowerCase().trim();

        if (pollOrQuiz.equals("p")) {
            PollGenerator.generatePoll();

            System.out.println("Would you like to take the created poll? If not, enter 'done' to exit: ");
            input = sc.nextLine();

            if (!input.equals("done")) {
                JSONArray pollResults = Poll.takePoll();

                Poll.printJSONArray(pollResults);
            }
        }
        else {
            //Write the quiz
//        QuizGenerator quiz = new QuizGenerator();
//        quiz.generateQuiz();
            QuizGenerator.generateQuiz();

            System.out.println("\nEnter your name to take the quiz or enter done to exit:\n");
            input = sc.nextLine();

            if (!input.equals("done")) {

                //If user wants to take the quiz, calls Quiz method
//            Quiz read = new Quiz();
                JSONArray results = Quiz.takeQuiz(input);

                //Calls printResults method in Quiz.java to record user's results
                Quiz.printResults(results, input);
            }
        }
    }
}
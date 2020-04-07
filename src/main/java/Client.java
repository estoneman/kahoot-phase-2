
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;

public class Client {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);

        //Write the quiz
        QuizGenerator quiz = new QuizGenerator();
        quiz.generateQuiz();

        System.out.println("\nEnter your name to take the quiz or enter done to exit:\n");
        String input = sc.nextLine();

        if (!input.equals("done")) {

            //If user wants to take the quiz, calls Quiz method
            Quiz read = new Quiz();
            JSONArray results = read.takeQuiz(input);

            //Calls printResults method in Quiz.java to record user's results
            read.printResults(results, input);
        }
    }
}
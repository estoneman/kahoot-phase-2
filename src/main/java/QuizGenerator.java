import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//Referenced https://howtodoinjava.com/library/json-simple-read-write-json-examples/
public class QuizGenerator
{
    private static Scanner sc = new Scanner(System.in);

    private static JSONObject writeTfQuestion(int number, String question, String tfAnswer){
        JSONObject q = new JSONObject();

        q.put("Number", number);
        q.put("Type", "TF");
        q.put("Question", question);
        q.put("Answer", tfAnswer);
        return q;
    }

    private static JSONObject writeMcQuestion(int number, String question, String MCAnswer, String a, String b, String c, String d) {
        JSONObject q = new JSONObject();

        q.put("Number", number);
        q.put("Type", "Multiple Choice");
        q.put("Question", question);
        q.put("Answer", MCAnswer);

        JSONArray choices = new JSONArray();
        choices.add(a);
        choices.add(b);
        choices.add(c);
        choices.add(d);
        q.put("Choices", choices);
        return q;
    }

    private static JSONObject writeBlankQuestion(int number, String question, String answer){
        JSONObject q = new JSONObject();

        q.put("Number", number);
        q.put("Type", "Fill in the Blank");
        q.put("Question", question);
        q.put("Answer", answer);
        return q;
    }

    //Reads in all required components of question, then calls appropriate method to add that question and answer to JSONArray quiz
    private static JSONArray checkQuestion(int number, JSONArray quiz, String input){

        if (input.equals("tf")) {
            System.out.println("Enter your question:");
            String question = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter the answer, T or F:");
            String answer = sc.nextLine().toLowerCase();

            //Validates user input
            if (!answer.equals("t") && !answer.equals("f")) {
                System.out.println("Not a valid answer. Enter T or F:");
                answer = sc.nextLine().toLowerCase();
            }

            quiz.add(writeTfQuestion(number, question, answer));
        }

        if (input.equals("mc")) {
            System.out.println("Enter your question:");
            String question = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter choice A:");
            String a = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter choice B:");
            String b = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter choice C:");
            String c = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter choice D:");
            String d = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter the letter of the answer");
            String answer = sc.nextLine().toLowerCase().trim();

            quiz.add(writeMcQuestion(number, question, answer, "a. " + a, "b. " + b, "c. " + c, "d. " + d));
        }

        if (input.equals("blank")) {
            System.out.println("Enter your question:");
            String question = sc.nextLine().toLowerCase().trim();
            System.out.println("Enter the answer:");
            String answer = sc.nextLine().toLowerCase().trim();

            quiz.add(writeBlankQuestion(number, question, answer));
        }

        return quiz;
    }


    //Checks the question type and returns a string indicating question type
    private static String checkQuestionType() {

        System.out.println("For True / False : Enter TF");
        System.out.println("For Multiple Choice : Enter MC");
        System.out.println("For Fill in the Blank : Enter blank");
        System.out.println("If done writing quiz, Enter done.");

        //Collect and sanitize user input
        String input = sc.nextLine().toLowerCase().trim();
        return input;
    }


    public static void generateQuiz() throws IOException {
        //Create the JSON Array quiz
        JSONArray quiz = new JSONArray();
        int number = 1; //Counter to keep track of the number of each question

        //Adds data to the quiz
        while (true) {
            String type = checkQuestionType();

            //Validates question type
            if (!type.equals("tf") && !type.equals("mc") && !type.equals("blank") && !type.equals("done")) {
                System.out.println("Invalid question type. Enter tf, mc, or blank.");
            }
            else if (type.equals("done")) {
                break;
            }
            else {
                //Calls checkQuestion method to enter all required information for the specified question type
                quiz = checkQuestion(number, quiz, type);

                //Increments number of question by 1
                number++;
            }
        }

        JSONObject output = new JSONObject();
        output.put("Questions: ", quiz);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String finalOutput = gson.toJson(output);

        //Writes quiz JSON file
        FileWriter file = new FileWriter("quiz.JSON");
        try {
            file.write(finalOutput);
            System.out.println("Successfully written quiz!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.flush();
            file.close();
        }
    }
}
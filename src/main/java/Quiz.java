////reference: https://www.geeksforgeeks.org/parse-json-java/
//
//import java.io.*;
//import java.util.Scanner;
//import java.io.IOException;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.*;
//import java.util.Collections;
//import java.util.concurrent.atomic.AtomicInteger;
//
//
////this class will take the JSON file quiz created in QuizGenerator.java and read it out to the user 1 by 1
//public class Quiz {
//
//    private static Scanner sc = new Scanner(System.in);
//
//    public static JSONArray takeQuiz(String name) throws IOException, ParseException {
//
//        // parsing file "quiz.json"
//        Object obj = new JSONParser().parse(new FileReader("quiz.JSON"));
//
//        JSONObject jo = (JSONObject) obj; //Make object to parse with
//        int totalQuestions = 0; //Counter to keep track of total number of quiz questions
//        int numberCorrect = 0; //Counter to keep track of total number of correct answers
//
//        JSONArray quiz = (JSONArray) jo.get("Questions: "); //access the questions
//        Collections.shuffle(quiz); //randomize question order
//
//        System.out.print("Welcome to the quiz, " + name + "! Please follow these directions:\nFor \"TF\" questions, answer by typing T for true or F for false.\n"
//        + "If a question is labelled \"Multiple Choice,\" answer A, B, C or D.\nLastly, for \"Fill in the Blank\" questions, just type out the answer.\n"
//                + "Hit enter to move on to the next question. Good Luck!\n\n");
//
//        JSONArray results = new JSONArray();
//
//        //Referenced https://stackoverflow.com/questions/28790784/java-8-preferred-way-to-count-iterations-of-a-lambda
//        AtomicInteger countTotal = new AtomicInteger(0);
//        AtomicInteger countCorrect = new AtomicInteger(0);
//
//        //iterate through questions
//        quiz.forEach(item -> {
//
//            JSONObject x = (JSONObject) item;
//            System.out.println(x.get("Type"));
//            System.out.println(x.get("Question"));
//
//            //Print all options for a multiple choice question
//            if (x.get("Type").equals("Multiple Choice")){
//                JSONArray choices = (JSONArray) x.get("Choices");
//                choices.forEach(choice -> {
//                    System.out.println(choice);
//                });
//            }
//
//            System.out.println("Answer here: ");
//            String userAnswer = sc.nextLine().toLowerCase().trim(); //Sanitize user answer
//
//            //Create record of question, correct answer, and user answer
//            JSONObject question = new JSONObject();
//            question.put("Number", x.get("Number"));
//            question.put("Question", x.get("Question"));
//            question.put("Answer", x.get("Answer"));
//            question.put("Response", userAnswer);
//
//            //output whether user answered correctly
//            if (userAnswer.equals(x.get("Answer"))){
//                System.out.println("Correct!\n");
//                //Increment number of correctly answered questions by 1
//                countCorrect.getAndIncrement();
//            }
//            else {
//                System.out.println("Incorrect :(\n");
//            }
//
//            //Increment total number of questions in quiz by 1
//            countTotal.getAndIncrement();
//            results.add(question);
//            }
//        );
//
//        //Calculate percent correct
//        double percent = (100*(countCorrect.doubleValue()/countTotal.doubleValue()));
//
//        System.out.println("You answered " + countCorrect + " out of " + countTotal + " questions correctly.");
//        System.out.println("That equals " + percent + " percent!");
//
//        JSONObject score = new JSONObject(); //Create new JSON object to store score
//        score.put("Total questions:", countTotal.doubleValue());
//        score.put("Total correct: ", countCorrect.doubleValue());
//        score.put("Percent correct: ", percent);
//        results.add(score);
//        return results;
//    }
//
//    public static void printResults(JSONArray results, String name) throws IOException {
//        //Filename will be unique to the user's name
//        String fileName = name + "_results.JSON";
//        FileWriter file = new FileWriter(fileName);
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String finalOutput = gson.toJson(results);
//
//        try {
//            file.write(finalOutput);
//            System.out.println("Successfully written results!");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            file.flush();
//            file.close();
//        }
//
//        System.out.println("Would you like to see your results? Enter yes or no:");
//        if (sc.nextLine().toLowerCase().equals("no")) {
//            System.out.println("Your results have been recorded. Goodbye!");
//        }
//        else {
//            System.out.println(finalOutput);
//        }
//    }
//}

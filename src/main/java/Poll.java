import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Poll {

    private static Scanner keyboard = new Scanner(System.in);

    //class that will be initially run by Client class
    static JSONArray takePoll(String name) throws IOException, ParseException {
        Object jsonFile;

        if (PollGenerator.getFileToBeRead() == null)
            jsonFile = new JSONParser().parse(new FileReader("defaultPoll.json"));
        else
            jsonFile = new JSONParser().parse(new FileReader(PollGenerator.getFileToBeRead()));

        JSONObject questionObject = (JSONObject) jsonFile; //Make json file able to be parsed with json-simple library
        JSONArray questionArray = (JSONArray) questionObject.get("Questions");//access the questions object
        Collections.shuffle(questionArray, new Random(System.nanoTime())); //randomize question order

        //reads each question object and stores the given response as a JSONArray
        JSONArray results = outputQuestions(questionArray, name);

        //writes results to json file
        writeToJSONFile(results);

        return results;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray outputQuestions(JSONArray questionArray, String name) {

        JSONArray resultsArray = new JSONArray();

        //iterates through each JSONObject in the given array (questionArray)
        questionArray.forEach(object -> {

            //initializes a new question object each iteration
            JSONObject questionDetails = (JSONObject) object;

            //Referenced https://stackoverflow.com/questions/28790784/java-8-preferred-way-to-count-iterations-of-a-lambda
            AtomicInteger optionNumber = new AtomicInteger(1);

            //prints the question onto the screen
            System.out.println("*-------------------------------------------*");
            System.out.println(questionDetails.get("Question"));
            System.out.println("*-------------------------------------------*");

            //makes it possible to access the JSONArray associated with key "Options" in readable json file
            JSONArray options = (JSONArray) questionDetails.get("Options");
            //iterates through JSONArray, printing each option and increases the option number for printing
            options.forEach(option -> {
                System.out.println(optionNumber + ".) " + option);
                optionNumber.getAndIncrement();
            });//end of nested lambda expression


            System.out.println("Answer here: ");
            String userAnswer = keyboard.nextLine().toLowerCase().trim();

            //checks if given user answer is in the options array. If not, keep asking for a new response
            while (!options.contains(userAnswer)) {
                System.out.println(userAnswer + " is not a valid answer, please input one of the provided options: ");
                userAnswer = keyboard.nextLine().toLowerCase().trim();
            }

            resultsArray.add(addQuestionToRecord(questionDetails, userAnswer, name));//after each response is recorded, populate json object to be written to new json array
        });//end of outer lambda expression

        return resultsArray;

    }

    //creates question object that will be recorded
    //called each time the iterator in outputQuestions() is run
    //returns fully packed JSONObject
    @SuppressWarnings("unchecked")
    private static JSONObject addQuestionToRecord(JSONObject jsonObject, String userAnswer, String name) {
        JSONObject question = new JSONObject();

        question.put("Number", jsonObject.get("Number"));
        question.put("Question", jsonObject.get("Question"));
        question.put("Response", userAnswer);
        question.put("Taker", name);

        return question;
    }

    static void printJSONArray(JSONArray results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String finalOutput = gson.toJson(results);

        System.out.println(finalOutput);
    }

    private static void writeToJSONFile(JSONArray jsonArray) throws IOException {

        System.out.println("*-------------------------------------------*");
        System.out.println("Would you like to save your results?(y/n): ");
        System.out.println("*-------------------------------------------*");
        String userAnswer = keyboard.nextLine().toLowerCase().trim();

        while (!Check.isValidNonNumericalInput(userAnswer)) {
            userAnswer = keyboard.nextLine().toLowerCase().trim();
        }

        if (userAnswer.equals("y") || userAnswer.equals("yes")) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
            String finalOutput = gson.toJson(jsonArray);//converts nicely formatted gson builder into string to be written to <filename>

            //File file = Check.makeFile();//file to be created and checked to see if it passes file creation rules

            //Creates reference to a file writer with given filename
            FileWriter resultJSONFile = new FileWriter(new File("C:/Users/Ethan/StudioProjects/kahoot-phase-2/json/pollResults.json"));

            try {
                resultJSONFile.write(finalOutput);
                System.out.println("*-------------------------------------------*");
                System.out.println(PollGenerator.getSuccessMessage() + " -> pollResults.json");
                System.out.println("*-------------------------------------------*");

            } catch (IOException e) {
                System.out.println("*-------------------------------------------*");
                System.out.println(PollGenerator.getErrorMessage() + " -> pollResults.json");
                System.out.println("*-------------------------------------------*");
                e.printStackTrace();
            } finally {
                resultJSONFile.flush();
                resultJSONFile.close();
            }
        }

        else
            System.out.println("Goodbye");

    }

}
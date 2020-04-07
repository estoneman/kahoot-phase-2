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
    static JSONArray takePoll() throws IOException, ParseException {
        Object jsonFile;

        if (PollGenerator.getFileToBeRead() == null)
            jsonFile = new JSONParser().parse(new FileReader("defaultPoll.json"));
        else
            jsonFile = new JSONParser().parse(new FileReader(PollGenerator.getFileToBeRead()));

        JSONObject questionObject = (JSONObject) jsonFile; //Make json file able to be parsed with json-simple library
        JSONArray questionArray = (JSONArray) questionObject.get("Questions");//access the questions object
        Collections.shuffle(questionArray, new Random(System.nanoTime())); //randomize question order

        JSONArray results = outputQuestions(questionArray);

        writeToJSONFile(results);

        return results;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray outputQuestions(JSONArray questionArray) {

        JSONArray resultsArray = new JSONArray();

        questionArray.forEach(object -> {

            JSONObject questionDetails = (JSONObject) object;

            //Referenced https://stackoverflow.com/questions/28790784/java-8-preferred-way-to-count-iterations-of-a-lambda
            AtomicInteger optionNumber = new AtomicInteger(1);

            System.out.println(questionDetails.get("Question"));

            JSONArray options = (JSONArray) questionDetails.get("Options");
            options.forEach(option -> {
                System.out.println(optionNumber + ".) " + option);
                optionNumber.getAndIncrement();
            });//end of nested lambda expression


            System.out.println("Answer here: ");
            String userAnswer = keyboard.nextLine().toLowerCase().trim();

            while (!options.contains(userAnswer)) {
                System.out.println("not a valid answer, please input one of the provided options: ");
                userAnswer = keyboard.nextLine().toLowerCase().trim();
            }

            System.out.println("*------------*");//question separator, signifies new question to be output

            resultsArray.add(addQuestionToRecord(questionDetails, userAnswer));//after each response is recorded, populate json object to be written to new json array
        });//end of outer lambda expression

        return resultsArray;

    }

    @SuppressWarnings("unchecked")
    private static JSONObject addQuestionToRecord(JSONObject jsonObject, String userAnswer) {
        JSONObject question = new JSONObject();

        question.put("Number", jsonObject.get("Number"));
        question.put("Question", jsonObject.get("Question"));
        question.put("Response", userAnswer);

        return question;
    }

    static void printJSONArray(JSONArray results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String finalOutput = gson.toJson(results);

        System.out.println(finalOutput);
    }

    private static void writeToJSONFile(JSONArray jsonArray) throws IOException {

        System.out.println("Would you like to save your results?(y/n): ");
        String userAnswer = keyboard.nextLine().toLowerCase().trim();

        if (userAnswer.equals("y")) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
            String finalOutput = gson.toJson(jsonArray);//converts nicely formatted gson builder into string to be written to <filename>

            File file = PollGenerator.makeFile();//file to be created and checked to see if it passes file creation rules

            //Creates reference to a file writer with given filename
            FileWriter resultJSONFile = new FileWriter(file);

            try {
                resultJSONFile.write(finalOutput);
                System.out.println(PollGenerator.getSuccessMessage() + " -> " + file.getName());

            } catch (IOException e) {
                System.out.println(PollGenerator.getErrorMessage() + " -> " + file.getName());
                e.printStackTrace();
            } finally {
                resultJSONFile.flush();
                resultJSONFile.close();
            }
        }

        else
            System.out.println("goodbye");

    }

}
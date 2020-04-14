import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
class PollGenerator {

    //If file is written successfully, SUCCESS_MESSAGE is printed
    private static final String SUCCESS_MESSAGE = "File successfully written";

    //If file is written unsuccessfully, ERROR_MESSAGE is printed
    private static final String ERROR_MESSAGE = "File could not be written";

    //questions and their respective information will be written to this file
    private static File fileToBeWritten;

    //how the user interacts with our program (for now)
    private static final Scanner keyboard = new Scanner(System.in);

    static String getSuccessMessage() {
        return SUCCESS_MESSAGE;
    }

    static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    private static void setFileToBeRead(File file) {
        fileToBeWritten = file;
    }

    static File getFileToBeRead() {
        return fileToBeWritten;
    }

    //called first by Client class
    //creates the poll and writes to json file
    static void generatePoll(String name) throws IOException {
        //create the poll
        JSONArray poll = new JSONArray();
        int number = 1;//keeps track of question number

        //continuously adds question to the poll until user stops
        while (true) {
            System.out.println("Enter question " + number + " or enter 'done' when you are finished adding questions: ");
            String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

            while (!Check.isValidNonNumericalInput(question)) {
                question = keyboard.nextLine().toLowerCase().trim();
            }

            if (question.equals("done"))
                break;//loop condition

            populateJSONArrayElement(question, number, poll, name);
            number++;

        }

        writeToJSONFile(poll);

    }

    //populates a JSONObject and adds to resultant JSONArray that will be used for writing to the desired json file
    private static void populateJSONArrayElement(String question, int number, JSONArray questionArray, String name) {

        String option;
        int optionNumber = 1;

        List<String> optionsList = new ArrayList<>();

        //loop that asks user for all options for the given question
        while(true) {
            System.out.println("Enter option " + optionNumber + " or enter 'done' to stop adding options: ");
            option = keyboard.nextLine().toLowerCase().trim();

            while (option.isEmpty()) {
                System.out.println("option cannot be empty: ");
                option = keyboard.nextLine().toLowerCase().trim();
            }

            if (option.equals("done"))
                break;
            optionsList.add(option);
            optionNumber++;
        }

        String numberString = String.valueOf(number);//for easier writing and reading
        questionArray.add(writePollQuestion(numberString, question, optionsList, name));

    }

    //helper method for populateJSONArrayElement which pairs question info with the question object
    //returns packed JSONObject as a whole
    private static JSONObject writePollQuestion(String number, String question, List<String> optionsList, String name) {
        JSONObject questionObject = new JSONObject();

        questionObject.put("Number", number);
        questionObject.put("Type", "Poll");
        questionObject.put("Question", question);
        questionObject.put("User", name);

        JSONArray optionsJSONArray = new JSONArray();

        //adds all options into the options field of written json file
        optionsJSONArray.addAll(optionsList);

//        optionsList.forEach(option -> optionsJSONArray.add(option));

//        for (String option : optionsLinkedList)
//            optionsJSONArray.add(option);

//        while (listIterator.hasNext())
//            optionsJSONArray.add(listIterator.next());

        questionObject.put("Options", optionsJSONArray);

        return questionObject;
    }

    //writes and saves all changes to a json file which can be named by the user however they would like, under some guidelines
    private static void writeToJSONFile(JSONArray jsonArray) throws IOException {
        //formats single line json string to a better, more readable json string using gson library
        JSONObject preFormatJSONObject = new JSONObject();

        preFormatJSONObject.put("Questions", jsonArray);//populates the output JSONObject in a non-formatted manner
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
        String finalOutput = gson.toJson(preFormatJSONObject);//converts nicely formatted gson object into string to be written to desired destination

        //allows user to save their poll with desired name
        //File fileToBeWritten = Check.makeFile();//Check.makeFile() checks to see if the file is valid through a series of tests
        setFileToBeRead(new File("C:/Users/Ethan/StudioProjects/kahoot-phase-2/json/pollQuestions.json"));//sets the file that will be read by the poll taker according to what they named if

        //Writes quiz JSON file
        FileWriter pollJSONFile = new FileWriter(new File("C:/Users/Ethan/StudioProjects/kahoot-phase-2/json/pollQuestions.json"));

        try {
            pollJSONFile.write(finalOutput);
            System.out.println("*-------------------------------------------*");
            System.out.println(getSuccessMessage() + " -> pollQuestions.json");
            System.out.println("*-------------------------------------------*");

        } catch (IOException e) {
            System.out.println("*-------------------------------------------*");
            System.out.println(getErrorMessage() + " -> pollQuestions.json");
            System.out.println("*-------------------------------------------*");
            e.printStackTrace();
        } finally {
            pollJSONFile.flush();//flushes the writer object
            pollJSONFile.close();//closes the input stream
        }
    }
}
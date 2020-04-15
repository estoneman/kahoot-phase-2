import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CreatePoll {

    //If file is written successfully, SUCCESS_MESSAGE is printed
    private static final String SUCCESS_MESSAGE = "File successfully written";

    //If file is written unsuccessfully, ERROR_MESSAGE is printed
    private static final String ERROR_MESSAGE = "File could not be written";

    //questions and their respective information will be written to this file
    private static File fileToBeWritten;

    //how the user interacts with our program (for now)
    private static final Scanner keyboard = new Scanner(System.in);

    private static void setFileToBeRead(File file) {
        fileToBeWritten = file;
    }

    private static File getFileToBeRead() { return fileToBeWritten; }

    public static void main(String[] args) {
        run();
    }

    static void run() {
        System.out.println("Enter your name or any unique identifier: ");
        String unique_identifier = keyboard.nextLine().toLowerCase().trim();

        //if database does not already exist, create a new one
        if (!Check.dBExists(unique_identifier))
            SQLInstructions.createDatabase(unique_identifier);

        System.out.println("Enter the name of the poll you would like to create: ");
        String pollName = keyboard.nextLine().toLowerCase().trim();

        //if table does not already exist, create a new one
        while (Check.tableExists(pollName, unique_identifier)) {
            System.out.println("poll name already exists, try a new one: ");
            pollName = keyboard.nextLine().toLowerCase().trim();
        }

        //creates table in specified database
        SQLInstructions.createTable(pollName, unique_identifier);

        generatePoll();

        WriteToSQLServer.writePollQuestions(pollName, unique_identifier);
    }

    //called first by Client class
    //creates the poll and writes to json file
    static void generatePoll() {

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

            populateJSONArrayElement(question, poll);
            number++;

        }

        writeToJSONFile(poll);

    }

    //populates a JSONObject and adds to resultant JSONArray that will be used for writing to the desired json file
    @SuppressWarnings("unchecked")
    private static void populateJSONArrayElement(String question, JSONArray questionArray) {

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

        questionArray.add(writePollQuestion(question, optionsList));

    }

    //helper method for populateJSONArrayElement which pairs question info with the question object
    //returns packed JSONObject as a whole
    @SuppressWarnings("unchecked")
    private static JSONObject writePollQuestion(String question, List<String> optionsList) {
        JSONObject questionObject = new JSONObject();

        questionObject.put("Question", question);

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
    @SuppressWarnings("unchecked")
    private static void writeToJSONFile(JSONArray jsonArray)  {

        FileWriter pollJSONFile = null;

        try {

            //formats single line json string to a better, more readable json string using gson library
            JSONObject preFormatJSONObject = new JSONObject();

            preFormatJSONObject.put("Questions", jsonArray);//populates the output JSONObject in a non-formatted manner
            Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
            String finalOutput = gson.toJson(preFormatJSONObject);//converts nicely formatted gson object into string to be written to desired destination

            //allows user to save their poll with desired name
            //File fileToBeWritten = Check.makeFile();//Check.makeFile() checks to see if the file is valid through a series of tests
            setFileToBeRead(new File(System.getProperty("user.dir") + "/json/pollQuestions.json"));//sets the file that will be read by the poll taker according to what they named if

            //Writes quiz JSON file
            pollJSONFile = new FileWriter(new File(System.getProperty("user.dir") + "/json/pollQuestions.json"));

            pollJSONFile.write(finalOutput);

            System.out.println("*-------------------------------------------*");
            System.out.println(SUCCESS_MESSAGE + " -> pollQuestions.json");
            System.out.println("*-------------------------------------------*");

        }
        catch (IOException e) {
            System.out.println("*-------------------------------------------*");
            System.out.println(ERROR_MESSAGE + " -> pollQuestions.json");
            System.out.println("*-------------------------------------------*");
            e.printStackTrace();
        }
        finally {
            try {
                if (pollJSONFile != null) {
                    pollJSONFile.flush();//flushes the writer object
                    pollJSONFile.close();//closes the input stream
                }
            }
            catch (IOException iOE) {
                iOE.printStackTrace();
            }
        }
    }

}

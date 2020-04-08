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

    private static final String SUCCESS_MESSAGE = "File successfully written";
    private static final String ERROR_MESSAGE = "File could not be written";

    private static File fileToBeWritten;

    private static final Scanner keyboard = new Scanner(System.in);

    static String getSuccessMessage() {
        return SUCCESS_MESSAGE;
    }

    private static void setFileToBeRead(File file) {
        fileToBeWritten = file;
    }

    static File getFileToBeRead() {
        return fileToBeWritten;
    }

    static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    //called first by Client class
    static void generatePoll() throws IOException {
        //create the poll
        JSONArray poll = new JSONArray();
        int number = 1;//keeps track of question number

        //continuously adds question to the poll until user stops
        while (true) {
            System.out.println("Enter question " + number + " or enter 'done' when you are finished adding questions: ");
            String question = keyboard.nextLine().toLowerCase().trim();//converts to lower case and eliminates whitespace characters for easier comparison of strings

            if (question.equals("done"))
                break;//loop condition

            populateJSONArrayElement(question, number, poll);
            number++;

        }

        writeToJSONFile(poll);

    }

    private static void populateJSONArrayElement(String question, int number, JSONArray questionArray) {

        String option;
        int optionNumber = 1;

        List<String> optionsList = new ArrayList<>();

        while(true) {
            System.out.println("Enter option " + optionNumber + " or enter 'done' to stop adding options: ");
            option = keyboard.nextLine().toLowerCase().trim();
            if (option.equals("done"))
                break;
            optionsList.add(option);
            optionNumber++;
        }

        questionArray.add(writePollQuestion(number, question, optionsList));

    }

    private static JSONObject writePollQuestion(int number, String question, List<String> optionsList) {
        JSONObject questionObject = new JSONObject();

        questionObject.put("Number", number);
        questionObject.put("Type", "Poll");
        questionObject.put("Question", question);

        JSONArray optionsJSONArray = new JSONArray();

        optionsJSONArray.addAll(optionsList);

//        optionsList.forEach(option -> optionsJSONArray.add(option));

//        for (String option : optionsLinkedList)
//            optionsJSONArray.add(option);

//        while (listIterator.hasNext())
//            optionsJSONArray.add(listIterator.next());

        questionObject.put("Options", optionsJSONArray);

        return questionObject;
    }

    private static void writeToJSONFile(JSONArray jsonArray) throws IOException {
        //formats single line json string to a better, more readable json string using gson library
        JSONObject output = new JSONObject();

        output.put("Questions", jsonArray);//populates the output JSONObject in a non-formatted manner
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
        String finalOutput = gson.toJson(output);//converts nicely formatted gson builder into string to be written to <filename>

        //allows user to save their poll with desired name
        File fileToBeWritten = makeFile();
        setFileToBeRead(fileToBeWritten);

        //Writes quiz JSON file
        FileWriter pollJSONFile = new FileWriter(fileToBeWritten);

        try {
            pollJSONFile.write(finalOutput);
            System.out.println(SUCCESS_MESSAGE + " -> " + fileToBeWritten.getName());

        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE + " -> " + fileToBeWritten.getName());
            e.printStackTrace();
        } finally {
            pollJSONFile.flush();
            pollJSONFile.close();
        }
    }

    private static boolean hasPeriod(File file) {
        //checks if user entered in a '.'
        return file.getName().contains(".");
    }

    private static boolean exists(File file) {
        return new File(file.getName() + ".json").exists() || file.exists();
    }

    //validates the creation of desired filename
    static File makeFile() {
        System.out.println("What would you like to name the file?: ");
        String filename = keyboard.nextLine();

        File file = new File(filename);//file to be written

        while (exists(file)) {
            System.out.println("File already, exists: ");
            filename = keyboard.nextLine();
            file = new File(filename);
        }

        while (hasPeriod(file)) {
            System.out.println("Remove '.' : '.json' will be added for you: ");
            filename = keyboard.nextLine();
            file = new File(filename);
        }

        return new File(filename + ".json");

    }

}
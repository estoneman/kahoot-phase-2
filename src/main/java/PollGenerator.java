import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
class PollGenerator {

    private static final String FILENAME = "poll.json";
    private static final String SUCCESS_MESSAGE = "File successfully written";
    private static final String ERROR_MESSAGE = "File could not be written";

    private static final Scanner keyboard = new Scanner(System.in);

    public static String getFileName() {
        return FILENAME;
    }

    public static String getSuccessMessage() {
        return SUCCESS_MESSAGE;
    }

    public static String getErrorMessage() {
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

            poll = populateJSONArrayElement(question, number, poll);
            number++;

        }

        writeToJSONFile(FILENAME, poll);

    }

    private static JSONArray populateJSONArrayElement(String question, int number, JSONArray questionArray) {

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

        return questionArray;
    }

    private static JSONObject writePollQuestion(int number, String question, List<String> optionsList) {
        JSONObject questionObject = new JSONObject();

        questionObject.put("Number", number);
        questionObject.put("Type", "Poll");
        questionObject.put("Question", question);
        questionObject.put("Answer", "");

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

    private static void writeToJSONFile(String filename, JSONArray jsonArray) throws IOException {
        //formats single line json string to a better, more readable json string using gson library
        JSONObject output = new JSONObject();

        output.put("Questions", jsonArray);//populates the output JSONObject in a non-formatted manner
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//each key, value pair is one line and each array index gets their own line for improved readability
        String finalOutput = gson.toJson(output);//converts nicely formatted gson builder into string to be written to <filename>

        //Writes quiz JSON file
        FileWriter pollJSONFile = new FileWriter(filename);

        try {
            pollJSONFile.write(finalOutput);
            System.out.println(SUCCESS_MESSAGE + " -> " + filename);

        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE + " -> " + filename);
            e.printStackTrace();
        } finally {
            pollJSONFile.flush();
            pollJSONFile.close();
        }
    }

}
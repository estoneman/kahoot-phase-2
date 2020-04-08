import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
//writer
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//reader
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//https://howtodoinjava.com/library/json-simple-read-write-json-examples/

import java.util.Scanner;

//public class User extends ReadJSONFile {
public class User  {

//    ReadJSONFile readJSONFile = new ReadJSONFile();
//    private HashMap<String, String> JSONHashMap;
    @SuppressWarnings("unchecked")//avoids unchecked errors with an unknown error that I can't solve
    public static void main(String[] args) {
        //this class will be used to interact with the user

//        ReadJSONFile readJSONFile = new ReadJSONFile();

//        String name; //for scoring
        String qType = ""; //(tf,mc,fb,m, or all)
        String userChoice; //between (A)Create a quiz or (B)Take a quiz

        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Please enter your name: ");
//        name = keyboard.nextLine();


        //Sample tf question arrayList for testing
        //questions in reader will be stored in an arraylist as questionA, answerA, questionB, answerB...
        ArrayList<String> qList = new ArrayList<String>();
        qList.add("The sun is hot");
        qList.add("True");
        qList.add("The sun is cold");
        qList.add("false");

        int score;
        String userA = null;



        System.out.println("Would you like to (A)Create a quiz or (B)Take a quiz?"); //or any other letter to exit
        userChoice = keyboard.nextLine();

        if (userChoice.equalsIgnoreCase("A")) {
            //Ask reader for q's and a's and format them into JSON file
            JSONObject trueFalseQuestionDetails = new JSONObject();//
            JSONObject mCQuestionDetails;
            JSONObject mCQuestionObject = new JSONObject();
            JSONArray mCArray = new JSONArray();

            String answer, option, numOptions, numQuestions, userPermission;
            String question = "";
            boolean creating = true;
            //for multiple choice questions and matching

            int loopCounter;//for controlling number of questions the user would like to add as well as number of options

            while (creating) {
                loopCounter = 0;

                System.out.println("What kind of question would you like to add?\n1. tf\n2. mc\n3. fb\n4. m");
                qType = keyboard.nextLine();

                if (qType.equalsIgnoreCase("quit")) {
                    creating = false;
                }

                switch (qType) {
                    case "tf":
                        //will be later put in a loop to keep asking until user wants to quit
                        //asks user for the question they would like to add
                        System.out.println("how many questions would you like to add");
                        numQuestions = keyboard.nextLine();

                        if (numQuestions.equalsIgnoreCase("quit"))
                            creating = false;

                        while (creating && (loopCounter < Integer.parseInt(numQuestions))) {
                            System.out.println("Type the true/false question you would like to add: ");
                            question = keyboard.nextLine();

                            if (question.equalsIgnoreCase("quit"))
                                creating = false;

                            //asks user for the answer to the above question
                            System.out.println("Type the answer to that question: ");
                            answer = keyboard.nextLine();

                            if (answer.equalsIgnoreCase("quit"))
                                creating = false;

                            //puts the question and answer as a key value pair in a map like system to JSONFile to be written later
                            trueFalseQuestionDetails.put(question, answer);

                            loopCounter++;
                        }

                        break;

                    case "mc":
                        mCQuestionDetails = new JSONObject();//flushes values in memory of this variable

                        System.out.println("how many questions would you like to add?: ");
                        numQuestions = keyboard.nextLine();

                        if (numQuestions.equalsIgnoreCase("quit"))
                            creating = false;

                        //loops through however many times user wants
                        //maybe we change this to while loop so user has more control over data input?
                        while (creating && (loopCounter < Integer.parseInt(numQuestions))) {

                            System.out.println("Type the multiple choice question you would like to add");
                            question = keyboard.nextLine();

                            if (question.equalsIgnoreCase("quit"))
                                creating = false;

                            System.out.println("How many options would you like to have?: ");
                            numOptions = keyboard.nextLine();

                            if (numOptions.equalsIgnoreCase("quit"))
                                creating = false;

                            //change to a while loop
                            for (int j = 1; j < Integer.parseInt(numOptions) + 1; j++) {
                                System.out.println("Enter option " + j + ": ");
                                option = keyboard.nextLine();

                                if (option.equalsIgnoreCase("quit"))
                                    creating = false;

                                mCQuestionDetails.put(j + ". ", option);//puts choice number and option as key value pair into json file
                            }

                            System.out.println("Enter the answer: ");
                            answer = keyboard.nextLine();

                            if (answer.equalsIgnoreCase("quit"))
                                creating = false;

                            mCQuestionDetails.put("Correct Answer", answer);

                            //associates question with all options and correct answer

                            loopCounter++;
                        }

                        mCQuestionObject.put(question, mCQuestionDetails);

                        break;
                    case "fb":
                        break;

                    case "m":
                        break;

                }

                System.out.println("Would you like to add another question type to your quiz?(y/n): ");
                userPermission = keyboard.nextLine();
                if (userPermission.equalsIgnoreCase("y"))
                    continue;
                else {
                    creating = false;
                }

            }

            mCArray.add(mCQuestionObject);//adds to json file as an array for later parsing

            //writes to the true-false.json file
            //NOTE: it overwrites anything already existing which I think is what we want for a new quiz each time for now
            try {
                //accesses the json file to be written to
                FileWriter trueFalseFile = new FileWriter("/Users/Ethan/AndroidStudioProjects/COMP330/json/trueFalse.json");
                FileWriter multipleChoiceFile = new FileWriter("/Users/Ethan/AndroidStudioProjects/COMP330/json/multipleChoice.json");

                //writes all contents of each JSONObject to their respective json files
                trueFalseFile.write(trueFalseQuestionDetails.toJSONString());
                multipleChoiceFile.write(mCArray.toJSONString());

                //flushes the file of any buffers
                trueFalseFile.flush();
                multipleChoiceFile.flush();

            }

            catch (IOException e) {
                e.printStackTrace();
            }



        }





        if (userChoice.equalsIgnoreCase("B")) {

            score = 0;

            System.out.println("Select question type: tf, mc, fb, m, or all");
            //currently TakeQuiz only works with option "all" and questions.json

            //eventually we want to only offer q types avaliable from json
            //and allow users to select multiple q types to form their own quiz, ex tf + fb
            qType = keyboard.nextLine();

            //switch case statement for each qType
            //check if case sensitive!
            switch (qType) {
                    case "tf":
                        //test list
                        System.out.println("Answer true or false: ");
                        for (int i = 0; i <= qList.size()-1; i++) {
                            System.out.println(qList.get(i));

                            userA = keyboard.nextLine();

                            //i is added to switch to answer for check
                            i++;

                            if (userA.equalsIgnoreCase(qList.get(i))) {
                                score++;
                            }


                        }

                        System.out.println("Your score = " + score);
                        System.out.println("Quiz key:");
                        System.out.println(qList);



                        //get tf arraylist
                        break;

                    case "mc":
                        //get mc arraylist
                        break;

                    case "fb":
                        //get fb arraylist
                        break;

                    case "m":
                        //get m arraylist
                        break;

                    case "all":
                        //iterate through each list
                        //state which type of question list before asking new question list

                        break;
                }

        }

        else {
            System.out.println("goodbye");
        }

//        JSONHashMap = new HashMap<String, String>();

        //test hashmap
        //System.out.println(JSONHashmap);

        //test keyboard


        keyboard.close();

    }
}
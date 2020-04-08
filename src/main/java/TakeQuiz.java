import java.util.*;

public class TakeQuiz {

//take quiz and create quiz are currently in User.java
//both will be moved to their respective classes and called from the User class

    //collect the arraylist questions from ReadJSONFile, iterate through selected questions with user.

    Scanner keyboard = new Scanner(System.in);

    //Sample tf question arrayList for testing
    //questions in reader will be stored in an arraylist as questionA, answerA, questionB, answerB...
    ArrayList<String> qList = new ArrayList<String>();
        qList.add("The sun is hot");
        qList.add("True");
        qList.add("The sun is cold");
        qList.add("false");

    int score;
    String userA = null;



    public void userInput() {

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





    //main
    public static void main(String[] args) { new TakeQuiz().userInput(); }





}
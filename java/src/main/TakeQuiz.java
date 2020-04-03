import java.util.*;

public class TakeQuiz {

    //general hash map to be used for reading in questions from ReadJSON.java
    HashMap<String, String> hm;

    //for keyboard user input
    static Scanner keyboard;

    //counter for incorrect and correct answers
    int numCorrect = 0;
    int numIncorrect = 0;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);

        System.out.println("What quiz would you like to take?");
        String user = keyboard.nextLine();

        if (user.toLowerCase().equals("tf"))
            new TakeQuiz().takeTrueFalse();
    }

    public void takeTrueFalse() {
        hm = new ReadJSON().readTFObject();
        String userInput;

        for (Map.Entry<String, String> entry : hm.entrySet()) {
            System.out.println(entry.getKey());

            userInput = keyboard.nextLine();
            if (userInput.equals(entry.getValue())) {
                numCorrect++;
                System.out.println("correct");
            }
            else {
                numIncorrect++;
                System.out.println("incorrect");
            }
        }
        System.out.println("correct: " + numCorrect + "\nIncorrect: " + numIncorrect);
    }

}
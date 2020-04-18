import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;

import javax.swing.*;

public class Client {

    public static void main(String[] args) throws IOException, ParseException {

      /**  JFrame frame = new JFrame("330 Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button1 = new JButton("Take");
        JButton button2 = new JButton("Create");
        JLabel label = new JLabel("Welcome! Would you like to Create or Take?");
        frame.getContentPane().add(BorderLayout.LINE_END, button1);
        frame.getContentPane().add(BorderLayout.NORTH, label);
        frame.getContentPane().add(BorderLayout.LINE_START, button2);
        frame.setVisible(true);**/

        Scanner sc = new Scanner(System.in);

        String input;

        System.out.println("Would you like to create a quiz or a poll?\nEnter 'q' for quiz and 'p' for polling: ");
        String pollOrQuiz = sc.nextLine().toLowerCase().trim();

        while (!Check.isValidNonNumericalInput(pollOrQuiz)) {
            pollOrQuiz = sc.nextLine().toLowerCase().trim();
        }

        if (pollOrQuiz.equals("p")) {

            CreatePoll.generatePoll();

            System.out.println("Would you like to take the created poll(y/n)? If not, enter 'done' or 'no' to exit: ");
            input = sc.nextLine().toLowerCase().trim();

            while (!Check.isValidNonNumericalInput(input)) {
                input = sc.nextLine().toLowerCase().trim();
            }

            if (input.equals("yes") || input.equals("y")) {
                TakePoll.takePoll();
            }

        }
//        else {
//            //Write the quiz
//            QuizGenerator.generateQuiz();
//
//            System.out.println("\nEnter your name to take the quiz or enter done to exit:\n");
//            input = sc.nextLine();
//
//            if (!input.equals("done")) {
//
//                //If user wants to take the quiz, calls Quiz method
//                JSONArray results = Quiz.takeQuiz(input);
//
//                //Calls printResults method in Quiz.java to record user's results
//                Quiz.printResults(results, input);
//            }
//        }
    }
}
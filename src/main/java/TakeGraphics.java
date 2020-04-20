import javax.swing.*;
import java.awt.*;

public class TakeGraphics {

    public int userScore = 0;


    public static void main(String[] args) {

        //NEED TO ADD ActionListeners for buttons and radio buttons --> record userAns to check against answer --> update score

        TakeGraphics takeGraphics = new TakeGraphics();
        takeGraphics.buildTrueFalse("\"Sanity Test\"","Tom","You are Crazy","False");

        /** TakeGraphics
         * type - label contains: poll/quiz, nameOfQuiz, nameOfPoll, author
         * question - label
         * --------------------
         * tf
         * true - button
         * false - button
         * --------------------
         * mc
         * answer# - button (however many)
         * or
         * Radio buttons
         * --------------------
         * fb
         * editable box?
         * --------------------
         * m
         * clickable selection?
         *
         */

        /**
         * Have each qType have a set format
         * or
         * Have get... statements that build elements such as labels and buttons
         */

        /**
         * Take Quiz is selected --> Client calls TakeGraphics
         * While loop runs through first qType (presenting its label and relevant options)
         * answer is recorded
         * continue loop w second qType
         * answer is recorded
         * continue loop w third qType...
         * -----------------
         * Go through the saved quiz/poll formats so you can read them based on each qType section(first, second, third...)
         */

        //sample ---------------------
        /**int length = 300;
         int width = 300;

         JFrame frame = new JFrame("Clickers");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(length,width);


         JLabel type = new JLabel("Quiz: " + "\"Sanity Test\" " + "by " + "Tom"); //type + title + author

        JLabel question = new JLabel("You are crazy"); //question
        JButton button1 = new JButton("True");
        JButton button2 = new JButton("False");

        question.setHorizontalAlignment((JLabel.CENTER));
        type.setHorizontalAlignment((JLabel.CENTER));

        frame.getContentPane().add(BorderLayout.SOUTH, type);
        frame.getContentPane().add(BorderLayout.NORTH, question);
        frame.getContentPane().add(BorderLayout.LINE_START, button1);
        frame.getContentPane().add(BorderLayout.LINE_END, button2);
        frame.setVisible(true);
         */
        //----------------------------

    }

    //buildTF
    public int buildTrueFalse(String title, String author, String question, String answer) {

        int length = 300;
        int width = 300;

        JFrame frame = new JFrame("Clickers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(length,width);

        JLabel type = new JLabel("Quiz: " + title + "by " + author); //type + title + author
        
        JLabel q1 = new JLabel(question); //question
        JButton button1 = new JButton("True");
        JButton button2 = new JButton("False");

        q1.setHorizontalAlignment((JLabel.CENTER));
        type.setHorizontalAlignment((JLabel.CENTER));

        frame.getContentPane().add(BorderLayout.SOUTH, type);
        frame.getContentPane().add(BorderLayout.NORTH, q1);
        frame.getContentPane().add(BorderLayout.LINE_START, button1);
        frame.getContentPane().add(BorderLayout.LINE_END, button2);
        frame.setVisible(true);



        return userScore;
    }

    //buildMC

    //buildFB

    //buildM

}

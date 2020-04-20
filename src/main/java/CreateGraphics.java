import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGraphics {

    public static void main(String[] args) {
         JFrame frame = new JFrame("PHASE 2");
         WelcomeScreen(frame);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setVisible(true);
    }

    public static void WelcomeScreen(JFrame frame) {
         JPanel panel = new JPanel();
         panel.setBackground(Color.CYAN);
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
         panel.setPreferredSize(new Dimension(400,200));
         JPanel panel2 = new JPanel();
         panel2.setBackground(Color.CYAN);
         panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
         panel2.setPreferredSize(new Dimension(400,200));
         JButton button1 = new JButton("Take");
         JButton button2 = new JButton("Create");
         JLabel label = new JLabel("Welcome! Would you like to Create or Take?");
         panel.add(label);
         label.setAlignmentX(Component.CENTER_ALIGNMENT);
         panel2.add(button1);
         panel2.add(button2);
         panel.add(panel2);
         panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
         frame.add(panel);

         button2.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                   frame.getContentPane().removeAll();
                   frame.repaint();
                   pollOrQuiz(frame);
              }
         });

    }

     public static void pollOrQuiz(JFrame frame) {
          JPanel panel = new JPanel();
          panel.setBackground(Color.CYAN);
          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
          panel.setPreferredSize(new Dimension(400,200));
          JPanel panel2 = new JPanel();
          panel2.setBackground(Color.CYAN);
          panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
          panel2.setPreferredSize(new Dimension(400,200));
          JButton button1 = new JButton("Poll");
          JButton button2 = new JButton("Quiz");
          JLabel label = new JLabel("Please choose whether you wish to create a Poll or a Quiz.");
          panel.add(label);
          label.setAlignmentX(Component.CENTER_ALIGNMENT);
          panel2.add(button1);
          panel2.add(button2);
          panel.add(panel2);
          panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
          frame.add(panel);
          frame.pack();
          frame.setVisible(true);

          button1.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    getQuestion(frame);
               }
          });
     }


     public static void getQuestion(JFrame frame) {
          JPanel panel = new JPanel();
          panel.setBackground(Color.CYAN);
          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
          panel.setPreferredSize(new Dimension(400, 400));
          JButton button1 = new JButton("Next");
          JLabel label = new JLabel("Enter the Question to be polled then click next.");
          JTextField textBar = new JTextField(20);
          textBar.setText("Write question here");
          panel.add(label);
          label.setAlignmentX(Component.CENTER_ALIGNMENT);
          panel.add(textBar);
          panel.add(button1);
          button1.setAlignmentX(Component.CENTER_ALIGNMENT);
          frame.add(panel);
          frame.pack();
          frame.setVisible(true);
          textBar.selectAll();
          String userQuestion = textBar.getText();

          button1.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                    frame.getContentPane().removeAll();
                    frame.repaint();;
                    getOptions(userQuestion,frame);
               }
          });

     }

     public static String[] getOptions(String question, JFrame frame) {
          JPanel panel = new JPanel();
          String enteredQ = question;
          panel.setBackground(Color.CYAN);
          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
          panel.setPreferredSize(new Dimension(400, 400));
          JButton button1 = new JButton("Next");
          JLabel label1 = new JLabel("Your Question is: " + enteredQ);
          JLabel label2 = new JLabel("Enter answer options in the drop down box.");
          JComboBox options = new JComboBox();
          panel.add(label1);
          label1.setAlignmentX(Component.CENTER_ALIGNMENT);
          panel.add(label2);
          label2.setAlignmentX(Component.CENTER_ALIGNMENT);
          panel.add(options);
          button1.setAlignmentX(Component.CENTER_ALIGNMENT);
          panel.add(button1);
          button1.setAlignmentX(Component.CENTER_ALIGNMENT);
          frame.add(panel);
          frame.pack();
          frame.setVisible(true);

          String stringArray[];
          stringArray = new String[20];
          return stringArray;
     }


}

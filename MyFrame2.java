//Clacker is a game in which a user must roll 2 die, and click either the numbers on each die, or the sum. If neither are available,
//the user must click nothing and roll again. The goal is to click all numbers in the least amount of rolls possible.

//File name: MyFrame2.java
//Date: 2022-06-19
//Programmer: Piero Camposeo
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class MyFrame2 extends JFrame implements ActionListener {

   static JButton[] buttons;
   static JButton rollButton, newGameButton;
   static JLabel rollResult, numRollsLabel;
   static Random rand = new Random();
   static int roll1 = -1, roll2 = -1, total = 100, numRolls = 0, clicked = 2, bestScore = 999, clicked_total = 0;
   static boolean firstGame = true;

   /** 
	 * Constructor
	 * pre: 
	 * post: MyFrame2 object created
	 */
   public MyFrame2() {
   
      GridLayout layout = new GridLayout(4,3,5,5);
      //Layout
      
      this.setTitle("Clacker");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLayout(layout);
      this.setSize(700,700);
      this.setDefaultLookAndFeelDecorated(true);
      //Initializing frame
      
      //Creating and initializing buttons for game
      buttons = new JButton[12];
      for (int i = 0; i < 12; i++) {
      
        buttons[i] = new JButton(Integer.toString(i + 1));
        buttons[i].setBackground(new Color(185, 233, 240));
        buttons[i].setFocusable(false);
        buttons[i].setHorizontalTextPosition(JButton.CENTER);
        buttons[i].addActionListener(this);
        this.add(buttons[i]);
        
      }
      
      //Creating and initializing roll button for user
      rollButton = new JButton("Roll");
      rollButton.setBackground(new Color(185, 233, 33));
      rollButton.setFocusable(false);
      rollButton.setHorizontalTextPosition(JButton.CENTER);
      rollButton.addActionListener(this);
      this.add(rollButton);
      
      //Creating and initializing new game button for user
      newGameButton = new JButton("New Game");
      newGameButton.setBackground(new Color(185, 233, 33));
      newGameButton.setFocusable(false);
      newGameButton.setHorizontalTextPosition(JButton.CENTER);
      newGameButton.addActionListener(this);
      this.add(newGameButton);
      
      //For user to see roll result and number of rolls, and best score (which is within the numRollsLabel)
      rollResult = new JLabel("Roll Result");
      rollResult.setHorizontalTextPosition(JLabel.LEFT);
      this.add(rollResult);
      numRollsLabel = new JLabel("<html>Number of Rolls: " + numRolls + "<br>Best Score: </html>" + bestScore);
      numRollsLabel.setHorizontalTextPosition(JLabel.LEFT);
      this.add(numRollsLabel);
      
      //Set frame visible
      this.setVisible(true);

      check_newGame();
      
   }

   //Sets the test for the numRollsLabel (to display best score or not)
   public void setRolls() {
      if (firstGame) {
         numRollsLabel.setText("<html>Number of Rolls: " + numRolls + "<br>Best Score: </html>" + bestScore);
      } else {
         numRollsLabel.setText("<html>Number of Rolls: " + numRolls + "<br>Best Score: " + bestScore + "</html>");
      }
   }

   //Resets the game
   public void newGame() {

      //Update best score if player just finished a game
      if ((numRolls < bestScore) && (clicked_total == 12)) {
         bestScore = numRolls;
      }

      //Enable all the buttons
      for (int i = 0; i < 12; i++) {
       
         buttons[i].setEnabled(true);
         buttons[i].setBackground(new Color(185, 233, 240));
         buttons[i].setText(Integer.toString(i + 1));
         
      }
      //Reset variables
      numRolls = 0;
      rollResult.setText("Roll Result");
      setRolls();
      total = 100;
      clicked = 2;
      clicked_total = 0;
         
   }
   
   //If the user clicks all 12 buttons, the game auto-resets, and displays the high score next time the roll button is clicked
   public void check_newGame() {
      if (clicked_total == 12) {
         newGame();
         firstGame = false;
      }
   }

   /** 
	 * preforms an action if a button is pressed
	 * pre: e must be passed through as an ActionEvent
	 * post: Varies
	 */
   public void actionPerformed(ActionEvent e) {

      //Rolls die
      if (e.getSource() == rollButton) {
         clicked = 0;
      
         //User has rolled
         numRolls++;
         
         //Generate 2 die rolls
         roll1 = rand.nextInt(6);
         roll2 = rand.nextInt(6);
         total = roll1 + roll2 + 2;
         
         //Present user information
         rollResult.setText("Your roll: " + (roll1 + 1) + ", " + (roll2 + 1) + ". Total: " + total);

         setRolls();
         
      }

      //If a new game is initialized, reset all buttons, rollcount, and die values
      if ((e.getSource() == newGameButton)) {
      
         newGame();
         
      }

      //If the user clicks the sum of their die, disable it, don't let them click anything else, check if all buttons have been clicked
      if ((clicked == 0) && (e.getSource() == buttons[total - 1])) {
        
         buttons[total - 1].setEnabled(false);
         buttons[total - 1].setBackground(Color.black);
         buttons[total - 1].setText("");
         clicked = 2;
         clicked_total++;
         check_newGame();

      //If the user clicks the value of the first die, disable it, check if they have clicked the other die,
      //check if all buttons have been clicked
      } else if (((clicked == 0) || (clicked == 1)) && (e.getSource() == buttons[roll1])) {
         buttons[roll1].setEnabled(false);
         buttons[roll1].setBackground(Color.black);
         buttons[roll1].setText("");
         clicked_total++;
         
         if (clicked == 0) {
            clicked = 1;
         } else {
            clicked = 2;
         }
         check_newGame();

      //If the user clicks the value of the second die, disable it, check if they have clicked the other die,
      //check if all buttons have been clicked
      } else if (((clicked == 0) || (clicked == 1)) && (e.getSource() == buttons[roll2])) {
         buttons[roll2].setEnabled(false);
         buttons[roll2].setBackground(Color.black);
         buttons[roll2].setText("");
         clicked_total++;
         
         if (clicked == 0) {
            clicked = 1;
         } else {
            clicked = 2;
         }
         check_newGame();
      }
   }
}

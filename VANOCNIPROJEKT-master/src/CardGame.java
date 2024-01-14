import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardGame extends JFrame implements ActionListener
{
    /** Number of cards */
    public static final int NO_OF_CARDS = 4;
    public static final Color backgroundColor = new Color(255, 255, 255);
    /** Just font */
    private Font scoreFont;
    private Font winningHandFont;
    /** Initializes card object */
    private CardSet hand;
    private Container initalArea;
    private JPanel cardArea;


    /** Names basically say what it does */
    private JButton newDealButton;
    private JButton secondDealButton;
    private JPanel[] usersCurrentHand;
    private JCheckBox[] checkBox;
    private JMenuItem newGameFn;
    private JMenuItem clearScoresFn;
    private JMenuItem exitFn;
    private Label tsLabel;
    private Label sLabel;
    private Label handLabel;



    /** Title */
    public CardGame(String title)
    {
        super(title);

        //Initialisisng the class variables
        checkBox = new JCheckBox[NO_OF_CARDS];
        scoreFont = new Font("Arial", Font.BOLD, 15);
        winningHandFont = new Font("Arial", Font.BOLD, 20);
        initalArea = new JPanel();
        cardArea = new JPanel();
        usersCurrentHand = new JPanel[NO_OF_CARDS];
        hand = new CardSet();
        tsLabel = new Label();
        sLabel = new Label();
        handLabel = new Label();
        exitFn = new JMenuItem();

        //Size and location of the window
        setSize(800,550);
        setLocation(250,100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //Layout and menu bar
        setContentPane(createLayout());
        setJMenuBar(createMenuBar());

        setVisible(true);
    }

    /** Creates a layout for the game window */
    private Container createLayout()
    {

        initalArea.setLayout(new BorderLayout());
        initalArea.setBackground(backgroundColor);

        //Score configuration
        JPanel scoreArea = new JPanel();;
        scoreArea.setLayout(new GridLayout(2,1));
        scoreArea.setBackground(backgroundColor);
        //This is adding text to display the score
        sLabel.setText("Score: "+hand.getScore());
        tsLabel.setText("Total Score: "+hand.getTotalScore());
        tsLabel.setFont(scoreFont);
        sLabel.setFont(scoreFont);
        scoreArea.add(sLabel);
        scoreArea.add(tsLabel);

        initalArea.add(scoreArea, BorderLayout.SOUTH);

        //Card configuration
        cardArea.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 110));
        cardArea.setBackground(backgroundColor);

        initalArea.add(cardArea, BorderLayout.CENTER);

        //Button configuration
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(backgroundColor);

        buttonPanel.add(Box.createRigidArea(new Dimension(0,150)));
        buttonPanel.add(Box.createRigidArea(new Dimension(150,0)));

        newDealButton = new JButton ("New Deal");
        //Adds the listener to the NEW DEAL button
        newDealButton.addActionListener(this);
        buttonPanel.add(newDealButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,20)));

        secondDealButton = new JButton ("Second Deal");
        //Adds the listener to the SECOND DEAL button
        secondDealButton.addActionListener(this);
        //Makes the SECOND DEAL button unusable
        secondDealButton.setEnabled(false);
        //Sets allignment for two buttons
        newDealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondDealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(secondDealButton);

        initalArea.add(buttonPanel, BorderLayout.WEST);

        //Best hand
        JPanel handPanel = new JPanel();
        handPanel.setBackground(backgroundColor);
        handLabel.setText("Info: ");
        handLabel.setFont(winningHandFont);
        handPanel.add(handLabel);
        initalArea.add(handPanel, BorderLayout.NORTH);

        return initalArea;
    }

    /** Method for the menu bar */
    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        newGameFn = new JMenuItem("New Game");
        newGameFn.addActionListener(this);

        clearScoresFn = new JMenuItem("Clear Scores");
        clearScoresFn.addActionListener(this);

        exitFn = new JMenuItem("Exit");
        exitFn.addActionListener(this);

        gameMenu.add(newGameFn);
        gameMenu.add(clearScoresFn);
        gameMenu.add(exitFn);

        menuBar.add(gameMenu);

        return menuBar;
    }

    public JPanel addCard(int cardNumber)
    {
        //Grid Layout for the card and check box
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        //The Area the card will be displayed
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(backgroundColor);

        //Adding images to game
        JLabel cardLabel = new JLabel();

        //Calls deal
        Icon cardIcon = new ImageIcon(hand.deal(cardNumber));
        cardLabel.setIcon(cardIcon);

        cardPanel.add(cardLabel);

        //Setting size of pannel image sits in
        cardPanel.setPreferredSize(new Dimension(79, 150));
        panel.add(cardPanel);

        //The check box
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(backgroundColor);
        checkBox[cardNumber] = new JCheckBox("Hold");
        checkBox[cardNumber].setBackground(backgroundColor);
        checkBoxPanel.add(checkBox[cardNumber]);

        panel.add(checkBoxPanel);

        return panel;
    }

    /** User interaction */
    public void actionPerformed (ActionEvent e)
    {
        if(e.getSource() instanceof JButton){

            if(e.getSource() == newDealButton){
                newDealButton.setEnabled(false);
                secondDealButton.setEnabled(true);

                //This adds the four cards to the screen
                for(int i=0; i<NO_OF_CARDS; i++){
                    usersCurrentHand[i] = addCard(i);
                    cardArea.add(usersCurrentHand[i]);
                }

                hand.determineSuit();				//Determines the suit type for each card
                hand.determineValue();				//Determines the value for each card
                //Refresh images
                setVisible(true);
            }
            if(e.getSource() == secondDealButton){
                secondDealButton.setEnabled(false);

                //This checks which card the user wishes to hold and updates the
                //images of the cards to match the users choice
                for(int k=0; k<NO_OF_CARDS; k++)
                    cardArea.remove(usersCurrentHand[k]);
                for(int i=0; i<NO_OF_CARDS; i++){
                    if(!checkBox[i].isSelected())
                        usersCurrentHand[i] = null;
                }
                for(int j=0; j<NO_OF_CARDS; j++){
                    if(usersCurrentHand[j] == null)
                        usersCurrentHand[j] = addCard(j);
                }
                for(int l=0; l<NO_OF_CARDS; l++){
                    cardArea.add(usersCurrentHand[l]);
                    //This disables the use of the textboxes
                    checkBox[l].setEnabled(false);
                }

                hand.determineSuit();				//Determines the suit type for each card
                hand.determineValue();				//Determines the value for each card
                //Updates score
                hand.updateScore();

                //Updates the scores and winning hand
                tsLabel.setText("Total Score: "+hand.getTotalScore());
                sLabel.setText("Score: "+hand.getScore());
                handLabel.setText("Info: "+hand.getWinningHand());

                setVisible(true);
            }
        }
        if(e.getSource() instanceof JMenuItem){
            //If the new game button is pressed it restarts some of the variables
            if(e.getSource() == newGameFn){
                initalArea = new JPanel();
                cardArea = new JPanel();
                usersCurrentHand = new JPanel[NO_OF_CARDS];
                hand = new CardSet();

                setContentPane(createLayout());
                setJMenuBar(createMenuBar());

                setVisible(true);
            }
            //This clears the old scores back to zero
            if(e.getSource() == clearScoresFn){
                hand.clearScores();
                tsLabel.setText("Total Score: "+hand.getTotalScore());
                sLabel.setText("Score: "+hand.getScore());
                setVisible(true);
            }
            //This exits the game
            if(e.getSource() == exitFn){
                System.exit(0);
            }
        }
    }
}
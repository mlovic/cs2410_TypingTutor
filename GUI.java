import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.lang.*;

// Marko Lovic
//
// How to sort map by value
// Only three prompts. In class or program
// Ways to extract classes
//
public class GUI extends JFrame implements KeyListener, ItemListener {
    private final int WIDTH = 800, LENGTH = 600;

    private JTextArea promptBox, typeBox;
    private JLabel wrongLabel, numErrorsLabel, accuracyLabel, wpmLabel;
    private JRadioButton radio1, radio2, radio3;
    private JCheckBox checkBox;
    private ButtonGroup bgroup;
    private java.util.List<JButton> buttons;
    private String qwerty, prompt, typedText;
    private String prompts[];
    private double accuracy, startTime, endTime;
    private int currentChar, rowBreak1, rowBreak2, numErrors, promptNum, 
                numKeysTyped;
    private Map<Character, Integer> errors; 

    public GUI() {
        this.setLayout(null);
        setVisible(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        errors = new HashMap<Character, Integer>();
        qwerty = "qwertyuiopasdfghjkl;'zxcvbnm,./ ";
        prompts = new String[] { 
            "The average typing speed of a computer typist is just 36wpm. Touch typists are generally faster, averaging a quick 48 words per minute. The fastest typing speed ever recorded is currently an extreme 216 words per minute! Average accuracy is 92%. Bananas?",
            "\"The quick brown fox jumps over the lazy dog\" is a phrase that's commonly used to showcase fonts, as it contains every letter of the english language, making it a pangram. My personal favorite is: \"You go tell that vapid existentialist quack Freddy Nietzsche that he can just bite me, twice.\"",
            "Okay, we're finally down to the last prompt. I'm running out of things to say. Maybe I could give you more amazing typing fun facts? Or write you a quick, but majestic poem? A story about the quick brown fox. Perhaps I could write about special characters like / or *. Bye!"  
        };
        promptNum = 0;
        prompt = prompts[promptNum];
        typedText = "> ";
        rowBreak1 = 10;
        rowBreak2 = 21;
        numErrors = 0;
        currentChar = 0;
        numKeysTyped = 0;
        radio1 = new JRadioButton("prompt 1");
        radio2 = new JRadioButton("prompt 2");
        radio3 = new JRadioButton("prompt 3");
        bgroup = new ButtonGroup();
        checkBox = new JCheckBox("Change prompt automatically");
        promptBox = new JTextArea(prompt);
        typeBox = new JTextArea(typedText);
        buttons = new ArrayList<JButton>();
        wrongLabel = new JLabel("Wrong!");
        numErrorsLabel = new JLabel("Errors: ");
        accuracyLabel = new JLabel("Accuracy: ");
        wpmLabel = new JLabel("Speed: ");

        promptBox.setSize(400, 100);
        promptBox.setLocation(200, 100);
        promptBox.setFocusable(false);
        promptBox.setBackground(null);
        promptBox.setLineWrap(true);
        promptBox.setWrapStyleWord(true);

        typeBox.setSize(400, 100);
        typeBox.setLocation(200, 200);
        typeBox.setFocusable(false);
        typeBox.setLineWrap(true);
        typeBox.setWrapStyleWord(true);

        wrongLabel.setSize(100, 50);
        wrongLabel.setLocation(50, 50);
        wrongLabel.setVisible(false);

        numErrorsLabel.setSize(100, 50);
        numErrorsLabel.setLocation(500, 50);
        numErrorsLabel.setVisible(true);

        accuracyLabel.setSize(200, 50);
        accuracyLabel.setLocation(200, 150);
        accuracyLabel.setVisible(false);

        wpmLabel.setSize(150, 50);
        wpmLabel.setLocation(600, 50);
        wpmLabel.setVisible(true);

        radio1.setSize(100, 50);
        radio1.setLocation(50, 100);
        radio1.addItemListener(this);
        radio1.setSelected(true);
        radio1.setFocusable(false);

        radio2.setSize(100, 50);
        radio2.setLocation(50, 150);
        radio2.addItemListener(this);
        radio2.setFocusable(false);

        radio3.setSize(100, 50);
        radio3.setLocation(50, 200);
        radio3.addItemListener(this);
        radio3.setFocusable(false);

        checkBox.setSize(400, 50);
        checkBox.setLocation(200, 300);
        //checkBox.addItemListener(this);
        checkBox.setFocusable(false);
        checkBox.setSelected(true);

        Container pane = getContentPane();
        pane.add(promptBox);
        pane.add(typeBox);
        pane.add(wrongLabel);
        pane.add(numErrorsLabel);
        pane.add(radio1);
        pane.add(radio2);
        pane.add(radio3);
        pane.add(accuracyLabel);
        pane.add(wpmLabel);
        pane.add(checkBox);
        addButtons();
        addSpacebar();

        bgroup.add(radio1);
        bgroup.add(radio2);
        bgroup.add(radio3);

        setVisible(true);
        setSize(WIDTH, LENGTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //public JButton keyToButton(
    public void addButtons() {
        for (int i = 0; i < qwerty.length()-1; ++i) {
            buttons.add(new JButton(Character.toString(qwerty.charAt(i))));
            // new Integer('a' + i).toString()
            buttons.get(i).setSize(50, 50);
            setPosition(i);
            this.getContentPane().add(buttons.get(i));
            buttons.get(i).setFocusable(false);
        }
    }
        
    public void addSpacebar() {
        JButton spacebar = new JButton();
        buttons.add(spacebar);
        spacebar.setSize(250, 50);
        spacebar.setLocation(200, 500);
        this.getContentPane().add(spacebar);
    }

    public void setPosition(int i) {
        if (i <= 9) {
            buttons.get(i).setLocation(150 + 50*i, 350);
        }
        else if (i >= 10 && i < rowBreak2) {
            buttons.get(i).setLocation(170 + 50*(i-rowBreak1), 400);
        }
        else if (i >= rowBreak2) {
            buttons.get(i).setLocation(190 + 50*(i-rowBreak2), 450);
        }
        else {
            System.out.println("error\n");
        }
    }

    public void setButtonColor(KeyEvent key, int i) {
        if (key.getKeyChar() == prompt.charAt(currentChar)) {
            buttons.get(i).setBackground(Color.green);
        }
        else if (key.getKeyChar() != prompt.charAt(currentChar)) {
            buttons.get(i).setBackground(Color.red);
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {
        System.out.println("you typed a key\n");
        int i = qwerty.indexOf(e.getKeyChar());
        System.out.println(e.getKeyChar());
        System.out.println(i);
        setButtonColor(e, i);
        System.out.println("you typed a key\n");
    }


    @Override
    public void keyReleased (KeyEvent e) {
        int i = qwerty.indexOf(e.getKeyChar());
        buttons.get(i).setBackground(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        numKeysTyped++;
        if (e.getKeyChar() == prompt.charAt(currentChar)) {
            wrongLabel.setVisible(false);
            typedText+= e.getKeyChar();
            typeBox.setText(typedText);
            ++currentChar;
        }
        else if (e.getKeyChar() != prompt.charAt(currentChar)) {
            System.out.println("wrong!");
            wrongLabel.setVisible(true);
            ++numErrors;
            numErrorsLabel.setText("Errors: " + numErrors);
            addError(prompt.charAt(currentChar));
        }
        calculateWPM();
        if (currentChar == prompt.length() && checkBox.isSelected()) {
            System.out.println("end of prompt\n");
            endTime = System.nanoTime();
            ++promptNum;
            if (promptNum < 3) {
                selectRadioButton(promptNum);
            }
            else {
                displayFinalScreen();
            }
        }
    }

    public void calculateWPM() {
        double timeElapsed = System.nanoTime() - startTime;
        int numWords = typedText.length() - 
                                typedText.replaceAll(" ", "").length() - 1;
        double wpm = numWords / (timeElapsed / 1000000000) * 60; 
        wpmLabel.setText("Speed: " + new DecimalFormat("#00.00").format(wpm) 
                                                                  + " wpm");
    }

    public void addError(char missedChar) {
        int errorFreq = errors.containsKey(missedChar) ? errors.get(missedChar) : 0;
        errors.put(missedChar, errorFreq + 1); 
    }

    public void displayErrors() {
        
    }

    public void displayFinalScreen() {
        promptBox.setVisible(false);
        accuracyLabel.setVisible(true);
        double accuracy = 100 - ((double)numErrors * 100 / numKeysTyped);
        accuracyLabel.setText("Accuracy: " + new DecimalFormat("#0.00").format(accuracy) + "%");
    }

    public void changePrompt(int n) {
        System.out.println("change to prompt" + n);
        promptNum = n;
        prompt = prompts[promptNum];
        promptBox.setText(prompt);
        currentChar = 0;
        resetTypeBox();
        startTime = System.nanoTime();
    }

    public void resetTypeBox() {
        typedText = "> ";
        typeBox.setText(typedText);
    }

    public void selectRadioButton(int num) {
        System.out.println("select radio button");
        switch (num) {
            case 0: radio1.setSelected(true);
                    break;
            case 1: radio2.setSelected(true);
                    break;
            case 2: radio3.setSelected(true);
                    break;
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (radio1.isSelected())  {
            changePrompt(0);
        }
        else if (radio2.isSelected())  {
            changePrompt(1);
        }
        else if (radio3.isSelected())  {
            changePrompt(2);
        }
        System.out.println("itemlistener done");
    }
}






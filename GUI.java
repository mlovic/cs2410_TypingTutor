import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI extends JFrame implements KeyListener, ItemListener {
    private final int WIDTH = 800, LENGTH = 600;

    private JTextArea promptBox, typeBox;
    private JLabel wrongLabel, numErrorsLabel;
    private JRadioButton radio1, radio2, radio3;
    private ButtonGroup bgroup;
    private java.util.List<JButton> buttons;
    private String qwerty, currentp, typedText;
    private String prompts[];
    private int cc, rowBreak1, rowBreak2, numErrors, currentpNum;

    public GUI() {
        this.setLayout(null);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();

        qwerty = "qwertyuiopasdfghjkl;'zxcvbnm,./ ";
        prompts = new String[] { "prompt one",
                      "prompt two",
                      "prompt three" };
        currentpNum = 0;
        currentp = prompts[currentpNum];
        typedText = "> ";
        rowBreak1 = 10;
        rowBreak2 = 21;
        numErrors = 0;
        radio1 = new JRadioButton("prompt 1");
        radio2 = new JRadioButton("prompt 2");
        radio3 = new JRadioButton("prompt 3");
        bgroup = new ButtonGroup();
        promptBox = new JTextArea(currentp);
        typeBox = new JTextArea(typedText);
        buttons = new ArrayList<JButton>();
        wrongLabel = new JLabel("Wrong!");
        numErrorsLabel = new JLabel("Errors: ");
        cc = 0;

        promptBox.setSize(400, 100);
        promptBox.setLocation(200, 100);
        promptBox.setFocusable(false);
        promptBox.setBackground(null);

        typeBox.setSize(400, 100);
        typeBox.setLocation(200, 200);
        typeBox.setFocusable(false);

        wrongLabel.setSize(100, 50);
        wrongLabel.setLocation(50, 50);
        wrongLabel.setVisible(false);

        numErrorsLabel.setSize(100, 50);
        numErrorsLabel.setLocation(500, 50);
        numErrorsLabel.setVisible(true);

        radio1.setSize(100, 50);
        radio1.setLocation(50, 100);
        radio1.addItemListener(this);
        radio1.setSelected(true);

        radio2.setSize(100, 50);
        radio2.setLocation(50, 150);
        radio2.addItemListener(this);

        radio3.setSize(100, 50);
        radio3.setLocation(50, 200);
        radio3.addItemListener(this);

 //List<String> slist = new ArrayList<String>();
  //slist.add(new String("Java")); 

        Container pane = getContentPane();
        pane.add(promptBox);
        pane.add(typeBox);
        pane.add(wrongLabel);
        pane.add(numErrorsLabel);
        pane.add(radio1);
        pane.add(radio2);
        pane.add(radio3);
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
        if (key.getKeyChar() == currentp.charAt(cc)) {
            buttons.get(i).setBackground(Color.green);
        }
        else if (key.getKeyChar() != currentp.charAt(cc)) {
            buttons.get(i).setBackground(Color.red);
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {
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
        if (e.getKeyChar() == currentp.charAt(cc)) {
            wrongLabel.setVisible(false);
            typedText+= e.getKeyChar();
            typeBox.setText(typedText);
            ++cc;
        }
        else if (e.getKeyChar() != currentp.charAt(cc)) {
            System.out.println("wrong!");
            wrongLabel.setVisible(true);
            ++numErrors;
            numErrorsLabel.setText("Errors: " + numErrors);
        }
        if (cc == currentp.length()) {
            System.out.println("end of prompt\n");
            ++currentpNum;
            if (currentpNum < 3) {
                selectRadioButton(currentpNum);
            }
            else {
                displayFinalScreen();
            }
        }
    }

    public void displayFinalScreen() {
        promptBox.setText("Final screen");
    }

    public void changePrompt(int promptNum) {
        System.out.println("change prompt");
        currentp = prompts[promptNum];
        //radio2.setSelected(true);
        promptBox.setText(currentp);
        cc = 0;
        resetTypeBox();
        //selectRadioButton(promptNum);
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
    }
}






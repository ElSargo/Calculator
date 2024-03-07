package org.calc;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public JTextField input = new JTextField();
    JLabel output = new JLabel();

    BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    public Display(){
        input.setFont(new Font("mono", Font.PLAIN, 50));
        output.setFont(new Font("mono", Font.PLAIN, 30));

        input.setText("( 10 )");
        output.setText("0.0");


        add(input);
        add(output);
        setLayout(layout);

        setPreferredSize(new Dimension(100, 100));
    }

    public void setInput(String s){
        input.setText(s);
    }

    public String getInput(){
        return input.getText();
    }

    public void setOutput(String s){
        output.setText(s);
    }

    public void setCaretPosition(int i){
        input.setCaretPosition(i);
    }

    public int getCaretPosition(){
        return input.getCaretPosition();
    }
}

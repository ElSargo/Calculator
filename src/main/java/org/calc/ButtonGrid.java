package org.calc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ButtonGrid extends JPanel {

    public ButtonGrid(ArrayList<CalcButton> buttons) {
        GridLayout layout = new GridLayout(6,4);
        setLayout(layout);
        setPreferredSize(new Dimension(500, 600));
        for (CalcButton calcButton : buttons) {
            calcButton.setFont(new Font("mono", Font.PLAIN, 50));

            add(
                    calcButton
            );
        }
    }
}

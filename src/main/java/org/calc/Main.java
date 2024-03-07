package org.calc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Main {

    static String[] buttonLayout = new String[]{"1", "2", "3", "-", "4", "5", "6", "+", "7", "8", "9", "*", "0", "(", ")", "/", "π", "i", "e", ",", "^", "log"};

    public static void main(String[] args) {
        Display display = new Display();
        State state = new State(display);
        display.input.addKeyListener(new KeyStrokeListener(state));


        ArrayList<CalcButton> buttons = new ArrayList<>();
        for (String label : buttonLayout) {
            buttons.add(state.makeButton(label));
        }
        buttons.add(state.clearButton("C"));
        buttons.add(state.evalButton("="));

        JFrame frame = new JFrame("Main");
        Container ctx = frame.getContentPane();
        BoxLayout layout = new BoxLayout(ctx, BoxLayout.Y_AXIS);
        frame.setLayout(layout);
        frame.setPreferredSize(new Dimension(500, 750));

        ctx.add(display);
        ctx.add(new ButtonGrid(buttons));


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}


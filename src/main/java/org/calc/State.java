package org.calc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class State {
    String displayText;
    Display display;
    int cursor;

    public State(Display d){
        cursor = 0;
        displayText = "";
        display = d;
    }

    void updateText(){
        display.setInput(displayText);
        display.setCaretPosition( Math.min( cursor, displayText.length() ));
    }

    void extractText(){
       displayText =  display.getInput();
       cursor = display.getCaretPosition();
    }

    public CalcButton makeButton(String label) {
        ButtonListener listener = new ButtonListener(new AddTextCallBack(this, label), this);
        CalcButton button = new CalcButton(label);
        button.addActionListener(listener);
        return button;
    }

    public CalcButton clearButton(String label){
        ButtonListener listener = new ButtonListener(new ClearCallBack(this), this);
        CalcButton button = new CalcButton(label);
        button.addActionListener(listener);
        return button;
    }

    public CalcButton evalButton(String label){
        ButtonListener listener = new ButtonListener(new EvalCallBack(this), this);
        CalcButton button = new CalcButton(label);
        button.addActionListener(listener);
        return button;
    }

    public void addText(String text){
        extractText();
        String beforeCursor = displayText.substring(0,cursor);
        String afterCursor = displayText.substring(cursor);
        displayText = beforeCursor + text + afterCursor;
        cursor += text.length();
        System.out.println("Add " + cursor);

        updateText();
    }

    public void onCallback(){
        extractText();
        displayText = displayText.replaceAll("([pP])([iI])", "π");
        updateText();
        display.input.repaint();
    }


    public void clear() {
        displayText = "";
        cursor = 0;
        updateText();
    }

    public void eval() {
        extractText();

        Tokenizer t = null;
        try {
            t = new Tokenizer(displayText);
        } catch (Tokenizer.UnmatchedParen e) {
            System.out.println("Unmatched paren");
        }

        assert t != null;
        display.setOutput(t.eval().prettyPrint());

    }


    static class ButtonListener implements ActionListener {
        StateCallback callback;
        State state;

        public ButtonListener(StateCallback s, State st){
            callback = s;
            state = st;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            callback.call();
            state.onCallback();
        }
    }
}


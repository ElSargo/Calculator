package org.calc;

public class AddTextCallBack implements StateCallback {
    State state;
    String text;

    AddTextCallBack(State s, String t) {
        text = t;
        state = s;
    }

    @Override
    public void call() {
        state.addText(text);
    }
}

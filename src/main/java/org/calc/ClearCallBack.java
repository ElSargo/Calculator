package org.calc;

public class ClearCallBack implements StateCallback {
    State state;
    public ClearCallBack(State s) {
        state = s;
    }

    @Override
    public void call() {
        state.clear();
    }
}

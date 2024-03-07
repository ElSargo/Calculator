package org.calc;

public class EvalCallBack implements StateCallback {
    State state;
    public EvalCallBack(State s) {
        state = s;
    }


    @Override
    public void call() {
        state.eval();
    }
}

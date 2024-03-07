package org.calc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStrokeListener implements KeyListener {
    State state;

    public KeyStrokeListener(State s){
        state = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        state.onCallback();
        if (e.getKeyCode() == KeyEvent.VK_ENTER ){
            state.eval();
        }
    }
}

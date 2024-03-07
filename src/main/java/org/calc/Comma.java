package org.calc;

public class Comma extends Operand {

    @Override
    public Complex eval() {
        return new Complex(0.0,0.0);
    }

    @Override
    public Boolean matches(Character c) {
        return c == ',';
    }
}

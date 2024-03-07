package org.calc;

public class LeftParen extends Operand {

    @Override
    public Complex eval() {
        return new Complex(0.0,0.0);
    }

    @Override
    public Boolean matches(Character c) {
        return text.isEmpty() && c == '(';
    }

    @Override
    public String toString() {
        return "[ ) ]";
    }
}


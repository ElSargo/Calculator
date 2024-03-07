package org.calc;

public class Div extends Operand {
    Operand numerator;
    Operand denominator;

    @Override
    public Complex eval() {

        return numerator.eval().div(denominator.eval());

    }

    @Override
    public Boolean matches(Character c) {
        return c == '/';
    }
}

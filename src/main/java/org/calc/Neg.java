package org.calc;

public class Neg extends Operand {
    Operand child;

    @Override
    public Complex eval() {

        Complex result = child.eval();

        return new Complex(
                -result.real,
                -result.img
        );
    }

    @Override
    public Boolean matches(Character c) {
        return c == '-';
    }
}


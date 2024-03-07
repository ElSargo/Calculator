package org.calc;


public class Mul extends Operand {
    Operand left;
    Operand right;

    @Override
    public Complex eval() {
        return left.eval().mul(right.eval());

    }

    @Override
    public Boolean matches(Character c) {
        return c == '*';
    }
}







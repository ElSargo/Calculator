package org.calc;

import java.util.Arrays;

public class ParenExpr extends Operand {

    Operand[] children;

    @Override
    public Complex eval() {
        return children[0].eval();
    }

    @Override
    public Boolean matches(Character c) {
        return false;
    }

    @Override
    public String toString() {
        return "[Paren expr" + Arrays.toString(children) + " ]";
    }
}

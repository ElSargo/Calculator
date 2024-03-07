package org.calc;

import java.util.Objects;

public class Log extends Operand {
    ParenExpr expr;

    @Override
    public Complex eval() {
        if (expr.children.length == 1) {
            return expr.children[0].eval().ln();
        } else {
            return expr.children[1].eval().ln().div(
                    expr.children[0].eval().ln()
            );
        }

    }

    @Override
    public Boolean matches(Character c) {
        return (Objects.equals(text, "") && c == 'l') || (Objects.equals(text, "l") && c == 'o') || (Objects.equals(text, "lo") && c == 'g');
    }
}

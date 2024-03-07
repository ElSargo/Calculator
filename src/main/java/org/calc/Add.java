package org.calc;

public class Add extends Operand {
    public Operand a;
    public Operand b;


    @Override
    public Complex eval() {

        Complex res_a = a.eval();
        Complex res_b = b.eval();

        return new Complex(
                res_a.getReal() + res_b.getReal(),
                res_a.getImg() + res_b.getImg()
        );
    }

    @Override
    public Boolean matches(Character c){
        return c == '+';
    }
}


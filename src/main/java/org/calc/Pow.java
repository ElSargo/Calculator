package org.calc;

public class Pow extends Operand {
    Operand base;
    Operand exponent;

    @Override
    public Complex eval() {

        Complex res_base = base.eval();
        Complex res_exponent = exponent.eval();

        double base_magnitude = Math.sqrt(res_base.real * res_base.real + res_base.img * res_base.img);

        double base_argument = Math.atan2(res_base.img, res_base.real);

        return res_exponent.mul(new Complex(Math.log(base_magnitude), base_argument)).exp();
    }

    @Override
    public Boolean matches(Character c) {
        return c == '^';
    }


}

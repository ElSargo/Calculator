package org.calc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Complex extends Operand {
    public double real;
    public double img;
    static NumberFormat formatter = new DecimalFormat("#0.000000");


    public double getReal() {
        return real;
    }

    public double getImg() {
        return img;
    }

    public Complex(double r, double i) {
        real = r;
        img = i;
    }

    @Override
    public Complex eval() {
        if (!text.isEmpty()) {
            int num_i = (int) text.chars().filter(ch -> ch == 'i').count();
            int num_pi = (int) text.chars().filter(ch -> ch == 'π').count();
            int num_e = (int) text.chars().filter(ch -> ch == 'e').count();

            String[] a = text.split("[ieπ]");

            double magnitude = Arrays.stream(a).map(Double::parseDouble).reduce(1.0, (x, b) -> x * b) * (Math.pow(Math.E, num_e) * Math.pow(Math.PI, num_pi));

            switch (num_i % 4) {

                case 0: // 0 i
                    real = magnitude;
                    img = 0.0;
                    break;
                case 1: // 1 i
                    real = 0.0;
                    img = magnitude;
                    break;
                case 2: // 2i
                    real = -magnitude;
                    img = 0.0;
                    // 3i
                    break;
                case 3:
                    real = 0.0;
                    img = -magnitude;
                    break;
            }
        }
        text = "";
        return this;
    }

    @Override
    public Boolean matches(Character c) {
        return Character.isDigit(c) || c == '.' || c == 'i' || c == 'π' || c == 'e';
    }

    @Override
    public String toString() {
        String f_real = formatter.format(real);
        String f_img = formatter.format(img);
        if (text.isEmpty()) {
            return String.format("%s+%si", f_real, f_img);
        } else {
            return String.format("[Complex: %s + %si, \"%s\" ]", f_real, f_img, text);
        }
    }

    public Complex mul(Complex rhs) {
        return new Complex(real * rhs.real - img * rhs.img, img * rhs.real + real * rhs.img);
    }

    public Complex exp() {
        double m = Math.exp(real);
        return new Complex(m * Math.cos(img), m * Math.sin(img));
    }

    public double magnitude() {
        return Math.sqrt(real * real + img * img);
    }

    public double argument() {
        return Math.atan2(img, real);
    }

    public Complex ln() {
        double magnitude = magnitude();

        double argument = argument();

        return new Complex(Math.log(magnitude), argument);
    }

    public Complex div(Complex rhs){
        double a = real, b = img, c = rhs.real, d = rhs.img;

        return new Complex(
                (a * c + b * d) / (c * c + d * d),
                (b * c - a * d) / (c * c + d * d)
        );
    }

    public String prettyPrint() {
        String output = "";
        Boolean real_not_zero = Math.abs(real) > 0.00001;
        Boolean img_not_zero = Math.abs(img) > 0.00001;

        if (real_not_zero){
             output += trimZeros(formatter.format(real));
        }
        if (real_not_zero && img_not_zero){
            output += "+";
        }
        if (img_not_zero){
            output += trimZeros(formatter.format(img) )+ "i";
        }
        return output;
    }

    private String trimZeros(String s){
        StringBuilder output = new StringBuilder();
        char[] chars = s.toCharArray();
        int i = chars.length-1;
        for (;i>0;i-- ){
            if (chars[i] != '0'){
                if (chars[i] != '.'){
                    i += 1;
                }
                break;
            }
        }
        for (int k=0;k<i;k++){
            output.append(chars[k]);
        }

        return output.toString();
    }
}

package org.calc;

public abstract class Operand implements Cloneable{

    String text = "";

    public abstract Complex eval();

    public Operand addChar(Character c) {
        text += c;
        return this;
    }

    public abstract Boolean matches(Character c);


    @Override
    public Operand clone() {
        try {
            return (Operand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


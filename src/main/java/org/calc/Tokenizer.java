package org.calc;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    ArrayList<Operand> tokens = new ArrayList<>();
    Operand root;


    Operand[] operands = new Operand[]{new RightParen(), new LeftParen(), new Comma(), new Add(), new Complex(0, 0), new Mul(), new Div(), new Pow(), new Log(), new Neg()};

    Tokenizer(String s) throws UnmatchedParen {
        Operand currentToken = null;
        for (Character c : s.toCharArray()) {
            if (currentToken == null) {
                currentToken = createNextMatchingToken(c);

            } else if (currentToken.matches(c)) {
                currentToken.addChar(c);
            } else {
                tokens.add(currentToken);
                currentToken = createNextMatchingToken(c);
            }
        }
        tokens.add(currentToken);

        System.out.println(tokens);
        insertHiddenOps();
        System.out.println(tokens);


        Operand[] arr = new Operand[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) arr[i] = tokens.get(i);

        root = scan(arr);
    }

    private void insertHiddenOps() {
        for (int i = 1; i < tokens.size(); i++) {
            Class<? extends Operand> l_class = tokens.get(i - 1).getClass();
            // No need to check ParenExpr as they aren't created yet
            if (l_class == RightParen.class || l_class == Complex.class) {
                Class<? extends Operand> m_class = tokens.get(i).getClass();

                if (m_class == LeftParen.class || m_class == Complex.class) {
                    tokens.add(i, new Mul());
                }

                if (m_class == Neg.class) {
                    Class<? extends Operand> r_class = tokens.get(i).getClass();
                    if (r_class == LeftParen.class || r_class == Complex.class) {
                        tokens.add(i, new Add());
                    }
                }

            }


        }
    }

    public Complex eval() {
        return root.eval();
    }

    public Operand scan(Operand[] slice) throws UnmatchedParen {
        if (slice.length == 1) {
            return slice[0];
        }
        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];
            if (opp.getClass().equals(LeftParen.class)) {
                // Parse out the paren, then create a new array with the outside of the paren
                // around it

                ArrayList<Integer> sections = matchParen(slice, i);
                //((Paren) opp).
                Operand[] children = new Operand[sections.size()];
                int start = i;
                for (int k = 0; k < sections.size(); k++) {
                    int end = sections.get(k);
                    children[k] = scan(Arrays.copyOfRange(slice, start + 1, end));
                    start = end;
                }
                // ((Paren) opp).children = children;
                ParenExpr expr = new ParenExpr();
                expr.children = children;
                Operand[] left = Arrays.copyOfRange(slice, 0, i);
                Operand[] right = Arrays.copyOfRange(slice, start + 1, slice.length);

                Operand[] result = new Operand[left.length + right.length + 1];
                System.arraycopy(left, 0, result, 0, left.length);
                result[left.length] = expr;
                for (int j = 0; j < right.length; j++) {
                    result[j + left.length + 1] = right[j];
                }

                return scan(result);
            }
        }

        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];
            if (opp.getClass().equals(Neg.class)) {
                if (i == 0) {
                    ((Neg) opp).child = scan(Arrays.copyOfRange(slice, i + 1, slice.length));
                    return opp;
                }
                Add add = new Add();

                add.a = scan(Arrays.copyOfRange(slice, 0, i));
                ((Neg) opp).child = scan(Arrays.copyOfRange(slice, i + 1, slice.length));

                add.b = opp;
                return add;
            }
        }

        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];

            if (opp.getClass().equals(Add.class)) {
                ((Add) opp).a = scan(Arrays.copyOfRange(slice, 0, i));
                ((Add) opp).b = scan(Arrays.copyOfRange(slice, i + 1, slice.length));
                return opp;
            }
        }

        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];
            if (opp.getClass().equals(Mul.class) || opp.getClass().equals(Div.class)) {
                Operand left = scan(Arrays.copyOfRange(slice, 0, i));
                Operand right = scan(Arrays.copyOfRange(slice, i + 1, slice.length));
                if (opp.getClass().equals(Mul.class)) {
                    ((Mul) opp).left = left;
                    ((Mul) opp).right = right;
                } else {
                    ((Div) opp).numerator = left;
                    ((Div) opp).denominator = right;
                }
                return opp;
            }
        }

        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];
            if (opp.getClass().equals(Pow.class)) {
                Operand left = scan(Arrays.copyOfRange(slice, 0, i));
                Operand right = scan(Arrays.copyOfRange(slice, i + 1, slice.length));
                ((Pow) opp).base = left;
                ((Pow) opp).exponent = right;
                return opp;
            }
        }


        for (int i = 0; i < slice.length; i++) {
            Operand opp = slice[i];
            if (opp.getClass().equals(Log.class)) {
                ((Log) opp).expr = (ParenExpr) slice[i + 1];
                return opp;
            }
        }

        return slice[0];

    }

    private ArrayList<Integer> matchParen(Operand[] slice, int start) throws UnmatchedParen {
        int level = 0;
        ArrayList<Integer> sections = new ArrayList<>();
        for (int i = start + 1; i < slice.length; i++) {
            Operand token = slice[i];
            if (token.getClass() == Comma.class) {
                sections.add(i);
            }
            if (token.getClass() == RightParen.class) {
                if (level == 0) {
                    sections.add(i);
                    return sections;
                } else {
                    level -= 1;
                }
            } else if (token.getClass() == LeftParen.class) {
                level += 1;
            }
        }
        throw new UnmatchedParen();
    }

    public static class UnmatchedParen extends Exception {

    }

    private Operand createNextMatchingToken(Character c) {
        for (Operand opp : operands) {
            if (opp.matches(c)) {
                return opp.clone().addChar(c);
            }
        }
        return null;
    }
}

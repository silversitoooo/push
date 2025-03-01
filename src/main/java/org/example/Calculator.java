package org.example;

import java.util.StringTokenizer;

/**
 * Calculadora que convierte expresiones infijas a posfijas y las evalúa.
 * Implementación del patrón Singleton.
 */
public class Calculator {
    private static Calculator instance;
    private IStack<String> stack;

    public Calculator() {}

    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    public void setStack(IStack<String> stack) {
        this.stack = stack;
    }

    public String convertToPostfix(String infixExpr) {
        if (stack == null) {
            throw new IllegalStateException("La pila no ha sido inicializada");
        }
        if (infixExpr == null || infixExpr.isEmpty()) {
            throw new IllegalArgumentException("Expresión infija inválida");
        }

        StringBuilder postfix = new StringBuilder();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < infixExpr.length(); i++) {
            char c = infixExpr.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    postfix.append(number).append(' ');
                    number.setLength(0);
                }

                if (c == '(') {
                    stack.push(String.valueOf(c));
                } else if (c == ')') {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        postfix.append(stack.pop()).append(' ');
                    }
                    if (!stack.isEmpty()) stack.pop();
                } else if (isOperator(c)) {
                    while (!stack.isEmpty() && !stack.peek().equals("(") &&
                            getPrecedence(stack.peek().charAt(0)) >= getPrecedence(c)) {
                        postfix.append(stack.pop()).append(' ');
                    }
                    stack.push(String.valueOf(c));
                } else if (c != ' ') {
                    throw new IllegalArgumentException("Carácter inválido en la expresión: " + c);
                }
            }
        }

        if (number.length() > 0) {
            postfix.append(number).append(' ');
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    public double evaluatePostfix(String postfixExpr) {
        if (stack == null) {
            throw new IllegalStateException("La pila no ha sido inicializada");
        }
        if (postfixExpr == null || postfixExpr.isEmpty()) {
            throw new IllegalArgumentException("Expresión posfija inválida");
        }

        IStack<Double> evalStack = new ArrayListStack<>();
        String[] tokens = postfixExpr.split("\\s+");
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                evalStack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (evalStack.size() < 2) {
                    throw new IllegalStateException("Expresión posfija mal formada");
                }

                double operand2 = evalStack.pop();
                double operand1 = evalStack.pop();

                double result = performOperation(token.charAt(0), operand1, operand2);
                evalStack.push(result);
            }
        }

        if (evalStack.size() != 1) {
            throw new IllegalStateException("Expresión posfija inválida");
        }

        return evalStack.pop();
    }

    private double performOperation(char operator, double op1, double op2) {
        return switch (operator) {
            case '+' -> op1 + op2;
            case '-' -> op1 - op2;
            case '*' -> op1 * op2;
            case '/' -> {
                if (op2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                yield op1 / op2;
            }
            case '^' -> Math.pow(op1, op2);
            default -> throw new IllegalArgumentException("Operador desconocido: " + operator);
        };
    }

    private int getPrecedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }
}
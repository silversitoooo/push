package edu.cscc.topics.stack;

import java.util.StringTokenizer;

import org.example.ArrayListStack;
import org.example.IStack;

/**
 * Calculadora que convierte expresiones infijas a posfijas y las evalúa.
 * Implementación del patrón Singleton.
 */
public class Calculator {
    private static Calculator instance;
    private IStack<Character> operatorStack;
    private IStack<Double> operandStack;

    public Calculator() {
        // Constructor público para pruebas
    }

    /**
     * Obtiene la instancia única de la calculadora
     * @return La instancia de la calculadora
     */
    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    /**
     * Establece la pila a utilizar para los operadores
     * @param stack La pila de strings (se adaptará para operadores)
     */
    public void setStack(IStack<String> stack) {
        // Adaptador para convertir IStack<String> a IStack<Character>
        this.operatorStack = new IStack<>() {
            @Override
            public void push(Character item) {
                stack.push(item.toString());
            }

            @Override
            public Character pop() {
                return stack.pop().charAt(0);
            }

            @Override
            public Character peek() {
                return stack.peek().charAt(0);
            }

            @Override
            public boolean isEmpty() {
                return stack.isEmpty();
            }

            @Override
            public int size() {
                return stack.size();
            }
        };

        // También inicializamos la pila de operandos
        this.operandStack = new ArrayListStack<>();
    }

    /**
     * Convierte una expresión infija a notación posfija
     * @param infix La expresión en notación infija
     * @return La expresión en notación posfija
     */
    public String convertToPostfix(String infix) {
        if (operatorStack == null) {
            throw new IllegalStateException("Operator stack has not been set");
        }

        StringBuilder postfix = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(infix, "+-*/() ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (isNumeric(token)) {
                postfix.append(token);
            } else if (token.equals("(")) {
                operatorStack.push('(');
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Eliminar el paréntesis izquierdo
                }
            } else if (isOperator(token.charAt(0))) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                        precedence(operatorStack.peek()) >= precedence(token.charAt(0))) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(token.charAt(0));
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    /**
     * Evalúa una expresión en notación posfija
     * @param postfix La expresión en notación posfija
     * @return El resultado de la evaluación
     */
    public double evaluatePostfix(String postfix) {
        if (operandStack == null) {
            throw new IllegalStateException("Operand stack has not been initialized");
        }

        // Limpiamos la pila de operandos antes de empezar
        while (!operandStack.isEmpty()) {
            operandStack.pop();
        }

        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);

            if (Character.isDigit(ch)) {
                operandStack.push((double) (ch - '0'));
            } else if (isOperator(ch)) {
                if (operandStack.size() < 2) {
                    throw new IllegalStateException("Invalid postfix expression");
                }
                double val2 = operandStack.pop();
                double val1 = operandStack.pop();

                switch (ch) {
                    case '+': operandStack.push(val1 + val2); break;
                    case '-': operandStack.push(val1 - val2); break;
                    case '*': operandStack.push(val1 * val2); break;
                    case '/':
                        if (val2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        operandStack.push(val1 / val2);
                        break;
                }
            }
        }

        if (operandStack.isEmpty()) {
            throw new IllegalStateException("Invalid postfix expression");
        }

        return operandStack.pop();
    }

    /**
     * Determina la precedencia de un operador
     * @param op El operador
     * @return La precedencia (mayor valor indica mayor precedencia)
     */
    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Verifica si un carácter es un operador
     * @param c El carácter a verificar
     * @return true si es operador, false en caso contrario
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * Verifica si una cadena es un número
     * @param str La cadena a verificar
     * @return true si es número, false en caso contrario
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
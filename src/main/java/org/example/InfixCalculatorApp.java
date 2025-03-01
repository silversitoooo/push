package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación principal para la calculadora de expresiones infijas
 */
public class InfixCalculatorApp {
    public static void main(String[] args) {
        InfixCalculatorApp app = new InfixCalculatorApp();
        List<String> infixExpressions = app.readExpressionsFromFile("expression.txt");

        if (infixExpressions.isEmpty()) {
            System.out.println("No se encontraron expresiones en el archivo.");
            return;
        }

        // Obtenemos las opciones de pila y lista del usuario
        StackChoice stackChoice = app.getUserStackChoice();

        // Creamos la pila según la elección del usuario
        IStack<String> stack;
        if (stackChoice.stackType.equals("List")) {
            // Si eligió una lista, creamos la pila con el tipo de lista elegido
            IList<String> list = StackFactory.createList(stackChoice.listType);
            stack = new ListStack<>(list);
        } else {
            // Si eligió ArrayList o Vector, usamos el método existente
            stack = StackFactory.createStack(stackChoice.stackType);
        }

        Calculator calculator = Calculator.getInstance();
        calculator.setStack(stack);
        
        System.out.println("\nProcesando expresiones:");
        System.out.println("------------------------");
        
        for (int i = 0; i < infixExpressions.size(); i++) {
            String infixExpr = infixExpressions.get(i);
            System.out.println("\nExpresión " + (i + 1) + ": " + infixExpr);
            
            String postfixExpr = calculator.convertToPostfix(infixExpr);
            System.out.println("Expresión posfija: " + postfixExpr);
            
            double result = calculator.evaluatePostfix(postfixExpr);
            System.out.println("Resultado: " + result);
        }
    }

    /**
     * Lee múltiples expresiones desde un archivo
     * @param filename El nombre del archivo
     * @return Lista de expresiones leídas
     */
    private List<String> readExpressionsFromFile(String filename) {
        List<String> expressions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    expressions.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            // Expresión predeterminada en caso de error
            expressions.add("3+4*2/(1-5)");
        }
        return expressions;
    }

    /**
     * Clase auxiliar para almacenar la elección de pila y lista
     */
    private static class StackChoice {
        String stackType;
        String listType;

        StackChoice(String stackType, String listType) {
            this.stackType = stackType;
            this.listType = listType;
        }
    }

    /**
     * Obtiene la elección de pila y lista del usuario
     * @return Objeto con el tipo de pila y lista elegidos
     */
    private StackChoice getUserStackChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elige el tipo de pila:");
        System.out.println("1. ArrayList");
        System.out.println("2. Vector");
        System.out.println("3. Lista enlazada");
        System.out.print("Opción: ");
        int option = scanner.nextInt();

        String stackType = switch (option) {
            case 1 -> "ArrayList";
            case 2 -> "Vector";
            case 3 -> "List";
            default -> "ArrayList"; // Opción predeterminada
        };

        // Si eligió lista enlazada, preguntamos qué tipo de lista
        String listType = "SinglyLinkedList"; // valor predeterminado
        if (stackType.equals("List")) {
            System.out.println("Elige el tipo de lista:");
            System.out.println("1. Lista simplemente enlazada");
            System.out.println("2. Lista doblemente enlazada");
            System.out.print("Opción: ");
            int listOption = scanner.nextInt();

            listType = switch (listOption) {
                case 1 -> "SinglyLinkedList";
                case 2 -> "DoublyLinkedList";
                default -> "SinglyLinkedList"; // Opción predeterminada
            };
        }

        return new StackChoice(stackType, listType);
    }
}
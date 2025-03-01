package org.example;

import edu.cscc.topics.stack.Stack;

/**
 * Fábrica para crear diferentes tipos de pilas y listas
 */
public class StackFactory {
    /**
     * Crea una pila según el tipo especificado
     * @param type El tipo de pila a crear ("ArrayList" o "Vector")
     * @return La pila creada
     */
    public static IStack<String> createStack(String type) {
        switch (type) {
            case "ArrayList":
                return new org.example.ArrayListStack<>();
            case "Vector":
                return new VectorStack<>();
            case "SinglyLinkedList":
                return new ListStack<>(new SinglyLinkedList<>());
            case "DoublyLinkedList":
                return new ListStack<>(new DoublyLinkedList<>());
            default:
                throw new IllegalArgumentException("Unknown stack type: " + type);
        }
    }

    /**
     * Crea una lista según el tipo especificado
     * @param type El tipo de lista a crear ("SinglyLinkedList" o "DoublyLinkedList")
     * @return La lista creada
     */
    public static IList<String> createList(String type) {
        switch (type) {
            case "SinglyLinkedList":
                return new SinglyLinkedList<>();
            case "DoublyLinkedList":
                return new DoublyLinkedList<>();
            default:
                throw new IllegalArgumentException("Unknown list type: " + type);
        }
    }

    /**
     * Crea una pila para caracteres
     * @return La pila de caracteres
     */
    public Stack<Character> createCharacterStack() {
        return new StackAdapter<>(new VectorStack<>());
    }

    /**
     * Crea una pila para números
     * @return La pila de números
     */
    public Stack<Double> createDoubleStack() {
        return new StackAdapter<>(new VectorStack<>());
    }
}
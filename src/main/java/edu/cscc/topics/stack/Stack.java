package edu.cscc.topics.stack;

import java.util.ArrayList;

/**
 * Implementación genérica de una pila (stack)
 * @param <T> Tipo de elementos almacenados en la pila
 */
public class Stack<T> {
    private final ArrayList<T> elements;
    
    /**
     * Constructor
     */
    public Stack() {
        this.elements = new ArrayList<>();
    }
    
    /**
     * Añade un elemento a la pila
     * @param item El elemento a añadir
     */
    public void push(T item) {
        elements.add(item);
    }
    
    /**
     * Elimina y devuelve el elemento superior de la pila
     * @return El elemento superior
     * @throws IllegalStateException si la pila está vacía
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        return elements.remove(elements.size() - 1);
    }
    
    /**
     * Obtiene el elemento superior de la pila sin eliminarlo
     * @return El elemento superior
     * @throws IllegalStateException si la pila está vacía
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack");
        }
        return elements.get(elements.size() - 1);
    }
    
    /**
     * Verifica si la pila está vacía
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    /**
     * Obtiene el tamaño actual de la pila
     * @return número de elementos en la pila
     */
    public int size() {
        return elements.size();
    }
}
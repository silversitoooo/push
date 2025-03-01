package org.example;

import edu.cscc.topics.stack.Stack;

/**
 * Adaptador para convertir IStack en Stack
 * @param <T> Tipo de elementos en la pila
 */
public class StackAdapter<T> extends Stack<T> {
    private final IStack<T> stack;

    public StackAdapter(IStack<T> stack) {
        if (stack == null) {
            throw new IllegalArgumentException("Stack cannot be null");
        }
        this.stack = stack;
    }

    @Override
    public void push(T item) {
        stack.push(item);
    }

    @Override
    public T pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        return stack.pop();
    }

    @Override
    public T peek() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
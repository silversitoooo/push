

package edu.cscc.topics.stack;

/**
 * Implementación de la interfaz Stack
 */
public class StackImpl<T> extends Stack<T> {
    private final java.util.Stack<T> internalStack;

    public StackImpl() {
        this.internalStack = new java.util.Stack<>();
    }

    // Constructor adicional para inicializar con una stack existente
    public StackImpl(java.util.Stack<T> stack) {
        this.internalStack = (stack != null) ? stack : new java.util.Stack<>();
    }

    @Override
    public void push(T item) {
        internalStack.push(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        return internalStack.pop();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack");
        }
        return internalStack.peek();
    }

    @Override
    public boolean isEmpty() {
        return internalStack.isEmpty();
    }

    /**
     * Limpia todos los elementos de la pila
     */
    public void clear() {
        internalStack.clear();
    }

    /**
     * Obtiene el tamaño actual de la pila
     * @return número de elementos en la pila
     */
    public int size() {
        return internalStack.size();
    }
}

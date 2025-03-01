
import org.example.VectorStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Vector;

public class VectorStackTest {
    private VectorStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new VectorStack<>();
    }

    @Test
    @DisplayName("Prueba de push y pop")
    void testPushAndPop() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals(3, stack.size());
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Prueba de peek")
    void testPeek() {
        stack.push("A");
        stack.push("B");

        assertEquals("B", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    @DisplayName("Prueba de excepciones")
    void testExceptions() {
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
        assertThrows(IllegalStateException.class, () -> stack.pop());
        assertThrows(IllegalStateException.class, () -> stack.peek());
    }
}
package org.example;



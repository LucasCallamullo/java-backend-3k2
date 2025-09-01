package utils;
import org.junit.jupiter.api.*; // Importa anotaciones y clases de JUnit 5
import static org.junit.jupiter.api.Assertions.*; // Importa métodos de aserción


public class MyArrayTest {

    @Test
    void testAppendYSize() {
        MyArray<Integer> arr = new MyArray<>(2);
        arr.append(10);
        arr.append(20);
        assertEquals(2, arr.size());

        arr.append(30); // fuerza redimensionamiento
        assertEquals(3, arr.size());
    }

    @Test
    void testIterator() {
        MyArray<String> arr = new MyArray<>(2);
        arr.append("A");
        arr.append("B");

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        assertEquals("AB", sb.toString());
    }
}

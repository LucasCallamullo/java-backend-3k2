package utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Clase genérica <T>
public class MyArray<T> implements Iterable<T> {
    
    private T[] elements;   // arreglo de elementos genéricos
    private int size;       // capacidad actual del arreglo
    private int index;      // cantidad de elementos usados

    // Constructor
    /* 
     * ¿Qué hace @SuppressWarnings("unchecked")?
     * Le dice al compilador:
     * "Ya sé que este cast es inseguro en teoría, pero yo me hago 
     * responsable de que mi código lo use correctamente".
     */
    @SuppressWarnings("unchecked")
    public MyArray(int size) {
        // No se puede hacer new T[], entonces usamos Object[] y casteamos
        this.elements = (T[]) new Object[size];
        this.size = size;
        this.index = 0;
    }

    // Agregar elemento
    public boolean append(T e) {
        if (e == null) return false;
        
        if (this.index == this.size) {
            this.size = this.size * 2;
            this.appendCapacity(this.size);
            System.out.println("Se ejecutó esto...");
        }

        this.elements[this.index] = e;
        this.index++;

        return true;
    }

    // Expandir capacidad del arreglo
    @SuppressWarnings("unchecked")
    public void appendCapacity(int newSize) {
        T[] temp = (T[]) new Object[newSize];

        for (int i=0; i < this.index; i++) {
            temp[i] = this.elements[i];
        }

        this.elements = temp;
    }

    // Devuelve la cantidad de elementos agregados
    public int size() {
        return this.index;
    }

    // Obtener elemento
    public T get(int i) {
        if (i < 0 || i >= this.index) return null;     // chequeo de rango
        return this.elements[i];
    }

    // ------------------- Iterable -------------------
    @Override
    public Iterator<T> iterator() {
        // Este método es obligatorio porque la clase implementa Iterable<T>.
        // Devuelve un objeto de tipo Iterator<T> que sabe recorrer nuestro MyArray.
        // Cuando usás un for-each, Java "por debajo" llama a este método automáticamente.
        return new MyArrayIterator();
    }

    // Clase interna que implementa Iterator<T>
    private class MyArrayIterator implements Iterator<T> {
        private int pos = 0;  // posición actual en el recorrido

        @Override
        public boolean hasNext() {
            // Devuelve true si todavía quedan elementos por recorrer.
            // El for-each usa este método en cada iteración para decidir
            // si debe seguir iterando.
            return pos < index;
        }

        @Override
        public T next() {
            // Devuelve el siguiente elemento y avanza la posición.
            // Si ya no hay más elementos, lanza la excepción NoSuchElementException.
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[pos++];
        }
    }
}

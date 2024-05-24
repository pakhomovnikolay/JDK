package Task_2;
import java.util.Iterator;

public class Task_2<T> implements Iterable<T> {
    
    private Object[] array;
    private final int DEFAULT_SIZE = 10;
    private final int DELTA = DEFAULT_SIZE / 2;
    private int size;

    Task_2() {
        array = new Object[DEFAULT_SIZE];
    }

    public void add(T t) {
        if ((array.length - size) >= DELTA) {
            redefineArray();
        }
        array[size++] = t;
    }

    public void delete(int inedx) {
        System.arraycopy(array, inedx + 1, array, inedx, size - inedx - 1);
        size--;
    }

    private void redefineArray() {
        size  = DEFAULT_SIZE * 2;
        Object[] array = new Object[size];
        System.arraycopy(this.array, 0, array, 0, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T t : this) {
            sb.append(t + " ");
        }
        return "CollectionEx {" +
                "size= " + size +
                "array= " + "[" + sb + "]" +
                '}';
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return array[index] == null ? null : (T) array[index++];
            }
        };
    }
}

// Описать собственную коллекцию – список на основе массива.
// Коллекция должна иметь возможность хранить любые типы данных, иметь методы добавления и удаления элементов.
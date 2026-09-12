package list;

import java.util.*;

public class MyArray<E> extends AbstractList<E> {
    private Object[] data;
    private int size;
    private static final int defaultSize = 10;

    public MyArray() {
        data = new Object[defaultSize];
        size = 0;
    }

    public MyArray(Collection<? extends  E> c) {
        data = new Object[c.size()];
        size = 0;
        addAll(c);
    }

    @Override
    public int size() {
        return size;
    }

    private void growthReallocation(int required) {
        int factor = 2;
        while (data.length * factor < required) {
            factor *= 2;
        }
        try {
            data = Arrays.copyOf(data, data.length * 2);
        } catch (OutOfMemoryError e) {
            data = Arrays.copyOf(data, required);
        }
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == data.length) {
            growthReallocation(size + 1);
        }
        for (int i = size; i > index; --i) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E element = (E) data[index];
        size--;
        for (int i = index; i < size; ++i) {
            data[i] = data[i + 1];
        }
        data[size] = null;
        return element;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) data[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E result = (E) data[index];
        data[index] = element;
        return result;
    }
}

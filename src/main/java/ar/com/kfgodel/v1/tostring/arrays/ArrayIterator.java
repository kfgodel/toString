package ar.com.kfgodel.v1.tostring.arrays;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * This type represents an array iterator that can traverse the elements of an array
 * Created by kfgodel on 14/09/14.
 */
public class ArrayIterator<T> implements Iterator<T> {

    private Object array;
    private int currentIndex;
    private int arraySize;

    public static<E> ArrayIterator create(E[] array) {
        return create((Object)array);
    }

    public static ArrayIterator create(Object array) {
        ArrayIterator arrayIterator = new ArrayIterator();
        arrayIterator.array = array;
        arrayIterator.arraySize =  Array.getLength(array);
        return arrayIterator;
    }


    @Override
    public boolean hasNext() {
        return currentIndex < arraySize;
    }

    @Override
    public T next() {
        if(!hasNext()){
            throw new IllegalStateException("Array doesn't have next element on index["+currentIndex+"]");
        }
        Object element = Array.get(array, currentIndex++);
        return (T) element;
    }

    /**
     * @return The array size
     */
    public int size() {
        return this.arraySize;
    }
}

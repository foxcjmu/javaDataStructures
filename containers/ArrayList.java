/*
 * A list implemented with dynamic arrays.
 */
package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An ArrayList is a list of objects implemented using contiguous storage. This implementation
 * expands storage as needed when the array grows.
 * 
 * @author C. Fox
 * @version 6/2016
 *
 * @param <T> types of values are stored in the list
 */
public class ArrayList<T> implements List<T>
{
  public static final int INITIAL_SIZE = 8;
  
  private T[] store;    // holds list elements
  private int count;    // how many items in the list

  @SuppressWarnings("unchecked")
  public ArrayList(int initialSize) {
    if (initialSize < 1) initialSize = INITIAL_SIZE;
    count = 0;
    store = (T[]) new Object[initialSize];
  }
  public ArrayList() { this(INITIAL_SIZE); }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return count == 0; }

  @Override
  public void clear() { count = 0; }

  @Override
  public boolean contains(T item) {
    for (int i = 0; i < count; i++)
      if (store[i] == item) return true;
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayListIterator(store, count);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void insert(int i, T item) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted insertion at "+i);
    if (count == store.length) {
      Object[] newStore = new Object[2*store.length];
      for (int k = 0; k < count; k++) newStore[k] = store[k];
      store = (T[]) newStore;
    }
    for (int k = count; i < k; k--) store[k] = store[k-1];
    store[i] = item;
    count++;
  }

  @Override
  public T delete(int i) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted deletion at "+i);
    T result = store[i];
    for (int k = i; k < count-1; k++) store[k] = store[k+1];
    count--;
    return result;
  }

  @Override
  public T get(int i) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted get at "+i);
    return store[i];
  }

  @Override
  public void put(int i, T item) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted put at "+i);
    store[i] = item;
  }

  @Override
  public int index(T item) {
    for (int i = 0; i < count; i++)
      if (item == store[i]) return i;
    return -1;
  }

  @Override
  public List<T> slice(int i, int j) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    if (j < i || count < j) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    ArrayList<T> result = new ArrayList<T>(j-i);
    int m = 0;
    for (int k = i; k < j; k++) result.store[m++] = store[k];
    result.count = j-i;
    return result;
  }

  @Override
  public boolean equals(List<T> otherList) {
    if (otherList.size() != count) return false;
    for (int i = 0; i < count; i++)
      if (store[i] != otherList.get(i)) return false;
    return true;
  }

  /**
   * Reveal the current store size.
   * @return store size in range 0..k
   */
  public int capacity() { return store.length; }
  
  @Override
  public String toString() {
    if (count == 0) return "[]";
    StringBuffer result = new StringBuffer("[");
    for (int i = 0; i < count; i++) result.append(store[i]).append(',');
    result.setCharAt(result.lastIndexOf(","),']');
    return result.toString();
  }
  
  private class ArrayListIterator implements java.util.Iterator<T> {
    private T[] store;    // the associated ArrayList store array
    private int count;    // the associated ArrayList size
    private int cursor;   // where we are in the list

    public ArrayListIterator(T[] theStore, int theCount) {
      store = theStore;
      count = theCount;
      cursor = 0;
    }

    @Override
    public boolean hasNext() { return cursor < count; }

    @Override
    public T next() {
      if (count <= cursor) throw new NoSuchElementException();
      return store[cursor++];
    }
  } // end ArrayListIterator
}

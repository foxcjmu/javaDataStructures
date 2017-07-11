/**
 * A Queue implemented with dynamic arrays.
 */
package containers;

/**
 * A contiguous implementation of queues that is never full.
 * @author C. Fox
 */
public class ArrayQueue<T> implements Queue<T> {
  public static final int INITIAL_SIZE = 8;
  
  private T[] store;        // holds queue elements
  private int frontIndex;   // store location of the front element
  private int count;        // how many elements are in the queue

  @SuppressWarnings("unchecked")
  public ArrayQueue(int initialSize) {
    if (initialSize < 1) initialSize = INITIAL_SIZE;
    store = (T[]) new Object[initialSize];
    frontIndex = 0;
    count = 0;
  }

  public ArrayQueue() { this(INITIAL_SIZE); }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return count == 0; }

  @Override
  public void clear() { count = 0; }

  @Override
  @SuppressWarnings("unchecked")
  public void enter(T item) {
    if (count == store.length) {
      Object[] newStore = new Object[2*store.length];
      for (int i = 0; i < count; i++) {
        newStore[i] = store[(i+frontIndex) % store.length];
      }
      store = (T[]) newStore;
      frontIndex = 0;
    }
    store[(frontIndex + count) % store.length] = item;
    count++;
  }

  @Override
  public T front() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("front of an empty queue");
    return store[frontIndex];
  }

  @Override
  public T leave() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("leave of an empty queue");
    T result = store[frontIndex];
    frontIndex = (frontIndex+1) % store.length;
    count--;
    return result;
  }

  /**
   * Reveal the current size of the store array.
   * @return the store size; will be &gt; 0
   */
  public int capacity() { return store.length; }
}

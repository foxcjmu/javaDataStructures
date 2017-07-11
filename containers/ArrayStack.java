/**
 * A Stack implemented with dynamic arrays.
 */
package containers;

/**
 * A contiguous implementation of stacks that is never full.
 * @author C. Fox
 */
public class ArrayStack<T> implements Stack<T> {
  public static final int INITIAL_SIZE = 8;

  private T[] store;  // contents of the stack
  private int count;  // top is at store[count]

  public ArrayStack() { this(INITIAL_SIZE); }

  @SuppressWarnings("unchecked")
  public ArrayStack(int initialSize) {
    if (initialSize < 1) initialSize = INITIAL_SIZE;
    store = (T[]) new Object[initialSize];
    count = 0;
  }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return count == 0; }

  @Override
  public void clear() { count = 0; }

  @Override
  @SuppressWarnings("unchecked")
  public void push(T item) {
    if (count == store.length) {
      Object[] newStore = new Object[2*store.length];
      for (int i = 0; i < store.length; i++) newStore[i] = store[i];
      store = (T[])newStore;
    }
    store[count++] = item;
  }

  @Override
  public T top() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("top of an empty stack");
    return store[count-1];
  }

  @Override
  public T pop() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("pop of an empty stack");
    return store[--count];
  }

  /**
   * Reveal the current capacity of the store for this stack.
   * @return how many items can be stored right now; always &gt; 0
   */
  public int capacity() { return store.length; }
}

/**
 * A Stack implemented with a singly linked list.
 */
package containers;

/**
 * A contiguous implementation of stacks that is never full.
 * @author C. Fox
 */
public class LinkedQueue<T> implements Queue<T> {
  private Node frontPtr;  // front of the list
  private Node rearPtr;   // end of the list
  private int count;      // how many items in the queue

  public LinkedQueue() {
    frontPtr = rearPtr = null;
    count = 0;
  }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return 0 == count; }

  @Override
  public void clear() {
     frontPtr = rearPtr = null;
     count = 0;
  }

  @Override
  public void enter(T item) {
    if (rearPtr == null)
      frontPtr = rearPtr = new Node(item, null);
    else
      rearPtr = rearPtr.next = new Node(item, null);
    count++;
  }

  @Override
  public T front() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("front of an empty queue");
    return frontPtr.item;
  }

  @Override
  public T leave() throws IllegalStateException {
    if (count == 0) throw new IllegalStateException("leave of an empty queue");
    T result = frontPtr.item;
    frontPtr = frontPtr.next;
    if (frontPtr == null) rearPtr = null;
    count--;
    return result;
  }

  /**
   * Nodes in a singly linked list
   */
  private class Node {
    T item;     // the value stored at this node
    Node next;  // link to the next node
    Node(T value, Node link) {
      item = value;
      next = link;
    }
  }
}

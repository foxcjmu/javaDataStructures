/**
 * A Stack implemented with a singly linked list.
 */
package containers;

/**
 * A contiguous implementation of stacks that is never full.
 * @author C. Fox
 */
public class LinkedStack<T> implements Stack<T> {
  private int count;    // how many items in the stack
  private Node topPtr;  // head of the singly linked list

  public LinkedStack() {
    count = 0;
    topPtr = null;
  }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return topPtr == null; }

  @Override
  public void clear() {
    topPtr = null;
    count = 0;
  }

  @Override
  public void push(T item) {
    topPtr = new Node(item, topPtr);
    count++;
  }

  @Override
  public T top() throws IllegalStateException {
    if (topPtr == null) throw new IllegalStateException("top of an empty stack");
    return topPtr.item;
  }

  @Override
  public T pop() throws IllegalStateException {
    if (topPtr == null) throw new IllegalStateException("pop of an empty stack");
    T result = topPtr.item;
    topPtr = topPtr.next;
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

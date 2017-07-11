/*
 * A list implemented with a doubly-linked circular list, a cursor, and a dummy first node.
 */
package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A DoublyLinkedList is a list of objects implemented using a doubly linked list. This implementation
 * uses a doubly-linked circular list with a cursor to speed up access.
 * 
 * @author C. Fox
 * @version 6/2016
 *
 * @param <T> types of values are stored in the list
 */
public class DoublyLinkedList<T> implements List<T>
{
  private final Node head;  // the first (dummy) node in the list
  private int count;        // how many elements are in the list
  private Node cursorPtr;   // the node holding the cursor value
  private int cursorIndex;  // which element the cursor points to (-1 to start)

  /**
   * The first node in every list is a dummy node, which might be thought of as the node at location -1.
   * Using this node simplifies several of the algorithms at the expense of some wasted space. Hence the
   * empty list is actually a one-element list consisting of the dummy node. Note also that all nodes
   * are linked into a circle, which means that in the empty list, the dummy node's succ and pred fields
   * refer to itself.
   * 
   * Class invariant: cursorPtr == head iff cursorIndex == -1
   */

  public DoublyLinkedList() {
    count = 0;
    head = new Node(null,null,null);
    cursorPtr = head.succ = head.pred = head;
    cursorIndex = -1;
  }

  @Override
  public boolean contains(T item) {
    for (Node ptr = head.succ; ptr != head; ptr = ptr.succ)
      if (ptr.item == item) return true;
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new DoublyLinkedListIterator(head);
  }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return count == 0; }

  @Override
  public void clear() {
    count = 0;
    cursorPtr = head.succ = head.pred = head;
    cursorIndex = -1;
  }

  @Override
  public void insert(int i, T item) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted insertion at "+i);
    setCursor(i);
    Node newNode = new Node(item,cursorPtr,cursorPtr.pred);
    cursorPtr.pred = cursorPtr.pred.succ = newNode;
    cursorPtr = newNode;
    cursorIndex = i;
    count++;
  }

  @Override
  public T delete(int i) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted deletion at "+i);
    setCursor(i);
    T result = cursorPtr.item;
    cursorPtr.succ.pred = cursorPtr.pred;
    cursorPtr.pred.succ = cursorPtr.succ;
    cursorPtr = cursorPtr.succ;
    count--;
    return result;
  }

  @Override
  public T get(int i) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted get at "+i);
    setCursor(i);
    return cursorPtr.item;
  }

  @Override
  public void put(int i, T item) throws IndexOutOfBoundsException {
  if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted put at "+i);
  setCursor(i);
  cursorPtr.item = item;
  }

  @Override
  public int index(T item) {
    Node ptr = head.succ;
    for (int index = 0; index < count; index++) {
      if (ptr.item == item) return index;
      ptr = ptr.succ;
    }
    return -1;
  }

  @Override
  public List<T> slice(int i, int j) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    if (j < i || count < j) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    List<T> result = new DoublyLinkedList<T>();
    for (int k = i; k < j; k++) result.insert(k-i, get(k));
    return result;
  }

  @Override
  public boolean equals(List<T> otherList) {
    if (otherList.size() != count) return false;
    for (int i = 0; i < count; i++)
      if (get(i) != otherList.get(i)) return false;
    return true;
  }

  @Override
  public String toString() {
    if (count == 0) return "[]";
    StringBuffer result = new StringBuffer("[");
    for (Node ptr = head.succ; ptr != head; ptr = ptr.succ)
      result.append(ptr.item).append(',');
    result.setCharAt(result.lastIndexOf(","),']');
    return result.toString();
  }

  /**
   * A doubly-linked list node holds a value and a link to its successor and predecessor node.
   * This list is also circular, so the node at count-1 is linked to the node at 0.
   */
  private class Node {
    T item;     // value stored here
    Node succ;  // the successor node
    Node pred;  // the predecessor node
    
    Node(T value, Node next, Node prev) { item = value; succ = next; pred = prev; }
  }
  
  /**
   * Move the cursor so it points to a node with some index in the fastest way possible.
   * This may mean going from the front, from the rear, or from the current cursor location.
   * 
   * @param index the target cursor location
   */
  private void setCursor(int index) {
    if (index <= count/2) {
      if (index+1 < Math.abs(index-cursorIndex)) {
        cursorIndex = -1;
        cursorPtr = head;
      }
    }
    else if ((count-index) < Math.abs(index-cursorIndex)) {
      cursorIndex = count;
      cursorPtr = head;
    }
    while (cursorIndex < index) {
      cursorPtr = cursorPtr.succ;
      cursorIndex++;
    }
    while (index < cursorIndex) {
      cursorPtr = cursorPtr.pred;
      cursorIndex--;
    }
    //System.out.print(dumpList());
    //System.out.println(cursorIndex+" "+cursorPtr.item);
    //System.out.println("-------");
  }
  
  /**
   * Represent the list (with links) as a string for debugging.
   * 
   * @return the string representation
   */
  private String dumpList() {
    StringBuffer result = new StringBuffer();
    Node ptr = head;
    for (int i = 0; i < count; i++) {
      if (ptr == null) result.append("error\n");
      result.append(new Integer(i).toString()).append(": ");
      result.append("pred ").append(ptr.pred.item);
      result.append("; item ").append(ptr.item);
      result.append("; succ ").append(ptr.succ.item);
      result.append("\n");
      ptr = ptr.succ;
    }
    return result.toString();
  }

  /**
   * This concrete iterator provides access from the first to the last elements in the list.
   */
  private class DoublyLinkedListIterator implements Iterator<T> {
    private final Node head;    // first (dummy) node in the list
    private Node cursor;        // current node in the list

    public DoublyLinkedListIterator(DoublyLinkedList<T>.Node first) {
      head = first;
      cursor = head.succ;
    }

    @Override
    public boolean hasNext() { return cursor != head; }

    @Override
    public T next() {
      if (cursor == head) throw new NoSuchElementException();
      Node current = cursor;
      cursor = cursor.succ;
      return current.item;
    }

  } // end DoublyLinkedListIterator
  
}

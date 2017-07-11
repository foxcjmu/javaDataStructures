/*
 * A list implemented with a singly linked list and a cursor.
 */
package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A LinkedList is a list of objects implemented using some sort of linked structure. This implementation
 * uses a singly linked non-circular list with a cursor to speed up access.
 * 
 * @author C. Fox
 * @version 6/2016
 *
 * @param <T> types of values are stored in the list
 */
public class LinkedList<T> implements List<T>
{
  private final Node head;  // pointer to a dummy node at the start of the list
  private int count;        // how many elements are in the list
  private Node cursorPtr;   // the node holding the cursor value
  private int cursorIndex;  // which element the cursor points to (-1 for none)

  /**
   * In this implementation, first points to a dummy node to make the algorithms a bit
   * simpler. Hence the empty list consists of a single dummy node with (pseudo) index
   * -1, and elements in a non-empty list come after the dummy node.
   * 
   * Class invariant: cursorPtr == first iff cursorIndex == -1
   */

  public LinkedList() {
    head = cursorPtr = new Node(null, null);
    count = 0;
    cursorIndex = -1;
  }

  @Override
  public boolean contains(T item) {
    for (Node ptr = head.next; ptr != null; ptr = ptr.next)
      if (ptr.item == item) return true;
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new LinkedListIterator(head.next);
  }

  @Override
  public int size() { return count; }

  @Override
  public boolean isEmpty() { return count == 0; }

  @Override
  public void clear() {
    count = 0;
    head.next = null;
    cursorPtr = head;
    cursorIndex = -1;
  }

  @Override
  public void insert(int i, T item) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted insertion at "+i);
    setCursor(i-1);
    cursorPtr.next = new Node(item, cursorPtr.next);
    count++;
  }

  @Override
  public T delete(int i) throws IndexOutOfBoundsException {
    if (i < 0 || count <= i) throw new IndexOutOfBoundsException("Attempted deletion at "+i);
    setCursor(i-1);
    T result = cursorPtr.next.item;
    cursorPtr.next = cursorPtr.next.next;
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
    int index = 0;
    for (Node ptr = head.next; ptr != null; ptr = ptr.next) {
      if (ptr.item == item) return index;
      index++;
    }
    return -1;
  }

  @Override
  public List<T> slice(int i, int j) throws IndexOutOfBoundsException {
    if (i < 0 || count < i) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    if (j < i || count < j) throw new IndexOutOfBoundsException("Attempted slice from "+i+" to "+j);
    List<T> result = new LinkedList<T>();
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
    for (Node ptr = head.next; ptr != null; ptr = ptr.next) result.append(ptr.item).append(',');
    result.setCharAt(result.lastIndexOf(","),']');
    return result.toString();
  }

  /**
   * A singly-linked list node holds a value and a link to the next node.
   */
  private class Node {
    T item;     // value stored here
    Node next;  // the successor node
    
    Node(T value, Node link) { item = value; next = link; }
  }
  
  /**
   * Move the cursor to the selected element as quickly as possible.
   * 
   * @param index where to move the cursor
   */
  private void setCursor(int index) {
    if (index < cursorIndex) {
      cursorIndex = -1;
      cursorPtr = head;
    }
    while (cursorIndex < index) {
      cursorPtr = cursorPtr.next;
      cursorIndex++;
    }
  }
  
  /**
   * A concrete iterator that provides access from the first to the last elements of the list.
   */
  private class LinkedListIterator implements Iterator<T> {
    private Node cursor;        // current node in the list

    public LinkedListIterator(LinkedList<T>.Node first) { cursor = first; }

    @Override
    public boolean hasNext() { return cursor != null; }

    @Override
    public T next() {
      if (cursor == null) throw new NoSuchElementException();
      Node current = cursor;
      cursor = cursor.next;
      return current.item;
    }
  } // end of LinkedListIterator
  
}

package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Hashtables map keys to values by hashing keys to array locations.
 * This implementation uses chaining with a singly linked list to resolve collisions.
 * Note that the Object.hashCode() method is used to hash keys. This should be overridden if
 * it is not appropriate for the key's class.
 * 
 * @author C. Fox
 *
 * @param <K> type of the key
 * @param <T> type of the value
 */
class Hashtable<K extends Comparable<K>,T> implements Iterable<T> {
  private static int MAX_LOAD_FACTOR = 5;     // if we reach this, expand the table
  public static final int DEFAULT_SIZE = 13;  // for too small or unspecified table sizes
  private Object[] table;                     // array of entries or null if no entry
  private int count;                          // how many key/value pairs in the table

  /**
   * Allow clients to specify the table size when creating a hash table.
   * If the size is less than 3 the default size is used. If the size is
   * not prime, the next largest prime is used.
   * @param size desired hash table size
   */
  public Hashtable(int size) {
    if (size < 5) size = DEFAULT_SIZE;
    int tableSize = nextPrime(size);
    table = new Object[tableSize];
    count = 0;
  }
  
  /*
   *  Make a hash table with the default size.
   */
  public Hashtable() { this(DEFAULT_SIZE); }

  /**
   * Reveal the hash table size.
   * @return
   */
  public int tableSize() { return table.length; }
  
  /**
   * Reveal how many key/value pairs are in the table.
   * @return count of key/value pairs in range 0..
   */
  public int size() { return count; }
  
  /**
   * Remove all values from the hash table.
   */
  public void clear() {
    for (int i = 0; i < table.length; i++) table[i] = null;
    count = 0;
  }

  /**
   * Put a new key/value pair in the table, or replace the value if the key is already present.
   * @param key the part of the pair hashed
   * @param value the part that goes with the key
   */
  @SuppressWarnings("unchecked")
  public void insert(K key, T value) {
    if (MAX_LOAD_FACTOR <= count/table.length) expandTable();
    int index = Math.abs(key.hashCode()) % table.length;
    
    // if the spot is empty, fill it
    if (table[index] == null) {
      table[index] = new Entry(key, value);
      count++;
      return;
    }
    
    // look down the list for the key/value pair
    Entry node = (Entry)table[index];
    int comparison = key.compareTo(node.key);
    while (comparison != 0 && node.next != null) {
      node = node.next;
      comparison = key.compareTo(node.key);
    }
    
    // if key is found, replace its value; otherwise add a node to the list
    if (comparison == 0) node.value = value;
    else {
      node.next = new Entry(key, value);
      count++;
    }

  }

  /**
   * Fetch the value associated with a key, or null if the key is absent.
   * @param key used to find the value
   * @return the value that goes with the key, or null if the key is absent
   */
  @SuppressWarnings("unchecked")
  public T get(K key) {
    int index = Math.abs(key.hashCode()) % table.length;
    if (table[index] == null) return null;
    
    // look down the list for the key/value pair
    Entry node = (Entry)table[index];
    int comparison = key.compareTo(node.key);
    while (comparison != 0 && node.next != null) {
      node = node.next;
      comparison = key.compareTo(node.key);
    }
    return (comparison == 0) ? node.value : null;
  }

  /**
   * Remove a key/value pair or do nothing if the key is absent.
   * @param key used to find the key/value pair
   */
  @SuppressWarnings("unchecked")
  public void delete(K key) {
    int index = Math.abs(key.hashCode()) % table.length;
    
    // if the spot is empty, return
    if (table[index] == null) return;
    
    // look down the list for the key
    Entry node = (Entry)table[index];
    Entry pred = null;
    int comparison = key.compareTo(node.key);
    while (comparison != 0 && node.next != null) {
      pred = node;
      node = node.next;
      comparison = key.compareTo(node.key);
    }
    if (comparison != 0) return;
    
    // remove a node from the list
    if (pred == null) table[index] = node.next;
    else pred.next = node.next;
    count--;
  }
  
  @SuppressWarnings("unchecked")
  public void visit(Visitor<T> visitor) {
    for (int i = 0; i < table.length; i++) {
      Entry node = (Entry)table[i];
      while (node != null) {
        visitor.visit(node.value);
        node = node.next;
      }
    }
  }
  
  /**
   * Return an iterator over the values in the table.
   * @return a <T> type iterator
   */
  public Iterator<T> iterator() { return new HashtableIterator(); }
  
  /**
   * Return an iterator over the keys in the table.
   * @return a <K> type iterator
   */
  public Iterator<K> keyIterator() { return new HashtableKeyIterator(); }
  
  /**
   * Make a shallow copy of this hash table.
   * @return a new hash table guaranteed to have the same key/value pairs, but not
   *         necessarily in the same value or key iteration order, and the keys
   *         and values are references to the originals (NOT copies)
   */
  public Hashtable<K,T> copy() {
    Hashtable<K,T> result = new Hashtable<>(table.length);
    Iterator<K> iter = keyIterator();
    while (iter.hasNext()) {
      K key = iter.next();
      result.insert(key, get(key));
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public String toString() {
    StringBuffer result = new StringBuffer("{");
    for (int i = 0; i < table.length; i++) {
      Entry node = (Entry)table[i];
      result.append(i);
      while (node != null) {
        result.append('[').append(node.key).
                           append(':').
                           append(node.value).
                           append(']');
        node = node.next;
      }
      result.append('\n');
    }
    result.append('}');
    return result.toString();
  }

  /************************************/
  /*** Private methods and classes ***/
  
  /*
   * Make a key/value pair with a link field for forming a list.
   */
  private class Entry {
    K key;      // hashed value
    T value;    // goes with the key
    Entry next; // successor entry in the linked list of colliding values
    
    public Entry(K k, T v) {
      key = k;
      value = v;
      next = null;
    }
  } // Entry
  
  /*
   * Iterate over all the keys in the table.
   */
  private class HashtableKeyIterator implements Iterator<K> {
    private int index;
    private Entry node;

    @SuppressWarnings("unchecked")
    public HashtableKeyIterator() {
      for (index = 0; index < table.length; index++)
        if (table[index] != null) break;
      node = index < table.length ? (Entry)table[index] : null;
    }

    @Override
    public boolean hasNext() {
      return node != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public K next() {
      if (node == null) throw new NoSuchElementException();
      K result = node.key;
      if (node.next != null) node = node.next;
      else {
        index++;
        while (index < table.length) {
          if (table[index] != null) break;
          index++;
        }
        node = index < table.length ? (Entry)table[index] : null;
      }
      return result;
    }
    
  }

  /*
   * Iterate over all the values in the table.
   */
  @SuppressWarnings("unchecked")
  private class HashtableIterator implements Iterator<T> {
    private int index;
    private Entry node;

    public HashtableIterator() {
      for (index = 0; index < table.length; index++)
        if (table[index] != null) break;
      node = index < table.length ? (Entry)table[index] : null;
    }

    @Override
    public boolean hasNext() {
      return node != null;
    }

    @Override
    public T next() {
      if (node == null) throw new NoSuchElementException();
      T result = node.value;
      if (node.next != null) node = node.next;
      else {
        index++;
        while (index < table.length) {
          if (table[index] != null) break;
          index++;
        }
        node = index < table.length ? (Entry)table[index] : null;
      }
      return result;
    }
    
  }

  /**
   * Make the table bigger if the load factor reaches MAX_LOAD_FACTOR.
   */
  @SuppressWarnings("unchecked")
  private void expandTable() {
    int newSize = nextPrime(table.length*MAX_LOAD_FACTOR);
    Object[] oldTable = table;
    table = new Object[newSize];
    count = 0;
    for (int i = 0; i < oldTable.length; i++) {
      Entry node = (Entry)oldTable[i];
      while (node != null) {
        insert(node.key, node.value);
        node = node.next;
      }
    }
  }

  /**
   * Determine whether a value is prime.
   * @param n value test for primality
   * @return true iff n is prime
   */
  private boolean isPrime(int n) {
    if (n < 2) return false;
    if (n == 2) return true;
    if (n % 2 == 0) return false;
    double limit = Math.sqrt(n);
    for (int i = 3; i <= limit; i += 2)
      if (n % i == 0) return false;
    return true;
  }
    
  /**
   * Find the next prime equal or greater than n.
   * @param n lower limit if prime search
   * @return n if it is prime, or the next prime greater than n
   */
  private int nextPrime(int n) {
    if (n < 3) return 2;
    while (true) {
      if (isPrime(n)) return n;
      n++;
    }
  }
}

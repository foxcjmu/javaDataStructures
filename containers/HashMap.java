package containers;

import java.util.Iterator;

/**
 * A HashMap uses a hash table to implement maps.
 * @author C. Fox
 *
 * @param <K> type of the key
 * @param <T> type of the value
 */
public class HashMap<K extends Comparable<K>, T> implements Map<K, T> {
  private Hashtable<K,T> table;

  /**
   * Create a new HashMap instance backed by a hash table.
   * The defaults size is Hashtable.DEFAULT_SIZE. The size can be
   * specified but if it is less than 5 is will be the default, and it
   * it is not prime is will be the next largest prime.
   */
  public HashMap() { table = new Hashtable<>(); }
  public HashMap(int size) { table = new Hashtable<>(size); }

  /**
   * Return the hash table size (for debugging).
   * @return the current hash table size: a prime number > 3
   */
  public int tableSize() { return table.tableSize(); }
  
  @Override
  public boolean contains(T v) {
    for (T value : table) if (v == value) return true;
    return false;
  }

  @Override
  public Iterator<T> iterator() { return table.iterator(); }

  @Override
  public int size() { return table.size(); }

  @Override
  public boolean isEmpty() { return 0 == table.size(); }

  @Override
  public void clear() { table.clear(); }

  @Override
  public T get(K key) { return table.get(key); }

  @Override
  public void insert(K key, T value) { table.insert(key, value); }

  @Override
  public void delete(K key) { table.delete(key); }

  @Override
  public boolean hasKey(K key) { return table.get(key) != null; }

  @Override
  public Iterator<K> keyIterator() { return table.keyIterator(); }

  @Override
  public boolean isEqual(Map<K, T> m) {
    if (size() != m.size()) return false;
    Iterator<K> iter = keyIterator();
    while(iter.hasNext()) {
      K key = iter.next();
      if (get(key) != m.get(key)) return false;
    }
    return true;
  }
}

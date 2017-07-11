package containers;

import java.util.Iterator;

/**
 * A HashSet uses a hash table to store set values.
 * Set elements are stored as both keys and values in this implementation.
 * @author C. Fox
 *
 * @param <T> type of values stored in the set
 */
public class HashSet<T extends Comparable<T>> implements Set<T> {
  private Hashtable<T,T> table;

  /*
   * Create a new hash set backed by a hash table.
   * The defaults size is Hashtable.DEFAULT_SIZE. The size can be
   * specified but if it is less than 5 is will be the default, and it
   * it is not prime is will be the next largest prime.
   */
  public HashSet() { table = new Hashtable<>(); }
  public HashSet(int size) { table = new Hashtable<>(size); }

  /**
   * Return the current hash table size (for debugging).
   * @return a prime number > 3
   */
  public int tableSize() { return table.tableSize(); }
  
  @Override
  public boolean contains(T v) { return table.get(v) != null; }

  @Override
  public Iterator<T> iterator() { return table.iterator(); }

  @Override
  public int size() { return table.size(); }

  @Override
  public boolean isEmpty() { return 0 == table.size(); }

  @Override
  public void clear() { table.clear(); }

  @Override
  public boolean isSubset(Set<T> set) {
    for (T element : this) if (!set.contains(element)) return false;
    return true;
  }

  @Override
  public void insert(T v) { table.insert(v,v); }

  @Override
  public void delete(T v) { table.delete(v); }

  @Override
  public Set<T> intersection(Set<T> set) {
    Set<T> result = new HashSet<>();
    for (T element : this) if (set.contains(element)) result.insert(element);
    return result;
  }

  @Override
  public Set<T> union(Set<T> set) {
    Set<T> result = new HashSet<>();
    for (T element : this) result.insert(element);
    for (T element : set) result.insert(element);
    return result;
  }

  @Override
  public Set<T> complement(Set<T> set) {
    Set<T> result = new HashSet<>();
    for (T element : this) if (!set.contains(element)) result.insert(element);
    return result;
  }

  @Override
  public boolean isEqual(Set<T> set) {
    if (size() != set.size()) return false;
    for (T element : this) if (!set.contains(element)) return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer("[");
    Iterator<T> iter = iterator();
    while (iter.hasNext()) {
      result.append(iter.next());
      if (!iter.hasNext()) break;
      result.append(',');
    }
    result.append(']');
    return result.toString();
  }
}

package containers;

import java.util.Iterator;

/**
 * A TreeMap uses a search tree to implement maps.
 * This TreeMap uses an AVL tree.
 * @author C. Fox
 *
 * @param <K> type of the key
 * @param <T> type of the value
 */
public class TreeMap<K extends Comparable<K>, T> implements Map<K, T> {
  private AVLTree<Pair> tree;

  /**
   * Create a new TreeMap instance backed by an AVL tree.
   */
  public TreeMap() { tree = new AVLTree<>(); }

  @Override
  public boolean contains(T v) {
    for (T value : this) if (v == value) return true;
    return false;
  }

  @Override
  public Iterator<T> iterator() { return new ValueIterator(tree); }

  @Override
  public int size() { return tree.size(); }

  @Override
  public boolean isEmpty() { return tree.isEmpty(); }

  @Override
  public void clear() { tree.clear(); }

  @Override
  public T get(K key) {
    Pair dummy = new Pair(key,null);
    Pair result = tree.get(dummy);
    return result == null ? null : result.value;
  }

  @Override
  public void insert(K key, T value) { tree.insert(new Pair(key,value)); }

  @Override
  public void delete(K key) {
    Pair dummy = new Pair(key,null);
    tree.delete(dummy);
  }

  @Override
  public boolean hasKey(K key) {
    Pair dummy = new Pair(key,null);
    return tree.get(dummy) != null;
  }

  @Override
  public Iterator<K> keyIterator() {
    return new KeyIterator(tree);
  }

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

  /**
   * Associate a key with a value.
   */
  private class Pair implements Comparable<Pair> {
    K key;    // the key in the pair
    T value;  // the value in the pair
    
    public Pair(K k, T v) { key = k; value = v; }

    public int compareTo(Pair other) {
      return key.compareTo(other.key);
    }
  }
  
  /**
   * A ValueIterator goes through all the values (not keys) in a map. Note that this
   * requires traversing the AVL tree and extracting the value component of the key-value
   * pairs stored in the tree. Fortunately, we can mostly delegate to the the tree
   * iterator.
   * The values are traversed in key order.
   */
  private class ValueIterator implements Iterator<T> {
    private Iterator<Pair> pairIterator;

    public ValueIterator(AVLTree<Pair> tree) {
      pairIterator = tree.inorderIterator();
    }

    @Override
    public boolean hasNext() { return pairIterator.hasNext(); }

    @Override
    public T next() { return pairIterator.next().value; }

  } // ValueIterator
  
  /**
   * A KeyIterator goes through all the keys (not values) in a map. Note that this
   * requires traversing the AVL tree and extracting the key component of the key-value
   * pairs stored in the tree. Fortunately, we can mostly delegate to the the tree
   * iterator.
   * The keys are traversed in order.
   */
  private class KeyIterator implements Iterator<K> {
    private Iterator<Pair> pairIterator;

    public KeyIterator(AVLTree<Pair> tree) {
      pairIterator = tree.inorderIterator();
    }

    @Override
    public boolean hasNext() { return pairIterator.hasNext(); }

    @Override
    public K next() { return pairIterator.next().key; }

  } // KeyIterator
}

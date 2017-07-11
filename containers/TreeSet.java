package containers;

import java.util.Iterator;

/**
 * A TreeSet uses a search tree to store set values.
 * This implementation uses AVL Trees.
 * @author C. Fox
 *
 * @param <T> type of values stored in the set
 */
public class TreeSet<T extends Comparable<T>> implements Set<T> {
  private AVLTree<T> tree;

  public TreeSet() {
    tree = new AVLTree<>();
  }

  @Override
  public boolean contains(T v) { return tree.contains(v); }

  @Override
  public Iterator<T> iterator() { return tree.inorderIterator(); }

  @Override
  public int size() { return tree.size(); }

  @Override
  public boolean isEmpty() { return tree.isEmpty(); }

  @Override
  public void clear() { tree.clear(); }

  @Override
  public boolean isSubset(Set<T> set) {
    for (T element : this) if (!set.contains(element)) return false;
    return true;
  }

  @Override
  public void insert(T v) { tree.insert(v); }

  @Override
  public void delete(T v) { tree.delete(v); }

  @Override
  public Set<T> intersection(Set<T> set) {
    Set<T> result = new TreeSet<>();
    for (T element : this) if (set.contains(element)) result.insert(element);
    return result;
  }

  @Override
  public Set<T> union(Set<T> set) {
    Set<T> result = new TreeSet<>();
    for (T element : this) result.insert(element);
    for (T element : set) result.insert(element);
    return result;
  }

  @Override
  public Set<T> complement(Set<T> set) {
    Set<T> result = new TreeSet<>();
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

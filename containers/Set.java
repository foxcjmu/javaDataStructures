package containers;

/**
 * A wSetollection is an unordered Collection
 * @author C. Fox
 */
public interface Set<T> extends Collection<T> {
  /**
   * Determine whether this set is a subset of the argument set.
   * @param set candidate super set
   * @return true if this is a subset of set
   */
  boolean isSubset(Set<T> set);
  
  /**
   * Put a new element into a set, or replace the one that is there if it is already present.
   * @param element value added or replaced
   */
  void insert(T item);
  
  /**
   * Remove an element from a set or do nothing if it is not present.
   * @param element value removed
   */
  void delete(T item);
  
  /**
   * Create a new set that is the intersection of this set and the argument set.
   * @param set intersected with this set
   * @return a new set that is the intersection of this and set
   */
  Set<T> intersection(Set<T> set);
  
  /**
   * Create a new set that is the union of this set and the argument set.
   * @param set united with this set
   * @return a new set that is the union of this and set
   */
  Set<T> union(Set<T> set);
  
  /**
   * Create a new set that is the relative complement of this set and the argument set.
   * @param set whose values are removed from this set
   * @return a new set that is this set complemented with set
   */
  Set<T> complement(Set<T> set);
  
  /**
   * Determine whether this set is the same as the argument set.
   * @param set compared with this set
   * @return true iff this set is identical to set
   */
  boolean isEqual(Set<T> set);
}

/**
 * Root for Collections, including lists, sets, and maps.
 */
package containers;

/**
 * A Collection is a Container that can be traversed.
 * @author C. Fox
 */
public interface Collection<T> extends Container, Iterable<T> {
  /**
   * Say whether a Collection includes some item. The "same" is determined using ==.
   * 
   * @return true iff item is in the Collection
   */
  boolean contains(T item);
}

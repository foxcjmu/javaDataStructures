/**
 * Root for the Container hierarchy.
 */
package containers;

/**
 * A Container is an entity that holds objects.
 * @author C. Fox
 */
public interface Container
{
  /**
   * Say how many elements are in a container.
   * @return the number of elements in the range 0..*
   */
  int size();         // how many elements are in the container?
  
  /**
   * True iff this container is empty.
   * @return True iff size() == 0
   */
  boolean isEmpty();  // is the container empty?
  
  /**
   * Make this container empty. Afterwards, isEmpty() and size() == 0.
   */
  void clear();
}

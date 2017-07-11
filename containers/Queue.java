/**
 * Interface for all Queue implementations.
 */
package containers;

/**
 * Every Queue implements this interface.
 * @author C. Fox
 */
public interface Queue<T> extends Container
{
  /**
   * Add a value to the rear of the queue.
   * 
   * @param item the value added
   */
  void enter(T item);
  
  /**
   * Return the front value from the queue without removing it. The queue cannot be empty.
   * 
   * @result the front value, which is not removed
   * @throws IllegalStateException if the queue is empty.
   */
  T front() throws IllegalStateException;
  
  /**
   * Remove and return the front value from the queue. The queue cannot be empty.
   * 
   * @result the front value, which is removed
   * @throws IllegalStateException if the queue is empty.
   */
  T leave() throws IllegalStateException;
}

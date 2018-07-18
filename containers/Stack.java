/**
 * Interface for all Stack implementations.
 */
package containers;

/**
 * Every Stack implements this interface.
 * @author C. Fox
 */
public interface Stack<T> extends Container {
  /**
   * Add a value to the top of the stack.
   * 
   * @param item the value added
   */
  void push(T item);
  
  /**
   * Return the top value from the stack without removing it. The stack cannot be empty.
   * 
   * @result the top value, which is not removed
   * @throws IllegalStateException if the stack is empty.
   */
  T top() throws IllegalStateException;
  
  /**
   * Remove and return the top value on the stack. The stack cannot be empty.
   * 
   * @result the top value, which is removed
   * @throws IllegalStateException if the stack is empty.
   */
  T pop() throws IllegalStateException;
}

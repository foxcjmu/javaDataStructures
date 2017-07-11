/**
 * Interface for all List implementations.
 */
package containers;

/**
 * A List is an ordered Collection of items.
 * @author C. Fox
 */

public interface List<T> extends Collection<T>
{
  /**
   * Put an item into a list at index i. Note that i can range from 0 to
   * the size of the list (inserting at the front to the end of the list).
   * 
   * @param i the index where insertion takes place
   * @param item the value inserted
   * @throws IndexOutOfBoundsException if i < 0 or size() < i
   */
  void insert(int i, T item) throws IndexOutOfBoundsException;
  
  /**
   * Remove an item from a list at index i. Note that i can range from 0
   * to one less than the size of the list.
   * 
   * @param i the index where deletion takes place
   * @returns the deleted item
   * @throws IndexOutOfBoundsException if i < 0 or size() <= i
   */
  T delete(int i) throws IndexOutOfBoundsException;
  
  /**
   * Fetch the value at index i. Note that i can range from 0 to one less
   * than the size of the list.
   * 
   * @param i the index of the fetched value
   * @returns the value at index i
   * @throws IndexOutOfBoundsException if i < 0 or size() <= i
   */
  T get(int i) throws IndexOutOfBoundsException;
  
  /**
   * Replace the value at index i with another. Note that i can range from 0 to
   * one less than the size of the list.
   * 
   * @param i the index of the replaced value
   * @param item the value that replaces the overwritten value
   * @throws IndexOutOfBoundsException if i < 0 or size() <= i
   */
  void put(int i, T item) throws IndexOutOfBoundsException;
  
  /**
   * Return the index of an item in the list, or -1 if it is not in the list.
   * 
   * @param item the value searched for
   * @return the index of the item in range -1 .. size()-1
   */
  int index(T item);
  
  /**
   * Create and return a list that is a copy of a contiguous portion of this list from
   * index i to one less than index j. Note that 0 <= i <= j <= size(). If i == j then
   * the result list is empty.
   * 
   * @param i the start of the slice
   * @param j one index past the end of the slice
   * @returns a list forming a slice of this list from i to one less than j
   * @throws IndexOutOfBoundsException if i < 0 or size() < i or j < i or size() < j
   */
  List<T> slice(int i, int j) throws IndexOutOfBoundsException;
  
  /**
   * Determine whether two lists are identical, that is that they have the same size and the
   * same values in the each position (where == is used to determine whether items values are 
   * the same).
   * 
   * @param otherList the list compared to this list
   * @return true iff the lists are the same size and their elements in each slot are ==
   */
  boolean equals(List<T> otherList);

}

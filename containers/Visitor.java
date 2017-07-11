/*
 * Visitors is used to implement the Command pattern for BinaryTree traversals.
 * @author C. Fox
 */
package containers;

/**
 * Encapsulate a visit() function applied to every element of a BinaryTree.
 *
 * @param <T> type of values stored at tree nodes
 */
interface Visitor<T> {

  /**
   * Do some processing on a tree node value.
   * @param value from the tree node
   */
  public void visit(T value);
}

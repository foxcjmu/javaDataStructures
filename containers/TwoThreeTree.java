package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked tree whose every node contains either one value and two children (a 2-node) or
 * two values and three children (a 3-node), that is perfectly balanced. Furthermore, when
 * traversed in order, its values are enumerated in ascending order.
 * @author C. Fox
 */

class TwoThreeTree<T extends Comparable<T>> {
  private TreeNode root;
  private int count;

  /**
   * Create an empty 2-3 tree.
   */
  TwoThreeTree() {
    root = null;
    count = 0;
  }

  /**
   * Determine whether a tree is empty.
   * @return true iff this tree is the empty tree.
   */
  public boolean isEmpty() { return count == 0; }
  
  /**
   * Determine how many nodes are in this tree.
   * @return the number of nodes in the tree: 0..
   */
  public int size() { return count; }
  
  /**
   * Make this tree empty.
   */
  public void clear() {
    count = 0;
    root = null;
  }

  /**
   * Determine the height of this tree.
   * @return length of the longest path from the root to a leaf in range 0..
   */
  public int height() { return root == null ? 0 : root.height(); }

  /**
   * Add one value to the tree, or replace it if it is already present.
   * @param value the value added or replaced
   */
  public void insert(T value) {
    if (root == null) {
      root = new TreeNode(value);
      count = 1;
    }
    else root = root.insert(value);
  }

  /**
   * Determine whether a value is in the tree.
   * @param v the value searched for
   * @return true iff v is in this tree
   */
  public boolean contains(T v) {
    return root == null ? false : root.get(v) != null;
  }

  /**
   * Fetch a value from the tree, or null if it is missing.
   * @param v the value retrieved
   * @return the value matching v in the tree, or null if no such value is present
   */
  public T get(T v) {
    return root == null ? null : root.get(v);
  }

  /**
   * Delete a value from this 2-3 tree, or do nothing if it is not present.
   * @param value the deleted value
   */
  public void delete(T value) {
    if (root == null) return;
    root.delete(value, null, 0);
    if (root.type == 1) root = root.left;
  }
  
  /**
   * Apply a visitor method to every value in the tree in order.
   * Note that in a 2-3 tree, the leftmost subtree is visited first, followed
   * by the leftmost value, followed by the middle subtree, followed (if the
   * node is a 3-node) by the rightmost value, followed by the rightmost
   * subtree.
   * @param visitor Instance of a class with a visitor(T) method
   */
  public void visit(Visitor<T> visitor) {
    if (root != null) root.visitInorder(visitor);
  }

  /**
   * Apply a visitor method to every value in the tree in preorder.
   * Note that in a 2-3 tree, the values at the node are visited first,
   * followed by the leftmost subtree, followed by the middle subtree,
   * followed (if the node is a 3-node) by the rightmost subtree.
   * @param visitor Instance of a class with a visitor(T) method
   */
  public void visitPreorder(Visitor<T> visitor) {
    if (root != null) root.visitPreorder(visitor);
  }

  /**
   * Apply a visitor method to every value in the tree in post order.
   * Note that in a 2-3 tree, the values in the leftmost subtree are visited
   * first, followed by the middle subtree, followed (if the node is a 3-node)
   * by the rightmost subtree, followed by the value(s) at this node.
   * @param visitor Instance of a class with a visitor(T) method
   */
  public void visitPostorder(Visitor<T> visitor) {
    if (root != null) root.visitPostorder(visitor);
  }

  /**
   * Create and return in inorder iterator over this tree.
   * @return the new iterator
   */
  public Iterator<T> iterator() { return new InorderIterator(root); }
  
  @Override
  public String toString() {
    return root == null ? "Empty tree\n" : root.toString("");
  }

  /**********************************************************************/
  /* Private Methods and Classes                                        */

  /**
   * A TreeNode is either a 1-node (used only in deletions), a 2-node,
   * or a 3-node. In all cases, the values and subtrees are as far left
   * as possible; in other words, the single subtree in a 1-node (there
   * is no value) is left; the value at a 2-node is value1 and the
   * two children are left and mid.
   */
  private class TreeNode {
    static final String INDENT = "  ";  // for string representations of trees

    int type;                     // is this a 1-node, 2-node, or a 3-node?
    T value1, value2;             // one or two values at this node
    TreeNode left, mid, right;    // two or three children at this node

    /**
     * Create a new leaf (always a 2-node).
     * @param v the balue at this node
     */
    public TreeNode(T v) {
      type = 2;
      value1 = v;
      value2 = null;
      left = mid = right = null;
    }

    /**
     * Create a new 2-node with given value and subtrees.
     * @param v the new value at the node
     * @param left the leftmost subtree
     * @param mid the middle subtree (rightmost in a 2-node)
     */
    public TreeNode(T v, TreeNode left, TreeNode mid) {
      this.type = 2;
      this.value1 = v;
      this.value2 = null;
      this.left = left;
      this.mid = mid;
      this.right = null;
    }

    /**
     * Determine the height of the tree rooted at this node.
     * Since the tree is balanced, we need only look down one path to the leaves.
     * @return the height of the subtree rooted at this node in range 0..
     */
    public int height() { return isLeaf() ? 0 : 1 + left.height(); }

    /**
     * Return a value matching a given value in the tree rooted at this node, or
     * null if a matching value is not present.
     * @param v the value searched for
     * @return a value matching v, or null if no such value is present
     */
    public T get(T v) {
      int comparison = v.compareTo(value1);
      if (comparison == 0) return value1;
      if (comparison < 0)
        return left == null ? null : left.get(v);
      if (type == 2)
        return mid == null ? null : mid.get(v);
      comparison = v.compareTo(value2);
      if (comparison == 0) return value2;
      if (comparison < 0)
        return mid == null ? null : mid.get(v);
      return right == null ? null : right.get(v);
    }

    /**
     * Put a new value into the subtree rooted at this node.
     * This is a somewhat complex operation:
     * - insertion always occurs at a leaf, so we recurse down the tree
     * - if the leaf is a 2-node, the value is added and it becomes a 3-node
     * - if the leaf is a 3-node, it is split to create a sub-tree with three
     *   2-nodes, and the parent node is returned
     * - after insertion in a subtree, if a split node is returned, then if
     *   this node is a 2-node, it is made into a 3-node and the subtrees are
     *   incorporated into it
     * - if this is a 3-node and a split node from a subtree insertion, then this
     *   node is split to make a new-subtree that is in turn returned
     * @param value the value added
     * @return if this node must be split, return the new created from the split.
     */
    TreeNode insert(T value) {
      int comparison = value.compareTo(value1);

      // just replace the value if it is already present
      if (comparison == 0) {
        value1 = value;
        return this;
      }

      // insert in the leftmost subtree
      TreeNode result;
      if (comparison < 0) {
        if (isLeaf()) {
          count++;
          if (type == 3)
            return new TreeNode(value1, new TreeNode(value), new TreeNode(value2));
          value2 = value1;
          value1 = value;
          type = 3;
          return this;
        }
        result = left.insert(value);
        if (result == left) return this;
        if (type == 3)
          return new TreeNode(value1, result, new TreeNode(value2, mid, right));
        shiftRight();
        graftAtLeft(result);
        return this;
      }

      // insert in the rightmost tree of a 2-node
      if (type == 2) {
        if (isLeaf()) {
          count++;
          value2 = value;
          type = 3;
          return this;
        }
        result = mid.insert(value);
        if (result != mid) graftAtRight(result);
        return this;
      }

      // insert into the middle or right of a 3-node
      comparison = value.compareTo(value2);
      if (comparison == 0) {
        value2 = value;
        return this;
      }
      if (isLeaf()) {
        count++;
        return (comparison < 0) ?
          new TreeNode(value, new TreeNode(value1), new TreeNode(value2)) :
          new TreeNode(value2, new TreeNode(value1), new TreeNode(value));
      }
      if (comparison < 0) {
        result = mid.insert(value);
        if (result != mid)
          return new TreeNode(result.value1, new TreeNode(value1, left, result.left),
                                             new TreeNode(value2, result.mid, right));
      }
      else { // (0 < comparions)
        result = right.insert(value);
        if (result != right)
          return new TreeNode(value2, new TreeNode(value1, left, mid), result);
      }
      return this;
    } // insert

    /**
     * Recursively delete a value from this tree.
     * Deletion always occurs starting from a leaf, so if the deleted value is in an internal
     * node, its successor is copied in to the node holding the delete value (the target node),
     * and the successor is deleted (just like in a BST or AVL tree).
     * If the deleted value is in a leaf, then we delete it. This may change the node from a
     * 3-node to a 2-node, which is ok, or from a 2-node to a 1-node, which propagates a problem
     * to the parent (hence the usefulness of recursion).
     * If a node has a child that is a 1-node then
     * - if the child has a sibling that is a 3-node, a value and a subtree are borrowed from
     *   the sibling fix the 1-node
     * - if this node (the parent) is a 3-node, then a value and a subtree are borrowed from
     *   the parent to fix the 1-node
     * - otherwise, this is a 2-node with a 2-node child and a 1-node child: this node becomes a
     *   1-node with a 3-node child holding the 2-node value, the parent value, and the 1-node and
     *   2-node children. The new 1-node is returned, propagating the problem up the tree
     * @param v the value deleted
     * @param target if non-null, the ancestor node holding the deleted value
     * @param whichValue whether the deleted value in the target is value1 or value2
     */
    void delete(T v, TreeNode target, int whichValue) {
      int comparison;

      // handle a leaf node
      if (isLeaf()) {
        if (target != null) {
          if (whichValue == 0) target.value1 = value1;
          else                 target.value2 = value1;
          value1 = value2;
        }
        else {
          comparison = v.compareTo(value1);
          if (comparison < 0) return;
          if (comparison == 0) value1 = value2;
          else { // comparison > 0
            if (type == 2) return;
            comparison = v.compareTo(value2);
            if (comparison != 0) return;
          }
        }
        count--;
        type--;
        return;
      }

      // handle an internal node
      comparison = v.compareTo(value1);

      // do the deletion
      if (target != null) left.delete(v, target, whichValue);
      else if (comparison < 0) left.delete(v, null, 0);
      else if (comparison == 0) mid.delete(v, this, 0);
      else if (type == 2) mid.delete(v, null, 0);
      else {
        comparison = v.compareTo(value2);
        if (comparison < 0) mid.delete(v, null, 0);
        else if (comparison == 0) right.delete(v, this, 1);
        else right.delete(v, null, 0);
      }

      // if any child is a 1-node, fix it
      if (left.type == 1) {
        if (mid.type == 3 || (type == 3 && right.type == 3)) {
          leftBorrowsFromMid();
          if (mid.type == 1) midBorrowsFromRight();
        }
        else if (type == 3) foldLeftIntoMid();
        else                pushLeftIntoMid();
      }

      else if (mid.type == 1) {
        if (left.type == 3) midBorrowsFromLeft();
        else if (type == 3) {
          if (right.type == 3) midBorrowsFromRight();
          else                  foldMidIntoRight();
        }
        else pushMidIntoLeft();
      }

      else if (type == 3 && right.type == 1) {
        if (left.type == 3 || mid.type == 3) {
          rightBorrowsFromMid();
          if (mid.type == 1) midBorrowsFromLeft();
        }
        else foldRightIntoMid();
      }
    } // delete

    /**
     * Determine whether this node is a leaf node.
     * @return true iff this node is a leaf node
     */
    private boolean isLeaf() { return left == null; }

    /**
     * Attach the subtree whose root is the argument as the
     * leftmost portion of this node.
     * Pre: this is a two node being made into a three node and
     * the leftmost portion of the node has been shifted right
     * Post: The subtree is attached and this is a 3-node
     * @param tree subtree attached
     */
    private void graftAtLeft(TreeNode tree) {
      value1 = tree.value1;
      left = tree.left;
      mid = tree.mid;
      type = 3;
    }

    /**
     * Attach the subtree whose root is the argument as the
     * rightmost portion of this node.
     * Pre: this is a two node being made into a three node and
     * the rightmost portion of the node has no significance
     * Post: The subtree is attached and this is a 3-node
     * @param tree subtree attached
     */
    private void graftAtRight(TreeNode tree) {
      value2 = tree.value1;
      mid = tree.left;
      right = tree.mid;
      type = 3;
    }
    
    /**
     * Move the rightmost portions of this node to the left.
     */
    private void shiftLeft() {
      value1 = value2;
      left = mid;
      mid = right;
    }

    /**
     * Move the leftmost portions of this node to the right.
     */
    private void shiftRight() {
      value2 = value1;
      right = mid;
      mid = left;
    }

    /**
     * During a deletion, a leftmost subtree is a 1-node that is made
     * into a 2-node by borrowing from the subtree to its right.
     * Pre: left.type == 1; mid.type == 2..3
     */
    private void leftBorrowsFromMid() {
      left.value1 = value1;
      value1 = mid.value1;
      left.mid = mid.left;
      mid.shiftLeft();
      left.type = 2;
      mid.type--;
    }

    /**
     * During a deletion, a middle subtree is a 1-node that is made
     * into a 2-node by borrowing from the subtree to its left.
     * Pre: mid.type == 1; left.type == 3
     */
    private void midBorrowsFromLeft() {
      mid.value1 = value1;
      value1 = left.value2;
      mid.mid = mid.left;
      mid.left = left.right;
      mid.type = 2;
      left.type = 2;
    }

    /**
     * During a deletion, a middle subtree is a 1-node that is made
     * into a 2-node by borrowing from the subtree to its right.
     * Pre: mid.type == 1; right.type == 3
     */
    private void midBorrowsFromRight() {
      mid.value1 = value2;
      value2 = right.value1;
      mid.mid = right.left;
      right.shiftLeft();
      mid.type = 2;
      right.type = 2;
    }

    /**
     * During a deletion, a rightmost subtree in a 3-node is a 1-node that
     * is made into a 2-node by borrowing from the subtree to its left.
     * pre: right.type == 1; mid.type == 2..3
     */
    private void rightBorrowsFromMid() {
      right.value1 = value2;
      right.mid = right.left;
      switch (mid.type) {
        case 2:
          value2 = mid.value1;
          right.left = mid.mid;
          break;
        case 3:
          value2 = mid.value2;
          right.left = mid.right;
          break;
        default: throw new AssertionError("Unreachable code");
      }
      right.type = 2;
      mid.type--;
    }

    /**
     * During a deletion, a leftmost subtree that is a 1-node is incorporated
     * into the 2-node to its right, borrowing a value from this node and thus
     * making it into a 2-node.
     * Pre: type == 3; left.type == 1; mid.type == 2
     */
    private void foldLeftIntoMid() {
      mid.shiftRight();
      mid.value1 = value1;
      mid.left = left.left;
      mid.type = 3;
      shiftLeft();
      type = 2;
    }

    /**
     * During a deletion, a middle subtree that is a 1-node is incorporated
     * into the 2-node to its right, borrowing a value from this node and thus
     * making it into a 2-node.
     * pre: type == 3; mid.type == 1; right.type == 2
     */
    private void foldMidIntoRight() {
      right.shiftRight();
      right.value1 = value2;
      right.left = mid.left;
      mid = right;
      right.type = 3;
      type = 2;
    }

    /**
     * During a deletion, a rightmost subtree in a 3-node that is a 1-node is
     * incorporated into the 2-node to its left, borrowing a value from this
     * node and thus making it into a 2-node.
     * Pre: type == 3; mid.type == 2; right.type == 1
     */
    private void foldRightIntoMid() {
      mid.value2 = value2;
      mid.right = right.left;
      mid.type = 3;
      type = 2;
    }

    /**
     * During a deletion, this 2-node has a left child that is a 1-node and
     * a right child that is a 2-node, forcing the left child to be combined
     * with the right, borrowing a value from this, to form a 3-node that is
     * the only child of this, which then becomes a 1-node.
     * Pre: type == 2; left.type == 1; mid.type == 2
     */
    private void pushLeftIntoMid() {
      mid.shiftRight();
      mid.value1 = value1;
      mid.left = left.left;
      mid.type = 3;
      left = mid;
      type = 1;
    }

    /**
     * During a deletion, this 2-node has a right child that is a 1-node and
     * a left child that is a 2-node, forcing the right child to be combined
     * with the left, borrowing a value from this, to form a 3-node that is
     * the only child of this, which then becomes a 1-node.
     * Pre: type == 2; left.type == 2; mid.type == 1
     */
    private void pushMidIntoLeft() {
      left.value2 = value1;
      left.right = mid.left;
      left.type = 3;
      type = 1;
    }

    /**
     * Make a string representation the tree rooted at this node.
     * @param indent how far to move over the output
     * @return the tree rooted as this node as a string
     */
    public String toString(String indent) {
      StringBuffer result = new StringBuffer(indent);
      result.append(value1);
      if (type == 3) result.append(" / ").append(value2);
      result.append('\n');
      if (left == null) return result.toString();
      result.append(left.toString(indent+INDENT));
      result.append(mid.toString(indent+INDENT));
      if (type == 3) result.append(right.toString(indent+INDENT));
      return result.toString();
    }

    /**
     * Recursively apply a visitor function in order.
     * @param visitor object with a visitor(T) method
     */
    public void visitInorder(Visitor<T> visitor) {
      if (isLeaf()) {
        visitor.visit(value1);
        if (type == 3) visitor.visit(value2);
      }
      else {
        left.visitInorder(visitor);
        visitor.visit(value1);
        mid.visitInorder(visitor);
        if (type == 3) {
          visitor.visit(value2);
          right.visitInorder(visitor);
        }
      }
    }

    /**
     * Recursively apply a visitor function in preorder.
     * @param visitor object with a visitor(T) method
     */
    public void visitPreorder(Visitor<T> visitor) {
      if (isLeaf()) {
        visitor.visit(value1);
        if (type == 3) visitor.visit(value2);
      }
      else {
        visitor.visit(value1);
        if (type == 3) visitor.visit(value2);
        left.visitPreorder(visitor);
        mid.visitPreorder(visitor);
        if (type == 3) right.visitPreorder(visitor);
      }
    }

    /**
     * Recursively apply a visitor function in preorder.
     * @param visitor object with a visitor(T) method
     */
    public void visitPostorder(Visitor<T> visitor) {
      if (isLeaf()) {
        visitor.visit(value1);
        if (type == 3) visitor.visit(value2);
      }
      else {
        left.visitPostorder(visitor);
        mid.visitPostorder(visitor);
        if (type == 3) right.visitPostorder(visitor);
        visitor.visit(value1);
        if (type == 3) visitor.visit(value2);
      }
    }

  } // TreeNode
  
  class InorderIterator implements Iterator<T> {
    private LinkedStack<TreeNode> stack;
    
    public InorderIterator(TreeNode r) {
      stack = new LinkedStack<>();
      TreeNode node = r;
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }

    @Override
    public boolean hasNext() { return !stack.isEmpty(); }

    /**
     * This algorithm is like the binary tree algorithm except that it converts 3-nodes to
     * 2-nodes and pushes them back on the stack after they are visited the first time.
     */
    @Override
    public T next() {
      if (stack.isEmpty()) throw new NoSuchElementException();
      TreeNode node = stack.pop();
      T result = node.value1;
      if (node.type == 3) stack.push(new TreeNode(node.value2, node.mid, node.right));
      if (node.isLeaf()) return result;
      node = node.mid;
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
      return result;
    }

  } // InorderIterator
}

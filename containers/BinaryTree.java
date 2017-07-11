/*
 * BinaryTree is the super class for other sorts of binary trees. It implements a linked
 * binary tree.
 */
package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked binary tree holding values of type T with internal and external iterators.
 * @author C. Fox
 */
class BinaryTree<T> {
  protected int count;      // how many nodes in the tree
  protected TreeNode root;  // the top node, if any

  /**
   * Create a new empty binary tree.
   */
  public BinaryTree() {
    count = 0;
    root = null;
  }

  /**
   * Create a binary tree with a root value and given subtrees.
   * @param v root value
   * @param l left subtree
   * @param r right subtree
   */
  public BinaryTree(T v, BinaryTree<T> l, BinaryTree<T> r) {
    root = new TreeNode(v, l.root, r.root);
    count = l.count + r.count + 1;
  }

  /**
   * Determine whether a tree is empty.
   * @return true iff this tree is the empty tree.
   */
  public boolean isEmpty() { return count == 0; }
  
  /**
   * Determine how many nodes are in this tree.
   * @return the nunber of nodes in the tree: 0..
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
   * Compute the height of this tree. The height is the maximum level, counting from 0.
   * @return the tree height: 0..
   */
  public int height() { return root == null ? 0 : root.height(); }
  
  /**
   * Fetch the value at the root of the tree. 
   * @return the value stored at the root
   * @throws IllegalStateException if the tree is empty
   */
  public T rootValue() {
    if (root == null) throw new IllegalStateException("Empty tree has no root value");
    return root.value;
  }

  /**
   * Create a new binary search tree consisting of the left subtree of this tree.
   * @return the left subtree as a new Binary Tree instance
   * @throws IllegalState exception if this tree is empty
   */
  public BinaryTree<T> leftSubtree() {
    if (root == null) throw new IllegalStateException("Empty tree has no left subtree");
    BinaryTree<T> result = new BinaryTree<T>();
    result.root = root.left;
    result.count = root.left == null ? 0 : root.left.countNodes();
    return result;
  }

  /**
   * Create a new binary search tree consisting of the right subtree of this tree.
   * @return the right subtree as a new Binary Tree instance
   * @throws IllegalState exception if this tree is empty
   */
  public BinaryTree<T> rightSubtree() {
    if (root == null) throw new IllegalStateException("Empty tree has no right subtree");
    BinaryTree<T> result = new BinaryTree<T>();
    result.root = root.right;
    result.count = root.right == null ? 0 : root.right.countNodes();
    return result;
  }

  /**
   * Determine whether a value is in this tree.
   * @param value the datum searched for
   * @return true iff value is stored in a node of this tree
   */
  public boolean contains(T value) { return root == null ? false : root.contains(value); }

  /**
   * Apply a visit function to every value in the tree in preorder.
   * This method uses the Command pattern to encapsulate the processing applied to
   * values in the tree.
   * @param visitor an object with a visit() method applied to values this tree
   */
  public void visitPreorder(Visitor<T> visitor) { if (root != null) root.visitPreorder(visitor); }

  /**
   * Apply a visit function to every value in the tree in order.
   * This method uses the Command pattern to encapsulate the processing applied to
   * values in the tree.
   * @param visitor an object with a visit() method applied to values this tree
   */
  public void visitInorder(Visitor<T> visitor) { if (root != null) root.visitInorder(visitor); }

  /**
   * Apply a visit function to every value in the tree in post order.
   * This method uses the Command pattern to encapsulate the processing applied to
   * values in the tree.
   * @param visitor an object with a visit() method applied to values this tree
   */
  public void visitPostorder(Visitor<T> visitor) { if (root != null) root.visitPostorder(visitor); }
  
  /**
   * Create an iterator that yields every value in the tree in preorder.
   * @return Iterator instance that can be used to access every element in preorder
   */
  public Iterator<T> preorderIterator() { return new PreorderIterator(root); }
  
  /**
   * Create an iterator that yields every value in the tree in order.
   * @return Iterator instance that can be used to access every element in order
   */
  public Iterator<T> inorderIterator() { return new InorderIterator(root); }
  
  /**
   * Create an iterator that yields every value in the tree in post order.
   * @return Iterator instance that can be used to access every element in post order
   */
  public Iterator<T> postorderIterator() { return new PostorderIterator(root); }
  
  @Override
  public String toString() {
    return root == null ? "Empty tree\n" : root.toString("");
  }

  /**********************************************************************************/
  /* Private Methods and Classes                                                    */

  /**
   * Store a value and references to left and right child nodes.
   */
  protected class TreeNode {
    static final String INDENT = "  ";  // for string representations of trees
    
    T value;          // what is stored at the node
    TreeNode left;    // root of the left subtree
    TreeNode right;   // root of the right subtree
    
    public TreeNode(T v, TreeNode l, TreeNode r) {
      value = v; left = l; right = r;
    }
    
    /**
     * Use recursion to determine the height of the subtree at this node.
     * @return height in range 0..
     */
    public int height() {
      if (left == null && right == null) return 0;
      int leftHeight = left == null ? 0 : left.height();
      int rightHeight = right == null ? 0 : right.height();
      return 1 + (leftHeight < rightHeight ? rightHeight : leftHeight);
    }

    /**
     * Use recursion to search the subtree at this node.
     * @param v what to search for
     * @return true iff v is in the tree whose root is this node
     */
    public boolean contains(T v) {
      if (value == v) return true;
      return (left != null && left.contains(v)) || (right != null && right.contains(v));
    }

    /**
     * Use recursion to count the nodes in the subtree at this node.
     * @return the number of nodes in the subtree rooted at this node in range 0..
     */
    public int countNodes() {
      int leftCount = left == null ? 0 : left.countNodes();
      int rightCount = right == null ? 0 : right.countNodes();
      return 1 + leftCount + rightCount;
    }

    /**
     * Use recursion to visit a subtree in order.
     * @param visitor instance of a class with a visit() method applied to every value in the tree in order
     */
    public void visitInorder(Visitor<T> visitor) {
      if (left != null) left.visitInorder(visitor);
      visitor.visit(value);
      if (right != null) right.visitInorder(visitor);
    }

    /**
     * Use recursion to visit a subtree in preorder.
     * @param visitor instance of a class with a visit() method applied to every value in the tree in preorder
     */
    public void visitPreorder(Visitor<T> visitor) {
      visitor.visit(value);
      if (left != null) left.visitPreorder(visitor);
      if (right != null) right.visitPreorder(visitor);
    }

    /**
     * Use recursion to visit a subtree in post order.
     * @param visitor instance of a class with a visit() method applied to every value in the tree in post order
     */
    public void visitPostorder(Visitor<T> visitor) {
      if (left != null) left.visitPostorder(visitor);
      if (right != null) right.visitPostorder(visitor);
      visitor.visit(root.value);
    }
    
    /**
     * Make a string representation the tree rooted at this node.
     * @param indent how far to move over the output
     * @return the tree rooted as this node as a string
     */
    public String toString(String indent) {
      StringBuffer result = new StringBuffer(indent);
      result.append(value).append('\n');
      if (left == null && right == null) return result.toString();
      if (left == null) result.append(indent+INDENT+"-\n");
      else              result.append(left.toString(indent+INDENT));
      if (right == null) result.append(indent+INDENT+"-\n");
      else result.append(right.toString(indent+INDENT));
      return result.toString();
    }
  } // TreeNode
  
  /**
   * Keep track of data for traversing a tree in order using a stack. The top
   * of the stack is always the node holding the current value. When the next
   * node is needed, the stack is popped and nodes are pushed on going from
   * the left child of the popped node all the way down to the left. Iteration
   * is complete when the stack is empty.
   */
  private class InorderIterator implements Iterator<T> {
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

    @Override
    public T next() {
      if (stack.isEmpty()) throw new NoSuchElementException();
      TreeNode node = stack.pop();
      T result = node.value;
      node = node.right;
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
      return result;
    }

  } // InorderIterator

  /**
   * Keep track of data for traversing a tree in preorder using a stack. The top
   * of the stack is always the node holding the current value. When the next
   * node is needed, the stack is popped and its children are pushed on, rightmost
   * first. Iteration is complete when the stack is empty.
   */
  private class PreorderIterator implements Iterator<T> {
    private LinkedStack<TreeNode> stack;
    
    public PreorderIterator(TreeNode r) {
      stack = new LinkedStack<>();
      if (r != null) stack.push(r);
    }

    @Override
    public boolean hasNext() { return !stack.isEmpty(); }

    @Override
    public T next() {
      if (stack.isEmpty()) throw new NoSuchElementException();
      TreeNode node = stack.pop();
      T result = node.value;
      if (node.right != null) stack.push(node.right);
      if (node.left != null) stack.push(node.left);
      return result;
    }

  } // PreorderIterator

  /**
   * Keep track of data for traversing a tree in post order using a stack. The top
   * of the stack is always the node holding the current value. When the next
   * node is needed, the stack is popped and if the popped node is the left child
   * of the new top node, then the right node of the top node and all its left
   * children are pushed on the stack. Iteration is complete when the stack is empty.
   */
  private class PostorderIterator implements Iterator<T> {
    private LinkedStack<TreeNode> stack;
    
    public PostorderIterator(TreeNode r) {
      stack = new LinkedStack<>();
      TreeNode node = r;
      while (node != null) {
        stack.push(node);
        while (node.left != null) {
          node = node.left;
          stack.push(node);
        }
        node = node.right;
      }
    }

    @Override
    public boolean hasNext() { return !stack.isEmpty(); }

    @Override
    public T next() {
      if (stack.isEmpty()) throw new NoSuchElementException();
      TreeNode node = stack.pop();
      T result = node.value;
      if (!stack.isEmpty() && (node == stack.top().left)) {
        node = stack.top().right;
        while (node != null) {
          stack.push(node);
          while (node.left != null) {
            node = node.left;
            stack.push(node);
          }
          node = node.right;
        }
      }
      return result;
    }
    
  } // PostorderIterator

}

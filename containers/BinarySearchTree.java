package containers;

/**
 * A linked binary tree whose every node is such that all values in its left sub-tree
 * are less than the value at the node, which in turn is less than all values in its
 * right sub-tree.
 * @author C. Fox
 */
class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

  /* Create an empty binary search tree */
  public BinarySearchTree() { super(); }

  @Override
  /**
   * Determine whether a value is in the tree. This method is overridden
   * to take advantage of the binary search tree order properties.
   * @param value the value searched for
   * @return true iff value is in the tree
   */
  public boolean contains(T value) { return get(value) != null; }

  /**
   * Add a value, preserving the tree as a binary search tree. Replace the value
   * if it is already in the tree. Note that this may not keep the tree balanced.
   * @param value the value inserted into the tree.
   */
  public void insert(T value) {
    if (root == null) {
      root = new TreeNode(value, null, null);
      count++;
      return;
    }
    TreeNode node = root;
    while (true) {
      int comparison = value.compareTo(node.value);
      if (comparison == 0) {
        node.value = value;
        return;
      } else if (comparison < 0) {
        if (node.left == null) {
          node.left = new TreeNode(value,null, null);
          count++;
          return;
        } else node = node.left;
      } else {
        if (node.right == null) {
          node.right = new TreeNode(value,null, null);
          count++;
          return;
        } else node = node.right;
      }
    }
  }

  /**
   * Remove a value, preserving the tree as a binary search tree. Do nothing if
   * the value is not present. Note that this may not keep the tree balanced.
   * @param value the value deleted
   */
  public void delete(T value) {
    // find node with value and its parent
    TreeNode node = root;   // node deleted
    TreeNode parent = null; // its parent
    while (node != null) {
      int comparison = value.compareTo(node.value);
      if (comparison == 0) break;
      else {
        parent = node;
        node = (comparison < 0) ? node.left : node.right;
      }
    }
    if (node == null) return;
    
    // the node has 0 or one child, then delete it
    if (node.left == null) {
      if (parent == null) root = node.right;
      else if (parent.left  == node) parent.left  = node.right;
      else                           parent.right = node.right;
      count--;
      return;
    }
    if (node.right == null) {
      if (parent == null) root = node.left;
      else if (parent.left  == node) parent.left  = node.left;
      else                           parent.right = node.left;
      count--;
      return;
    }
    
    // the node has two children, so do the thing with its successor
    parent = node;
    TreeNode succ = node.right;
    while (succ.left != null) {
      parent = succ;
      succ = succ.left;
    }
    node.value = succ.value;
    if (parent == node) parent.right = succ.right;
    else                parent.left  = succ.right;
    count--;
  }

  /**
   * Fetch a value from the tree.
   * @param value the value searched for
   * @return the value from the tree that matches the parameter value, or null
   */
  public T get(T value) {
    TreeNode node = root;
    while (node != null) {
      int comparison = value.compareTo(node.value);
      if (comparison == 0) return node.value;
      node = (comparison < 0) ? node.left : node.right;
    }
    return null;
  }
}

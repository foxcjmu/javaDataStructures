package containers;

/**
 * AVL trees are balanced binary search trees. The balance factor at a node is the left sub-tree height
 * minus the right sub-tree height (with empty tree heights defined as -1). The balance factor at each
 * node of an AVL tree is between -1 and 1. When insertion or deletion causes a balance factor to go to
 * 2 or -2 the tree is rebalanced. All AVL tree operations are thus O(lg n).
 * @param <T> type of values stored at tree nodes
 */
class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  /**
   *  Make only empty AVLTrees so all insertions are done through this class.
   */
  public AVLTree() { super(); }

  /**
   * Take advantage of the height field in each node to make this O(1) rather than O(n).
   */
  @Override
  @SuppressWarnings("unchecked")
  public int height() { return root == null ? 0 : ((AVLNode)root).height; }

  /**
   * Reveal the AVL tree balance value of the root. Balance values are the difference
   * in height between the left and right subtrees and in AVL trees range between -1 and 1.
   * @return the root balance factor: -1..1
   */
  @SuppressWarnings("unchecked")
  public int balance () { return root == null ? 0 : ((AVLNode)root).balance(); }

  /**
   * Add a value to an AVL tree.
   * Insertion works as in regular binary search trees, except that after the insertion
   * at the bottom of the tree, the tree may be altered to restore balance.
   * @param v the value inserted
   */
  @Override
  @SuppressWarnings("unchecked")
  public void insert(T v) {
    if (root == null) {
      root = new AVLNode(v, null, null);
      count = 1;
      return;
    }
    ((AVLNode)root).insert(v);
  }

  /**
   * Remove a value from an AVL tree.
   * Deletion works as in a binary search tree, except that after deletion at the
   * bottom of the tree, the tree may be altered to restore balance.
   * @param v the value deleted
   */
  @Override
  @SuppressWarnings("unchecked")
  public void delete(T v) {
    if (root == null) return;
    AVLNode r = (AVLNode)root;
    root = r.delete(v, null);
    r.rebalance();
  }

  /********************************************************************************/
  /* Private Methods and Classes                                                  */
  
  /**
   * Make a node with tree node fields plus a height field.
   */
  private class AVLNode extends TreeNode {
    int height; // of the tree with this node as root
    
    public AVLNode(T v, TreeNode l, TreeNode r) {
      super(v, l, r);
      setHeight();
    }

    /**
     * Insert a value into the AVL tree rooted at this node.
     * A recursive insertion algorithm is used because it is easy to rebalance and adjust
     * node heights after the insertion as the algorithm backs up along the path to the root.
     * @param value the value inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(T v) {
      int comparison = v.compareTo(value);
      
      // handle a value already in the tree
      if (comparison == 0) {
        value = v;
        return;
      }
      
      // insert a value in the left subtree
      if (comparison < 0) {
        if (left == null) {
          left = new AVLNode(v, null, null);
          count++;
          setHeight();
          return;
        }
        ((AVLNode)left).insert(v);
        rebalance();
        return;
      }

      // insert a value in the right subtree
      if (right == null) {
        right = new AVLNode(v, null, null);
        count++;
        setHeight();
        return;
      }
      ((AVLNode)right).insert(v);
      rebalance();
    }

    /**
     * Recursively remove an element from an AVL tree. Each node may have to be
     * rebalanced, and node heights adjusted, all the way on the path from the
     * deleted node to the root. This makes it convenient to use recursion for this
     * task because each node may be rebalanced and its height adjusted as necessary
     * before returning from the recursive call.
     * 
     * Note that there is extra complication because if a deleted value is in a node
     * with two children, the node with the successor value must be found. Since it
     * has no left child, it can be deleted, and the successor value copied into the
     * node with the deleted value (the target). This is as in deletion from a plain
     * binary search tree, with the addition of the rebalancing code.
     * 
     * @param v the value deleted (if found)
     * @param target the node with the deleted value, if it has two children
     * @return the node replacing this node (if any)
     */
    @SuppressWarnings("unchecked")
    public AVLNode delete(T v, AVLNode target) {
      if (target != null) {
        if (left == null) {
          target.value = value;
          count--;
          return (AVLNode)right;
        }
        left = ((AVLNode)left).delete(v, target);
      }
      else {
        int comparison = v.compareTo(value);
        if (comparison == 0) {
          if (left == null)  { count--; return (AVLNode)right; }
          if (right == null) { count--; return (AVLNode)left; }
          right = ((AVLNode)right).delete(v, this);
        }
        else if (comparison < 0) {
          if (left == null) return this;
          left = ((AVLNode)left).delete(v, null);
        }
        else {
          if (right == null) return this;
          right = ((AVLNode)right).delete(v, null);
        }
      }
      rebalance();
      return this;
    }

    /**
     * Compute the AVL balance factor, which is the height of the left subtree minus the height
     * of the right subtree, with empty tree heights defined as -1.
     */
    @SuppressWarnings("unchecked")
    public int balance() {
      int leftHeight = (left == null) ? -1 : ((AVLNode)left).height;
      int rightHeight = (right == null) ? -1 : ((AVLNode)right).height;
      return leftHeight - rightHeight;
    }

    /**
     * Check the balance factor and it is 2 or -2, then rebalance the subtree from this node.
     * There are two kinds of rebalancing depending on the shape of the subtrees. If the balance
     * factor is 2, then an R rotation is done when the left child has a balance factor of 1 or 0;
     * otherwise (that is, a balance factor of -1), an LR rotation is done. The rotations done
     * with a balance factor of -2 are symmetric.
     * The height is always reset because even if no rotations occur, this method is only called
     * when the tree has been altered, so height must be reset anyway.
     */
    @SuppressWarnings("unchecked")
    public void rebalance() {
      switch (balance()) {
        case 2:
          AVLNode leftNode = (AVLNode)left;
          if (leftNode.balance() == -1) rotateLR();
          else                          rotateR();
          break;
        case -2:
          AVLNode rightNode = (AVLNode)right;
          if (rightNode.balance() == 1) rotateRL();
          else                          rotateL();
          break;
      }
      setHeight();
    }

    /**
     * Print the tree with the height and balance factor of each node.
     * This is for debugging.
     * @indent string to use to indent at this level of the tree
     */
    public String toString(String indent) {
      StringBuffer result = new StringBuffer(indent);
      result.append(value).append("(").append(height).append(")(").append(balance()).append(")\n");
      if (left == null && right == null) return result.toString();
      if (left == null) result.append(indent+INDENT+"-\n");
      else              result.append(left.toString(indent+INDENT));
      if (right == null) result.append(indent+INDENT+"-\n");
      else result.append(right.toString(indent+INDENT));
      return result.toString();
    }
    
    /**
     * Look at the child nodes to figure out this nodes height.
     */
    @SuppressWarnings("unchecked")
    private void setHeight() {
      int leftHeight = (left == null) ? -1 : ((AVLNode)left).height;
      int rightHeight = (right == null) ? -1 : ((AVLNode)right).height;
      height = ((leftHeight < rightHeight) ? rightHeight : leftHeight) + 1;
    }

    /**
     * Rotate left, meaning the right child becomes the root of the subtree.
     * This node is actually retained and its fields changed to that its parent
     * node is not affected by the change.
     */
    private void rotateL() {
      AVLNode newLeft = new AVLNode(value, left, right.left);
      value = right.value;
      right = right.right;
      left = newLeft;
    }
    
    /**
     * Rotate right, meaning the left child becomes the root of the subtree.
     * This node is actually retained and its fields changed to that its parent
     * node is not affected by the change.
     */
    private void rotateR() {
      AVLNode newRight = new AVLNode(value, left.right, right);
      value = left.value;
      left = left.left;
      right = newRight;
    }
    
    /**
     * Rotate left-right, meaning the right child of the left child of this node
     * becomes the root of the subtree.
     * This node is actually retained and its fields changed to that its parent
     * node is not affected by the change.
     */
    @SuppressWarnings("unchecked")
    private void rotateLR() {
      AVLNode newRight = new AVLNode(value, left.right.right, right);
      value = left.right.value;
      left.right = left.right.left;
      ((AVLNode)left).setHeight();
      right = newRight;
    }
      
    /**
     * Rotate right-left, meaning the left child of the right child of this node
     * becomes the root of the subtree.
     * This node is actually retained and its fields changed to that its parent
     * node is not affected by the change.
     */
    @SuppressWarnings("unchecked")
    private void rotateRL() {
      AVLNode newLeft = new AVLNode(value, left, right.left.left);
      value = right.left.value;
      right.left = right.left.right;
      ((AVLNode)right).setHeight();
      left = newLeft;
    }
      
  } // AVLNode
  
}

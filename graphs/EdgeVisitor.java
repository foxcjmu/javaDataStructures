/*
 * EdgeVisitor is used to implement the Command pattern for graph traversals.
 * @author C. Fox
 */
package graphs;

/**
 * Encapsulate a visit() function applied to edges in a graph.
 */
interface EdgeVisitor {

  /**
   * Do some processing on a graph edge.
   * @param v one vertex in the edge
   * @param w the other vertex in the edge
   * @param g the graph in which this edge reside
   */
  public void visit(Graph g, int v, int w);
}

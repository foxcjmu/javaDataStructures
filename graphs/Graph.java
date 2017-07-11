package graphs;

/**
 * Interface for undirected graphs.
 * This interface is implemented by all classes that represent graphs (typically
 * using either an adjacency list or an adjacency matrix data structure).
 * 
 * @author C. Fox
 */
import java.util.Iterator;

public interface Graph {
  /**
   * Reveal the number of edges in the graph.
   * @return how many edges in range 0..
   */
  int edges();

  /**
   * Reveal the number of vertices in the graph.
   * @return how many vertices in range 0..
   */
  int vertices();
  
  /**
   * Put an edge in the graph.
   * This method does nothing if a precondition is violated.
   * @pre 0 <= v,w and v,w < vertices() and v != w
   * @param v one vertex
   * @param w the other vertex
   */
  void addEdge(int v, int w);
  
  /**
   * Say whether an edge is in the graph.
   * This method returns false for out-of-range vertex numnbers.
   * @param v one vertex
   * @param w the other vertex
   * @return true iff {v,w} is in the graph
   */
  boolean isEdge(int v, int w);
  
  /**
   * Iterate over the edges from node v.
   * @param v the source node for iteration
   * @return a standard java.util.Iterator<Integer>
   */
  Iterator<Integer> iterator(int v);
}

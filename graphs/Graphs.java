package graphs;

import java.util.Iterator;

import containers.ArrayList;
import containers.LinkedQueue;
import containers.LinkedStack;
import containers.List;
import containers.Queue;
import containers.Stack;

/**
 * Graphs contains static public utility methods for searching graphs and determining
 * various of their properties.
 * 
 * @author C. Fox
 */
public class Graphs {

  /**
   * Perform a recursive depth-first search applying an EdgeVisitor to
   * the edge to a visited node and the node from which is was visited.
   * @param g the graph in which the vertices abide
   * @param v the start vertex for the search
   * @param edgeVisitor the class whose visit method is applied
   */
  public static void DFS(Graph g, int v, EdgeVisitor edgeVisitor) {
    boolean[] isVisited = new boolean[g.vertices()];
    edgeVisitor.visit(g, -1, v);
    isVisited[v] = true;
    dfs(g, v, edgeVisitor, isVisited);
  }

  /**
   * Perform a stack-based depth-first search applying an EdgeVisitor to
   * the edge to a visited node and the node from which is was visited.
   * @param g the graph in which the vertices abide
   * @param v the start vertex for the search
   * @param edgeVisitor the class whose visit method is applied
   */
  public static void stackDFS(Graph g, int v0, EdgeVisitor visitor) {
    boolean[] isVisited = new boolean[g.vertices()];
    Stack<Edge> stack = new LinkedStack<Edge>();
    stack.push(new Edge(-1, v0));

    while (!stack.isEmpty()) {
      Edge edge = stack.pop();
      if (isVisited[edge.w]) continue;
      visitor.visit(g, edge.v, edge.w);
      isVisited[edge.w] = true;
      Iterator<Integer> iter = g.iterator(edge.w);
      while (iter.hasNext()) {
        int x = iter.next();
        if (!isVisited[x]) stack.push(new Edge(edge.w,x));
      }
    }
  }
  
  /**
   * Perform a queue-based breadth-first search applying an EdgeVisitor to
   * the edge to a visited node and the node from which is was visited.
   * @param g the graph in which the vertices abide
   * @param v the start vertex for the search
   * @param edgeVisitor the class whose visit method is applied
   */
  public static void BFS(Graph g, int v0, EdgeVisitor visitor) {
    boolean[] isVisited = new boolean[g.vertices()];
    Queue<Edge> queue = new LinkedQueue<Edge>();
    queue.enter(new Edge(-1, v0));

    while (!queue.isEmpty()) {
      Edge edge = queue.leave();
      if (isVisited[edge.w]) continue;
      visitor.visit(g, edge.v, edge.w);
      isVisited[edge.w] = true;
      Iterator<Integer> iter = g.iterator(edge.w);
      while (iter.hasNext()) {
        int x = iter.next();
        if (!isVisited[x]) queue.enter(new Edge(edge.w,x));
      }
    }
  }
  
  /**
   * Determine whether there is a path in g between v and w.
   * @param g the graph examined
   * @param v one end of the potential path
   * @param w the other end of the potential path
   * @return true iff there is path between v and w in g
   */
  public static boolean isPath(Graph g, int v, int w) {
    if (v < 0 || g.vertices() <= v) return false;
    if (w < 0 || g.vertices() <= w) return false;
    TargetMarker targetMarker = new TargetMarker(w);
    DFS(g, v, targetMarker);
    return targetMarker.isReached;
  }

  /**
   * Find the shortest path from v to w in g, provided there is a path between them.
   * @param g the graph where vertices reside
   * @param v the source vertex
   * @param w the destination vertex
   * @return a shortest path from v to w in g, or null if there is no path
   */
  public static List<Integer> shortestPath(Graph g, int v, int w) {
    if (!isPath(g,v,w)) return null;
    int [] toEdge = new int[g.vertices()];
    PathVisitor visitor = new PathVisitor(toEdge);
    BFS(g,w,visitor);
    List<Integer> result = new ArrayList<Integer>();
    int x = v;
    while (x != w) {
       result.insert(result.size(),x);
       x = toEdge[x];
    }
    result.insert(result.size(),x);
    return result;
  }
  
  /**
   * Determine whether a graph is connected.
   * @param g the graph tested
   * @return true iff g is connected
   */
  public static boolean isConnected(Graph g) {
    if (g.vertices() == 0) return true;
    NodeMarker marker = new NodeMarker(g.vertices());
    DFS(g, 0, marker);
    return marker.allMarked();
  }
  
  /**
   * Create a spanning tree for a graph.
   * @param g the graph spanned
   * @return a LinkedGraph containing a spanning tree for g
   */
  public static Graph spanningTree(Graph g) {
    if (!isConnected(g)) return null;
    Graph result = new LinkedGraph(g.vertices());
    EdgeVisitor visitor = new TreeBuilder(result);
    DFS(g, 0, visitor);
    return result;
  }

  /***************************************/
  /***   Private Methods and Classes   ***/
  
  /**
   * Do a recursive depth-first search; this is the method that called by DFS to do the work.
   * @param g the graph examined
   * @param v the node from which to proceed
   * @param edgeVisitor the class with the visit method
   * @param isVisited keeps track of which vertices are already visited
   */
  private static void dfs(Graph g, int v, EdgeVisitor edgeVisitor, boolean[] isVisited) {
    Iterator<Integer> iter = g.iterator(v);
    while (iter.hasNext()) {
      int w = iter.next();
      if (isVisited[w]) continue;
      edgeVisitor.visit(g, v, w);
      isVisited[w] = true;
      dfs(g, w, edgeVisitor, isVisited);
    }
  }

  /**
   * Store edges in stack and queues for DFS and BFS.
   */
  private static class Edge {
    public int v;
    public int w;
    
    public Edge(int v, int w) {
      this.v = v;
      this.w = w;
    }
  } // Edge

  /**
   * An EdgVisitor that keeps track of whether a target vertex has been visited.
   */
  private static class TargetMarker implements EdgeVisitor {
    private int target;        // which vertex to watch for
    public boolean isReached;  // true iff target is visited
    
    public TargetMarker(int target) {
      this.target = target;
      isReached = false;
    }

    @Override
    public void visit(Graph g, int v, int w) {
      if (w == target) isReached = true;
    }
  } // TargetMarker

  /**
   * An EdgeVisitor that constructs an array of edges in the shortest path to a vertex.
   */
  private static class PathVisitor implements EdgeVisitor {
    private int[] toEdge; // toEdge[v] is the next vertex on the shortest
                          // path from v to some target vertex w
    
    public PathVisitor(int[] toEdge) { this.toEdge = toEdge; }

    @Override
    public void visit(Graph g, int v, int w) { toEdge[w] = v; }

  } // PathVisitor

  /**
   * An EdgeVisitor that keeps track of whether all vertices have been visited.
   */
  private static class NodeMarker implements EdgeVisitor {
    private boolean[] mark;    // mark a vertex when it is visited
    
    public NodeMarker(int n) { mark = new boolean[n]; }

    @Override
    public void visit(Graph g, int v, int w) { mark[w] = true; }
    
    public boolean allMarked() {
      for (boolean marked : mark) if (!marked) return false;
      return true;
    }
  } // NodeMarker

  /**
   * An EdgeVisitor that generates a spanning tree.
   */
  private static class TreeBuilder implements EdgeVisitor {
    private Graph tree;  // the new spanning tree

    public TreeBuilder(Graph g) { tree = g; }
    
    @Override
    public void visit(Graph g, int v, int w) {
      tree.addEdge(v, w);
    }
  } // TreeBuilder
  
}

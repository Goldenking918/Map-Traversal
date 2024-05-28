package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Graph<T> {
  private Map<T, List<T>> adjacencyMap;

  public Graph() {
    this.adjacencyMap = new HashMap<>();
  }

  public void addNode(T node) {
    adjacencyMap.putIfAbsent(node, new LinkedList<>());
  }

  public void addEdge(T node1, T node2) {
    addNode(node1);
    addNode(node2);
    adjacencyMap.get(node1).add(node2);
    adjacencyMap.get(node2).add(node1);
  }

  public List<T> shortestPath(T root, T destination) {
    List<T> visited = new ArrayList<>();
    Map<T, T> previous = new HashMap<>();
    Queue<T> queue = new LinkedList<>();
    queue.add(destination);
    previous.put(destination, null);
    visited.add(destination);
    while (!queue.isEmpty()) {
      T node = queue.poll();
      for (T n : adjacencyMap.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          previous.put(n, node);
        } else if (n.equals(root)) {
          List<T> path = new ArrayList<>();
          T current = root;
          while (current != null) {
            path.add(current);
            current = previous.get(current);
          }
          return path;
        }
      }
    }
    return visited;
  }
}

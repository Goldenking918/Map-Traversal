package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
  }

  public List<T> findShortestPath(T root, T destination) {
    Set<T> visited = new LinkedHashSet<>();
    Map<T, T> previous = new LinkedHashMap<>();
    Queue<T> queue = new LinkedList<>();
    queue.add(root);
    previous.put(root, null);

    // Uses BFS until the destination is reached.
    while (!queue.isEmpty()) {
      T node = queue.poll();
      if (!visited.contains(node)) {
        visited.add(node);
      }
      for (T n : adjacencyMap.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          previous.put(n, node);
        }
        // if the destination is reached it goes back to the root and returns the path reversed.
        if (n.equals(destination)) {
          List<T> path = new ArrayList<>();
          for (T current = destination; current != null; current = previous.get(current)) {
            path.add(current);
          }
          Collections.reverse(path);
          return path;
        }
      }
    }
    return new ArrayList<>();
  }
}

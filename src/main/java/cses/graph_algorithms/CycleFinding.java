package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class CycleFinding {

  /*
   * Time Complexity: O(nm)
   * Space Complexity: O(n+m)
   *
   * The traditional Bellman-Ford application.
   * Incorporate [parents] to reconstruct the targeted route
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    FlatEdge[] edges = new FlatEdge[m];
    
    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());
      
      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      int c = Integer.parseInt(in.nextToken());

      edges[i] = new FlatEdge(a, b, c);
    }
    
    long[] lowScores = new long[n+1];
    int[] parents = new int[n+1];

    Arrays.fill(lowScores, 0);

    int lastUpdatedNode = 0;

    // Bellman-Ford -> iterate every edge n times
    // Take note of last new lowScore on the nth iteration, or its absence
    for (int iter = 0; iter < n; iter++) {
      lastUpdatedNode = 0;
      for (FlatEdge edge : edges) {
        long score = lowScores[edge.source] + edge.weight;
        if (score < lowScores[edge.destination]) {
          lowScores[edge.destination] = score;
          parents[edge.destination] = edge.source;
          lastUpdatedNode = edge.destination;
        }
      }
    }

    if (lastUpdatedNode == 0) {
      System.out.println("NO");
      return;
    }

    // traverse back up the path such that we are guaranteed to be in the loop
    int start = lastUpdatedNode;
    for (int i = 1; i <= n; i++) {
      start = parents[start];
    }

    ArrayList<Integer> loop = new ArrayList<>();

    int current = parents[start];
    while (current != start) {
      loop.add(current);
      current = parents[current];
    }

    loop.add(start);
    Collections.reverse(loop);

    StringBuilder result = new StringBuilder();
    result.append("YES").append("\n");

    for (int node : loop) {
      result.append(node).append(" ");
    }

    result.append(start);

    System.out.println(result);
  }

  private static class FlatEdge {
    int source, destination;
    long weight;

    public FlatEdge(int source, int destination, long weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
    }
  }
}

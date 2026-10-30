package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LongestFlightRoute {
  private static final int SYRJALA = 1;

  /*
   * Time Complexity O(n+m)
   * Space Complexity O(n+m)
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      adj.add(new ArrayList<>());
    }

    // Mix in Kahn's algorithm
    int[] numFlightsIn = new int[n+1];

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int u = Integer.parseInt(in.nextToken());
      int v = Integer.parseInt(in.nextToken());

      adj.get(u).add(v);
      numFlightsIn[v]++;
    }

    ArrayDeque<Integer> queue = new ArrayDeque<>();
    int[] parent = new int[n+1];

    // Flush irrelevant flights in
    for (int i = 2; i < n; i++) {
      if (numFlightsIn[i] == 0) {
        queue.add(i);
      }
    }
    
    while (!queue.isEmpty()) {
      int current = queue.poll();

      for (int neighbor: adj.get(current)) {
        // very funny
        if (neighbor != SYRJALA) {
          numFlightsIn[neighbor]--;

          // Do not explore if not last flight in
          if (numFlightsIn[neighbor] == 0) {
            queue.add(neighbor);
          }
        }
      }
    }

    // Now SYRJALA
    queue.add(SYRJALA);

    while (!queue.isEmpty()) {
      int current = queue.poll();

      for (int neighbor: adj.get(current)) {
        numFlightsIn[neighbor]--;

        // Do not explore if not last flight in
        if (numFlightsIn[neighbor] == 0) {
          queue.add(neighbor);
          parent[neighbor] = current;
        }
      }
    }

    if (parent[n] == 0) {
      System.out.println("IMPOSSIBLE");
      return;
    }

    int current = n;
    ArrayList<Integer> path = new ArrayList<>();
    while(current != SYRJALA) {
      path.add(current);
      current = parent[current];
    }
    path.add(SYRJALA);
    
    System.out.println(path.size());

    StringBuilder result = new StringBuilder();
    for (int i = path.size()-1; i > 0; i--) {
        result.append(path.get(i)).append(" ");
    }
    result.append(n);

    System.out.println(result);
  }
}

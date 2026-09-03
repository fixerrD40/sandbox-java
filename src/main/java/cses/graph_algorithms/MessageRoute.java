package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class MessageRoute {

  /*
   * Time Complexity: O(n+m)
   * Space Complexity: O(n+m) -- [network] and its boxed Integers
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    HashMap<Integer, List<Integer>> network = new HashMap<>();

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());

      network.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
      network.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
    }

    HashSet<Integer> visited = new HashSet<>();
    ArrayDeque<Integer> bfs = new ArrayDeque<>();

    visited.add(1);
    bfs.add(1);

    int[] parents = new int[n+1];

    while (!bfs.isEmpty()) {
      int cur = bfs.poll();

      if (cur == n) {
        break;
      }

      List<Integer> toVisit = network.getOrDefault(cur, Collections.emptyList());
      for (int neighbor : toVisit) {
        if (visited.add(neighbor)) {
          parents[neighbor] = cur;
          bfs.add(neighbor);
        }
      }
    }

    if (!visited.contains(n)) {
      System.out.println("IMPOSSIBLE");
    } else {
      int[] path = new int[n];
      int length = 0;
      for (int at = n; at != 0; at = parents[at]) {
        path[length++] = at;
      }

      StringBuilder result = new StringBuilder();
      for (int i = length-1; i >= 0; i--) {
        result.append(path[i]).append(" ");
      }

      System.out.println(length);
      System.out.println(result);
    }
  }
}

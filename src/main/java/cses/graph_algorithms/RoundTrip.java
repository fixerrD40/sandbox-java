package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RoundTrip {

  /*
   * Time Complexity: O(n+m) -- core, looped dsf
   * Space Complexity: O(n+m)
   *
   * Similar strategy to the last problem. I've heretofore avoided dfs,
   * but I felt compelled to use it here because the goal was to dive down a a bad path.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    City[] cities = new City[n+1];
    for (int i = 1; i <= n; i++) {
      cities[i] = new City();
    }

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      
      cities[a].roads.add(b);
      cities[b].roads.add(a);
    }

    boolean[] visited = new boolean[n + 1];
    int[] parent = new int[n + 1];

    int start = -1;
    int end = -1;

    for (int i = 1; i <= n; i++) {
      if (!visited[i]) {
        ArrayDeque<Integer> dfs = new ArrayDeque<>();
        dfs.push(i);

        while(!dfs.isEmpty()) {
          int current = dfs.pop();

          if (visited[current]) continue;
          visited[current] = true;

          for (int neighbor : cities[current].roads) {
            if (neighbor == parent[current]) {
              continue;
            }

            if (visited[neighbor]) {
              start = neighbor;
              end = current;
              break;
            }

            parent[neighbor] = current;
            dfs.push(neighbor);
          }
          if (start != -1) break;
        }
        if (start != -1) break;
      }
    }

    if (start == -1) {
      System.out.println("IMPOSSIBLE");
      return;
    }
    
    StringBuilder result2 = new StringBuilder();
    int result1 = 1;
    result2.append(start).append(" ");

    for (int i = end; i != start; i = parent[i]) {
      result1++;
      result2.append(i).append(" ");
    }

    result1++;
    result2.append(start).append(" ");

    System.out.println(result1);
    System.out.println(result2);
  }

  private static class City {
    ArrayList<Integer> roads = new ArrayList<>();
  }
}

package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class RoundTripII {

  /*
   * Time Complexity: O(n+m) -- core, looped dfs
   * Space Complexity: O(n+m)
   *
   * Very similar to RoundTrip, but crucially the bidirectional roads were replaced with flights.
   * This blew up the assumption in my last algorithm that revisiting a node meant there was a loop.
   *
   * In replacing [visited] with [state] each loop is enabled to decide according to its own state.
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
      
      cities[a].flights.add(b);
    }

    int[] state = new int[n + 1];
    int[] parents = new int[n + 1];

    int start = -1;

    for (int i = 1; i <= n; i++) {
      if (state[i] == 0) {
        ArrayDeque<Integer> dfs = new ArrayDeque<>();
        dfs.push(i);

        while(!dfs.isEmpty()) {
          int current = dfs.peek();

          if (state[current] == 0) {
            state[current] = 1;

            for (int neighbor : cities[current].flights) {
              if (state[neighbor] == 1) {
                parents[neighbor] = current;
                start = neighbor;
                break;
              } else if (state[neighbor] == 0) {
                parents[neighbor] = current;
                dfs.push(neighbor);
              }
            }

            if (start != -1) break;
          } else {
            if (state[current] == 1) {
              state[current] = 2;
            }

            dfs.pop();
          }
        }
        if (start != -1) break;
      }
    }

    if (start == -1) {
      System.out.println("IMPOSSIBLE");
      return;
    }
    
    ArrayList<Integer> roundTrip = new ArrayList<>();

    int current = parents[start];
    while(current != start) {
      roundTrip.add(current);
      current = parents[current];
    }

    roundTrip.add(start);
    Collections.reverse(roundTrip);
    
    StringBuilder result = new StringBuilder();
    result.append(roundTrip.size()+1).append("\n");

    for (int city : roundTrip) {
      result.append(city).append(" ");
    }

    result.append(start);
    System.out.println(result);
  }

  private static class City {
    ArrayList<Integer> flights = new ArrayList<>();
  }
}

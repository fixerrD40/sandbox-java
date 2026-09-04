package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BuildingTeams {

  /*
   * Time Complexity: O(n+m) -- core, looped bsf
   * Space Complexity: O(n+m)
   *
   * The graphs are changing things up.
   * Space is bounding...when n <= 10^5, it is not an option to n^2*boolean for speed.
   * -> *adjacency list*
   *
   * In order to explore a potentially disconnected graph I am bfs within a loop, which is new.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());
    
    Pupil[] pupils = new Pupil[n+1];
    for (int i = 1; i <= n; i++) {
      pupils[i] = new Pupil();
    }

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());

      pupils[a].friendships.add(b);
      pupils[b].friendships.add(a);
    }

    int[] teams = new int[n+1];
    for (int i = 1; i <= n; i++) {
      if (teams[i] == 0) {
        teams[i] = 1;
        ArrayDeque<Integer> bfs = new ArrayDeque<>();
        bfs.add(i);

        while (!bfs.isEmpty()) {
          int pupil = bfs.poll();
          
          for (int neighbor : pupils[pupil].friendships) {
            if (teams[neighbor] == 0) {
              teams[neighbor] = 3-teams[pupil];
              bfs.add(neighbor);
            } else if (teams[pupil] == teams[neighbor]) {
              System.out.println("IMPOSSIBLE");
              return;
            }
          }
        }
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 1; i <= n; i++) {
      result.append(teams[i]).append(" ");
    }

    System.out.println(result);
  }

  private static class Pupil {
    ArrayList<Integer> friendships = new ArrayList<>();
  }
}

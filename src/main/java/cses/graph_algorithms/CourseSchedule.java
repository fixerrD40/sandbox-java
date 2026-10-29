package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CourseSchedule {

  /*
   * Time Complexity: O(n+m)
   * Space Complexity: O(n+m)
   *
   * This is Kahn's algorithm.
   * The realization is that you should explore the classes with no prerequisites as entry points to the graph
   * If you fail to connect to any part of the graph gated [numPrereqs], there is a cycle.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    ArrayList<ArrayList<Integer>> adj = new ArrayList<>(n+1);
    for (int i = 0; i <= n; i++) {
      adj.add(new ArrayList<>());
    }
    
    int[] numPrereqs = new int[n+1];

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int prereq = Integer.parseInt(in.nextToken());
      int course = Integer.parseInt(in.nextToken());

      adj.get(prereq).add(course);
      numPrereqs[course]++;
    }

    ArrayDeque<Integer> stack = new ArrayDeque<>();
    for (int i = 1; i <= n; i++) {
      if (numPrereqs[i] == 0) {
        stack.push(i);
      }
    }

    ArrayList<Integer> order = new ArrayList<>();
    while (!stack.isEmpty()) {
      int course = stack.pop();
      order.add(course);

      for (int neighbor: adj.get(course)) {
        numPrereqs[neighbor]--;
        if (numPrereqs[neighbor] == 0) {
          stack.push(neighbor);
        }
      }
    }

    if (order.size() == n) {
      StringBuilder result = new StringBuilder();
      result.append(order.get(0));

      for (int i = 1; i < n; i++) {
        int course = order.get(i);
        result.append(" ").append(course);
      }

      System.out.println(result.toString());
    } else {
      System.out.println("IMPOSSIBLE");
    }
  }
}

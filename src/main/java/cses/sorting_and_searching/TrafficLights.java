package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class TrafficLights {

  /**
   *  When a split is added, [globalMax] stretch space diverges (=>n+1).
   *    Searching this space can be done cleverly O(logn).
   *
   *  When a split is removed, [globalMax] stretch space converges (=>2).
   *    Searching this space can be done simply O(1).
   *
   *  The optimal solution treats the problem in terms of the latter.
  **/
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int x = Integer.parseInt(in.nextToken());
    int n = Integer.parseInt(in.nextToken());

    in = new StringTokenizer(br.readLine());
    
    TreeSet<Integer> lights = new TreeSet<>();
    lights.add(0);
    lights.add(x);

    // Initialize with all lights and scan the same into queries
    int[] queries = new int[n];
    for (int i = 0; i < n; i++) {
      int light = Integer.parseInt(in.nextToken());
      queries[i] = light;
      lights.add(light);
    }

    // Calculate initial globalMax
    int globalMax = 0;
    int prior = 0;
    for (int light : lights) {
      if (light != 0) {
        globalMax = Math.max(globalMax, light - prior);
      }
      prior = light;
    }

    // Calculate answers in reverse order
    int[] ans = new int[n];
    for (int i = n-1; i >= 0; i--) {
      ans[i] = globalMax;

      int light = queries[i];
      lights.remove(light);

      int next = lights.higher(light);
      int last = lights.lower(light);

      int merged = next-last;

      globalMax = Math.max(globalMax, merged);
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
      result.append(ans[i]).append(" ");
    }

    System.out.println(result);
  }
}

package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SubarraySumsII {

  // O(n)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());
    
    in = new StringTokenizer(br.readLine());

    Map<Long, Integer> sums = new HashMap<>();
    long currentSum = 0;

    sums.put(currentSum, 1);

    long result = 0;
    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      currentSum += value;

      // In this form, we only track prior sums.
      // If any of those are x less, subarray between is x.
      long sought = currentSum - x;

      result += sums.getOrDefault(sought, 0);

      sums.compute(currentSum, (k,v) -> (v == null) ? 1 : v + 1);
    }
    
    System.out.println(result);
  }
}

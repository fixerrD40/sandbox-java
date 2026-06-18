package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DistinctValuesSubsequences {

  static long MOD = 1000000007;

  /**
   * Order did not matter.
   * Worst case: (2^n)-1
   * 2 * 2... - 1
   * Arbitrary case:
   * 20
   * 1 2 3 2 3 2 2 3 1 2 1 1 1 2 3 2 3 3 2 2
   * 1 1 1 1 1 2 2 2 2 2 2 2 2 2 3 3 3 3 3 3
   * 6 * 10 * 7 - 1
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    Map<Integer, Integer> valuesFreqs = new HashMap<>();

    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      valuesFreqs.compute(value, (k,v) -> (v==null) ? 1 : v+1);
    }

    long totalSubsequences = 1;
    for (int freq : valuesFreqs.values()) {
      totalSubsequences = (totalSubsequences * (freq + 1)) % MOD;
    }

    System.out.println(totalSubsequences-1);
  }
}

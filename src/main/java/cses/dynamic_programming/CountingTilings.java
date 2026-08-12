package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountingTilings {
  private static final int MODULO = 1_000_000_007;
  private static int n, m;

  private static int[][] transitions;
  private static int[] transitionCounts;

  /*
   * O(3^n + m*3^n) -> O(m*3^n)
   *
   * Very educational, but I definitely had to look this one up.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer in = new StringTokenizer(br.readLine());
    n = Integer.parseInt(in.nextToken());
    m = Integer.parseInt(in.nextToken());

    transitions = new int[1<<n][1<<n];
    transitionCounts = new int[1<<n];
    long[][] dp = new long[m + 1][1<<n];

    // Precompute valid transitions once
    for (int mask = 0; mask < 1<<n; mask++) {
      buildTransitions(mask, 0, 0);
    }

    // Core iterative DP loop
    dp[0][0] = 1;
    for (int column = 0; column < m; column++) {
      for (int mask = 0; mask < 1<<n; mask++) {
        // Can't transition to if not from
        if (dp[column][mask] == 0) continue;

        for (int i = 0; i < transitionCounts[mask]; i++) {
          int nextMask = transitions[mask][i];
          dp[column+1][nextMask] = (dp[column+1][nextMask] + dp[column][mask]) % MODULO;
        }
      }
    }

    System.out.println(dp[m][0]);
  }

  /*
   * O(1.5^n)
   *
   * DFS bitmask transitions from a column with current mask filled with our tiles
   * Our filled out grid begins (column = 0) and ends (column = m+1) with mask 0
   */
  private static void buildTransitions(int current, int index, int next) {
    if (index == n) {
      // transition complete
      transitions[current][transitionCounts[current]++] = next;
      return;
    }

    if ((current & (1<<index)) != 0) {
      // continue
      buildTransitions(current, index+1, next);
    } else {
      // Horizontal tile placement
      buildTransitions(current, index+1, next | (1<<index));
      // Vertical tile placement
      if (index+1 < n && ((current & (1 << (index+1))) == 0)) {
        buildTransitions(current, index+2, next);
      }
    }
  }
}

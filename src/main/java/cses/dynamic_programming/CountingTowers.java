package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CountingTowers {
  public static int MODULO = 1_000_000_007;

  // O(n+max(t))
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());

    int[] tests = new int[n];
    int maxTest = 0;

    for (int i = 0; i < n; i++) {
      int t = Integer.parseInt(br.readLine());
      tests[i] = t;
      maxTest = Math.max(maxTest, t);
    }

    int[][] dp = new int[maxTest+1][2];
    dp[1][0] = 1;
    dp[1][1] = 1;

    // Each level branches one of six ways dependent on whether or not prior row was connected or not
    for (int i = 2; i <= maxTest; i++) {
      // prior row separated
      dp[i][0] = (int) (((long) dp[i-1][0] * 4 + dp[i-1][1]) % MODULO);

      // prior row connected
      dp[i][1] = (int) (((long) dp[i-1][0] + dp[i-1][1]*2) % MODULO);
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
      int solution = (dp[tests[i]][0] + dp[tests[i]][1]) % MODULO;
      result.append(solution).append("\n");
    }

    System.out.println(result);
  }
}

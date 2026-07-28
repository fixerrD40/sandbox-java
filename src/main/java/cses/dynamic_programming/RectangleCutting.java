package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RectangleCutting {

  /*
   * O(a^2b + ab^2)
   * The greedy strategy degenerates when sides are relatively close before the cut, but far after.
   * ie. 499 500 -> 499 1
   * In fact, there is no fixed geometric pattern because the solution space is highly irregular
   * --It's the knapsack problem in two dimensions.
   *
   * Luckily, we were given this problem is a dp.
   * Construct a table for rectangles of every dimension.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());
    int a = Integer.parseInt(in.nextToken());
    int b = Integer.parseInt(in.nextToken());

    int[][] dp = new int[a+1][b+1];

    for (int i = 1; i <= a; i++) {
      for (int j = 1; j <= b; j++) {
        if (i == j) {
          dp[i][j] = 0;
        } else {
          dp[i][j] = Integer.MAX_VALUE;

          for (int k = 1; k < i; k++) {
            dp[i][j] = Math.min(dp[i][j], dp[k][j] + dp[i-k][j] + 1);
          }

          for (int k = 1; k < j; k++) {
            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[i][j-k] + 1);
          }
        }
      }
    } 

    System.out.println(dp[a][b]);
  }
}

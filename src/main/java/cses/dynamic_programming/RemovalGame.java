package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RemovalGame {

  /*
   * O(n^2)
   *
   * This one was really tricky.
   *
   * The dp is all the nested game states.
   * When you make a selection, you take the value but forfeit initiative over the subsequent state.
   * As such, any given state is worth the greater of its two options less the value of the forfeited substate.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());
    long[][] dp = new long[n+2][n+2];
    long total = 0;

    for (int i = 1; i <= n; i++) {
      int value = Integer.parseInt(in.nextToken());
      dp[i][i] = value;
      total += value;
    }

    for (int i = n-1; i > 0; i--) {
      for (int j = 1; j <= i; j++) {
        int k = j + n-i;
        long leftTake = dp[j][j];
        long leftForfeit = dp[j+1][k];

        long rightTake = dp[k][k];
        long rightForfeit = dp[j][k-1];

        dp[j][k] = Math.max(leftTake-leftForfeit, rightTake-rightForfeit);
      }
    }

    long result = (total + dp[1][n]) / 2;

    System.out.println(result);
  }
}

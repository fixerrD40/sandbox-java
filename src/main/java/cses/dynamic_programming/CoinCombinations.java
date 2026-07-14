package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CoinCombinations {
  public static int MODULO = 1_000_000_007;

  /*
   * O(x*n)
   * The dp relation of sum: coin permutations can be constructed upwards
   * by adding a single one of our denominated coins to an existing sum
   * starting from our base case of 0--no coins.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());

    int[] coins = new int[n];
    int[] dp = new int[x+1];
    dp[0] = 1;
    
    in = new StringTokenizer(br.readLine());
    for (int i = 0; i < n; i++) {
      coins[i] = Integer.parseInt(in.nextToken());
    }

    for (int i = 1; i <= x; i++) {
      for (int coin : coins) {
        if (i-coin >= 0) {
          dp[i] += dp[i-coin];
          if (dp[i] >= MODULO) {
            dp[i] -= MODULO;
          }
        }
      }
    }

    System.out.println(dp[x]);
  }
}

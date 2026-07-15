package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CoinCombinationsII {
  public static int MODULO = 1_000_000_007;

  /*
   * O(x*n)
   * The last algorithm interweaved the coins to track every way of arriving at any sum in the table
   * By constructing the table one coin at a time, we ensure solutions only contain contiguous
   * streams of those coins in that order--representative of our unique combinations.
   * 
   * Neatly, this could be achieved by simply swapping the inner and outer loops.
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

    for (int coin : coins) {
      for (int i = 0; i <= x; i++) {
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

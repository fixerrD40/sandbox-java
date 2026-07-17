package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BookShop {

  /*
   * O(n*x)
   *
   * It's daunting to explore all the combinations
   * Using dp, we apply each book to each remaining budget configuration sequentially.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());

    int[] prices = new int[n];
    int[] pages = new int[n];
    
    StringTokenizer pricesIn = new StringTokenizer(br.readLine());
    StringTokenizer pagesIn = new StringTokenizer(br.readLine());
    for (int i = 0; i < n; i++) {
      prices[i] = Integer.parseInt(pricesIn.nextToken());
      pages[i] = Integer.parseInt(pagesIn.nextToken());
    }

    int[] dp = new int[x+1];

    for (int i = 0; i < n; i++) {
      int cost = prices[i];
      int value = pages[i];

      // Go backward to ensure 0/1 Knapsack (each book used at most once)
      for (int j = x; j >= cost; j--) {
        dp[j] = Math.max(dp[j], dp[j-cost]+value);
      }
    }

    System.out.println(dp[x]);
  }
}

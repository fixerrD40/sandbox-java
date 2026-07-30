package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MoneySums {

  /*
   * O(n*2^n) -> O(2^n)
   *
   * This one is a little funny. The result will be 2^n-1 in the worst case,
   * and each sum must be visited meaning no algorithm escapes O(2^n).
   *
   * Consider input:
   * 4
   * 1 2 4 8
   *
   * This factor dwarfs the impact of how you particularly decide to visit these in the worst case
   * and the problem simply becomes intractable for large n.
   *
   * Cleverly, an alternative approach uses a boolean[maxSum] to achieve O(n*maxSum) and avoid the dedupe and sort
   * which performs valiantly when maxSum < 2^n (many coins, lower value), but poorly when maxSum > 2^n (few coins, high value)
   *
   * Comparing the growth rate of those two factors, the alternative approach is _preferred_.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());
    int[] coins = new int[n];

    for (int i = 0; i < n; i++) {
      coins[i] = Integer.parseInt(in.nextToken());
    }

    HashSet<Integer> dp = new HashSet<>();
    dp.add(0);

    for (int i = 0; i < n; i++) {
      ArrayList<Integer> proxy = new ArrayList<>();
      for (int sum : dp) {
        proxy.add(sum + coins[i]);
      }
      dp.addAll(proxy);
    }

    dp.remove(0);

    int[] sums = new int[dp.size()];
    int i = 0;
    for (int sum : dp) {
        sums[i++] = sum;
    }
    Arrays.sort(sums);

    StringBuilder result = new StringBuilder();
    result.append(sums.length).append("\n");

    for (int sum : sums) {
      result.append(sum).append(" ");
    }

    System.out.println(result);
  }
}

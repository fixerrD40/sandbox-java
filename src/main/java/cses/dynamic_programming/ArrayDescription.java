package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ArrayDescription {
  public static int MODULO = 1_000_000_007;

  // O(n*m)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    int[] values = new int[n+1];
    in = new StringTokenizer(br.readLine());

    int[][] dp = new int[n+1][m+1];

    int firstValue = Integer.parseInt(in.nextToken());
    values[1] = firstValue;
    if (firstValue != 0) {
      dp[1][firstValue] = 1;
    } else {
      for (int j = 1; j <= m; j++) {
        dp[1][j] = 1;
      }
    }

    for (int i = 2; i <= n; i++) {
      int value = Integer.parseInt(in.nextToken());
      values[i] = value;

      int lower = 1;
      int upper = m;
      if (value != 0) {
        lower = value;
        upper = value;
      }

      for (int j = lower; j <= upper; j++) {
        long local = dp[i - 1][j];
        if (j > 1) {
          local += dp[i - 1][j - 1];
        }
        if (j < m) {
          local += dp[i - 1][j + 1];
        }

        dp[i][j] = (int) (local % MODULO);
      }
    }

    int result = 0;
    if (values[n] > 0) {
      result = dp[n][values[n]];
    } else {
      for (int i = 1; i <= m; i++) {
        result += dp[n][i];
        if (result >= MODULO) {
          result -= MODULO;
        }
      }
    }

    System.out.println(result);
  }
}

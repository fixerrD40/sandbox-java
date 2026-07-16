package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GridPaths {
  public static int MODULO = 1_000_000_007;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    
    char[][] grid = new char[n][n];

    for (int i = 0; i < n; i++) {
      String line = br.readLine();
      for (int j = 0; j < n; j++) {
        grid[i][j] = line.charAt(j);
      }
    }

    int[][] dp = new int[n][n];

    if (grid[0][0] != '*') {
      dp[0][0] = 1;
    } else {
      System.out.println(0);
      return;
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i== 0 && j == 0) {
          continue;
        }

        if (grid[i][j] == '*') {
          continue;
        }

        int above = 0;
        if (i > 0) {
          above = dp[i-1][j];
        }

        int before = 0;
        if (j > 0) {
          before = dp[i][j-1];
        }

        dp[i][j] = above+before;

        if (dp[i][j] > MODULO) {
          dp[i][j] -= MODULO;
        }
      }
    }

    System.out.println(dp[n-1][n-1]);
  }
}

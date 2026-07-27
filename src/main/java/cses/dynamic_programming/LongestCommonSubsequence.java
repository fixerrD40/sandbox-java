package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LongestCommonSubsequence {

  // O(n*m)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    int[] sequence1 = new int[n+1];
    in = new StringTokenizer(br.readLine());
    for (int i = 1; i <= n; i++) {
      sequence1[i] = Integer.parseInt(in.nextToken());
    }

    int[] sequence2 = new int[m+1];
    in = new StringTokenizer(br.readLine());
    for (int i = 1; i <= m; i++) {
      sequence2[i] = Integer.parseInt(in.nextToken());
    }

    int[][] dp = new int[n+1][m+1];

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        int above = dp[i-1][j];
        int before = dp[i][j-1];
        int current = Math.max(above, before);

        int behind = dp[i-1][j-1];
        if (sequence1[i] == sequence2[j]) {
          behind++;
        }

        dp[i][j] = Math.max(current, behind);
      }
    }

    int length = dp[n][m];

    int a = n;
    int b = m;
    int[] lcs = new int[length];
    int counter = 0;

    // This loop converges on [1,1] naturally before terminating
    while (a > 0 && b > 0) {
      if (sequence1[a] == sequence2[b]) {
        lcs[counter++] = sequence1[a];
        a--;
        b--;
      } else if (dp[a-1][b] >= dp[a][b-1]) {
        a--;
      } else {
        b--;
      }
    }

    StringBuilder result = new StringBuilder();
    result.append(length).append("\n");
    for (int i = length-1; i >= 0; i--) {
      result.append(lcs[i]).append(" ");
    }

    System.out.println(result);
  }
}

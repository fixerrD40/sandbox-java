package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditDistance {

  // O(n*m)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String a = br.readLine();
    String b = br.readLine();

    int[][] dp = new int[a.length() + 1][b.length() + 1];

    // represents beginning by deleting a
    for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
    // represents beginning by inserting b
    for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

    for (int i = 1; i <= a.length(); i++) {
      for (int j = 1; j <= b.length(); j++) {
        int deletion = dp[i-1][j] + 1;
        int insertion = dp[i][j-1] + 1;
        boolean replace = (a.charAt(i-1) != b.charAt(j-1));
        int accept = dp[i-1][j-1] + (replace ? 1 : 0);

        dp[i][j] = Math.min(accept, Math.min(deletion, insertion));
      }
    }

    System.out.println(dp[a.length()][b.length()]);
  }
}

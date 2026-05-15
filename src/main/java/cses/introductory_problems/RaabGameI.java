package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RaabGameI {
  public static int T = 0;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    T = Integer.parseInt(br.readLine());

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < T; i++) {
      String[] game = br.readLine().split(" ");
      int n = Integer.parseInt(game[0]);
      int a = Integer.parseInt(game[1]);
      int b = Integer.parseInt(game[2]);
      solve(result, n, a, b);
    }
    
    System.out.println(result);
  }

  private static void solve(StringBuilder acc, int n, int a, int b) {
    if ((a == 0) != (b == 0) || n < a+b) {
      acc.append("NO\n");
      return;
    }

    acc.append("YES\n");

    StringBuilder playerA = new StringBuilder();
    StringBuilder playerB = new StringBuilder();

    int round = 1;
    int ties = n-(a+b);
    int i = ties;
    while (i > 0) {
      playerA.append(round).append(" ");
      playerB.append(round).append(" ");
      round++;
      i--;
    }

    int advantage = -1;
    if (a > b) {
      advantage = a-b;
      for (int j = 0; j < a; j++) {
        playerA.append(round+b).append(" ");
        playerB.append(round).append(" ");
        round++;
      }

      int j = 1;
      while (n >= round) {
        playerA.append(ties+j).append(" ");
        playerB.append(round).append(" ");
        round++;
        j++;
      }
    } else if (b > a) {
      advantage = b-a;
      for (int j = 0; j < b; j++) {
        playerA.append(round).append(" ");
        playerB.append(round+a).append(" ");
        round++;
      }

      int j = 1;
      while (n >= round) {
        playerA.append(round).append(" ");
        playerB.append(ties+j).append(" ");
        round++;
        j++;
      }
    } else {
      while (n > round) {
        playerA.append(round).append(" ").append(round+1).append(" ");
        playerB.append(round+1).append(" ").append(round).append(" ");
        round += 2;
      }
    }

    acc.append(playerA).append("\n");
    acc.append(playerB).append("\n");
  }
}

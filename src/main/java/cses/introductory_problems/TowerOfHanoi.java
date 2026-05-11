package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class TowerOfHanoi {
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringBuilder sb = new StringBuilder();

    sb.append((1<<n)-1).append("\n");
    recurse(n, 1, 2, 3, sb);

    System.out.println(sb);
  }

  private static void recurse(int n, int cur, int hop, int dst, StringBuilder result) {
    if (n==1) {
      result.append(cur).append(" ").append(dst).append("\n");
      return;
    }

    // free stone n
    recurse(n - 1, cur, dst, hop, result);

    // move stone n
    result.append(cur).append(" ").append(dst).append("\n");

    // shift
    recurse(n - 1, hop, cur, dst, result);
  }
}

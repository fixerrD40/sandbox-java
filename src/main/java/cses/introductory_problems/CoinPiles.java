package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CoinPiles {
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      sb.append(solve(a, b) + "\n");
    }

    System.out.println(sb);
  }

  private static String solve(int a, int b) {
    int greater = Math.max(a, b);
    int lesser = Math.min(a, b);

    if (lesser*2 < greater) return "NO";

    int base = lesser - (greater - lesser);

    if (base%3 == 0) {
      return "YES";
    } else {
      return "NO";
    }
  }
}

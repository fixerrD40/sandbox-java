package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CollectingNumbers {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());
    
    StringTokenizer input = new StringTokenizer(br.readLine());

    int result = 0;
    boolean[] seen = new boolean[n];
    for (int i = 0; i < n; i++) {
      int cur = Integer.parseInt(input.nextToken()) - 1;
      seen[cur] = true;

      // This doubles as index protection and base case
      if (cur == n-1) {
        result++;
      } else if (seen[cur+1]) {
        result++;
      }
    }

    System.out.println(result);
  }
}

package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MaximumSubarraySum {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());

    StringTokenizer input = new StringTokenizer(br.readLine());
    
    long best = Long.MIN_VALUE;
    long cur = 0;
    for (int i = 0; i < n; i++) {
      long node = cur+Long.parseLong(input.nextToken());
      
      if (node > best) {
        best = node;
      }

      cur = Math.max(0, node);
    }

    System.out.println(best);
  }
}

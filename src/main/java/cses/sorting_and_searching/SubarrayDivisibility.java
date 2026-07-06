package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SubarrayDivisibility {

  // O(n)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());

    long[] remainderCounts = new long[n];
    long currentSum = 0;

    remainderCounts[(int) currentSum]++;

    long result = 0;
    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      currentSum += value;

      // Here lies the trick.
      // Normalize negative remainders which otherwise yield the complement of their positive counterparts.
      int sought = (int) ((currentSum % n) + n) % n;

      result += remainderCounts[sought];

      remainderCounts[sought]++;
    }

    System.out.println(result);
  }
}

package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class DistinctValuesSubarrays {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    Set<Integer> buf = new HashSet<>();
    int[] queries = new int[n];

    StringTokenizer in = new StringTokenizer(br.readLine());

    int j = 0;
    long result = 0;
    for (int i = 0; i < n; i++) {
      int number = Integer.parseInt(in.nextToken());

      // queue
      queries[i] = number;
      if (!buf.add(number)) {
        // drain
        int inner = queries[j];
        while (true) {
          result += buf.size();
          buf.remove(inner);

          j++;

          if (inner == number) {
            break;
          } else {
            inner = queries[j];
          }
        }

        buf.add(number);
      }
    }

    int remaining = buf.size();
    // Gauss' Summation n + (n-1) + ..0
    result += (remaining * (remaining+1)) >> 1;

    System.out.println(result);
  }
}

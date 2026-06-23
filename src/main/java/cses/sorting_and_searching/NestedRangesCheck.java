package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NestedRangesCheck {

  public static class Range {
    int id;
    int lower;
    int upper;

    public Range(int id, int lower, int upper) {
      this.id = id;
      this.lower = lower;
      this.upper = upper;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
    int n = Integer.parseInt(br.readLine());

    Range[] ranges = new Range[n];

    for (int i = 0; i < n; i++) {
      StringTokenizer rangeIn = new StringTokenizer(br.readLine());

      int lower = Integer.parseInt(rangeIn.nextToken());
      int upper = Integer.parseInt(rangeIn.nextToken());

      ranges[i] = new Range(i, lower, upper);
    }

    // I bottle-necked here with Comparator. The fields were auto-boxed into distinct values
    Arrays.sort(ranges, (a, b) -> {
      if (a.lower != b.lower) {
        return Integer.compare(a.lower, b.lower);
      }
      return Integer.compare(b.upper, a.upper);
    });

    boolean[] contains = new boolean[n];
    boolean[] contained = new boolean[n];

    // Min-max sweep
    // The greedy sweep element reduces search on each index n -> logn
    // Nested ranges just fall through
    int maxUpper = 0; 
    for (int i = 0; i < n; i++) {
      Range current = ranges[i];
      if (i > 0 && current.upper <= maxUpper) {
        contained[current.id] = true;
      }
      maxUpper = Math.max(maxUpper, current.upper);
    }

    int minUpper = Integer.MAX_VALUE;
    for (int i = n - 1; i >= 0; i--) {
      Range current = ranges[i];
      if (i < n - 1 && current.upper >= minUpper) {
        contains[current.id] = true;
      }
      minUpper = Math.min(minUpper, current.upper);
    }


    // Lambda mapping functions again performed auto-boxing
    // Note literal "1" and "0" Strings
    StringBuilder result1 = new StringBuilder();
    StringBuilder result2 = new StringBuilder();
    for (int i = 0; i < n; i++) {
      result1.append(contains[i]? "1" : "0").append(" ");
      result2.append(contained[i]? "1" : "0").append(" ");
    }

    System.out.println(result1.toString());
    System.out.println(result2.toString());
  }
}

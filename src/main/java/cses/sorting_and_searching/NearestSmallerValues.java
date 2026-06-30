package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NearestSmallerValues {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());
    int[] values = new int[n];
    int[] nearestSmallerValues = new int[n];

    for (int i=0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());

      int j = i-1;
      nearestSmallerValues[i] = -1;
      while (j > -1) {
        if (values[j] < value) {
          nearestSmallerValues[i] = j;
          break;
        } else {
          j = nearestSmallerValues[j];
        }
      }
      
      values[i] = value;
    }

    StringBuilder result = new StringBuilder();
    for (int idx : nearestSmallerValues) {
      result.append(idx+1).append(" ");
    }

    System.out.println(result);
  }
}

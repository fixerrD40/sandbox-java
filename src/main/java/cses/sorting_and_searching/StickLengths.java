package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class StickLengths {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer input = new StringTokenizer(br.readLine());
    int[] sticks = new int[n];

    for (int i =0; i < n; i++) {
      sticks[i] = Integer.parseInt(input.nextToken());
    }

    Arrays.sort(sticks);
    
    int median = sticks[n/2];

    long result = 0;
    for (int stick : sticks) {
      result += Math.abs(stick-median);
    }
    
    System.out.println(result);
  }
}

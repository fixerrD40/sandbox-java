package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Bookshop {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] parts = br.readLine().split("\\s+");

    int n = Integer.parseInt(parts[0]);
    int x = Integer.parseInt(parts[1]);

    int[] prices = Arrays.stream(br.readLine().split("\\s+"))
      .mapToInt(Integer::parseInt)
      .toArray();

    int[] pages = Arrays.stream(br.readLine().split("\\s+"))
      .mapToInt(Integer::parseInt)
      .toArray();

    int[] bests = new int[x+1];

    for (int i = 0; i < n; i++) {
      int pr = prices[i];
      int pa = pages[i];

      for (int j = x; j >= pr; j--) {
        int localBest = pa + bests[j - pr];
        if (bests[j] < localBest) {
          bests[j] = localBest;
        }
      }
    }

    System.out.println(bests[x]);
  }

}

package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MissingCoinSum {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    int[] coins = new int[n];

    StringTokenizer input = new StringTokenizer(br.readLine());

    for (int i = 0; i < n; i++) {
      coins[i] = Integer.parseInt(input.nextToken());
    }

    Arrays.sort(coins);
    
    long floor = 0;

    for (int coin : coins) {
      long next = floor+1;
      if (next < coin) {
        break;
      }

      floor += coin;
    }

    System.out.println(floor+1);
  }
}

package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ArrayDivision {

  /*
   * O(nlog(sum))
   *
   * Similar to FactoryMachines
   * Any bottom-up solution dispersing the weight was just too complex (O(n^2)),
   * but testing an answer is relatively cheap (O(n))
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int k = Integer.parseInt(in.nextToken());

    in = new StringTokenizer(br.readLine());

    int[] values = new int[n];
    long maxValue = 0;
    long sum = 0;

    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      values[i] = value;
      maxValue = Math.max(maxValue, value);
      sum += value;
    }

    long low = maxValue;
    long high = sum;
    long result = high;

    while (low <= high) {
      long mid = low + (high - low) / 2;

      if (calculateSubarrays(values, mid) <= k) {
        result = mid;
        high = mid-1;
      } else {
        low = mid+1;
      }
    }

    System.out.println(result);
  }

  private static int calculateSubarrays(int[] array, long maxSum) {
    int result = 1;
    long currentSum = 0;

    for (int value : array) {
      if (currentSum+value > maxSum) {
        currentSum = value;
        result++;
      } else {
        currentSum += value;
      }
    }

    return result;
  }
}

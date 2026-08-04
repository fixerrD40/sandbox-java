package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class IncreasingSubsequence {

  /*
   * O(nlogn)
   *
   * At any given point, the longest increasing subsequence is the max of prior, lesser points' +1
   * Coordinate compression is used to reduce search space n -> unique n.
   * the [dp] is implemented as a Fenwick Tree to improve search unique n -> log unique n
   *
   * I'm including an alternateSolution that shows better understanding of the problem--
   * achieving the same complexity more simply by eagerly pruning inconsequential tails.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());
    int[] values = new int[n];
    HashSet<Integer> uniqueValues = new HashSet<>();

    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      values[i] = value;
      uniqueValues.add(value);
    }

    int[] coords = uniqueValues.stream().mapToInt(Integer::intValue).toArray();
    Arrays.sort(coords);
    
    FenwickTree dp = new FenwickTree(coords.length);

    int result = 0;
    for (int i = 0; i < n; i++) {
      int compressedValue = Arrays.binarySearch(coords, values[i]) + 1;

      int maxSubsequence = dp.query(compressedValue - 1);

      dp.update(compressedValue, maxSubsequence+1);
      result = Math.max(result, maxSubsequence+1);
    }

    System.out.println(result);
  }

  public static int alternateSolution(int n, int[] values) {
    int[] tails = new int[n];
    int len = 0;

    for (int x : values) {
        int i = Arrays.binarySearch(tails, 0, len, x);
        if (i < 0) i = -(i + 1); // Get insertion point from ret
        
        tails[i] = x;
        if (i == len) len++;
    }

    return len;
  }

  // Fenwick Tree (Binary Indexed Tree) to store max DP values
  static class FenwickTree {
    int[] tree;
    int size;

    FenwickTree(int size) {
      this.size = size;
      this.tree = new int[size + 1];
    }

    // Update the max value at a specific rank
    void update(int index, int value) {
      for (; index <= size; index += index & -index) {
        tree[index] = Math.max(tree[index], value);
      }
    }

    // Query the maximum value from rank 1 up to 'index'
    int query(int index) {
      int maxVal = 0;
      for (; index > 0; index -= index & -index) {
        maxVal = Math.max(maxVal, tree[index]);
      }
      return maxVal;
    }
  }
}

package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class IncreasingSubsequenceII {
  private static final int MODULO = 1_000_000_007;

  /*
   * O(nlogn)
   *
   * Luckily, performing the prior IncreasingSubsequence the hard way made this one simpler.
   *
   * Shift the Fenwick Tree's purpose: instead of tracking the maximum length via Math.max,
   * it now maintains the running summation of all valid subsequence paths (MODULO applied).
   *
   * For each element, a prefix query on lesser coordinates yields the number of valid prefixes.
   * Adding 1 accounts for starting a new sequence at the current element.
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

    for (int i = 0; i < n; i++) {
      int compressedValue = Arrays.binarySearch(coords, values[i]) + 1;

      int subsequences = (dp.query(compressedValue-1)+1) % MODULO;

      dp.update(compressedValue, subsequences);
    }

    System.out.println(dp.query(coords.length));
  }

  // Fenwick Tree (Binary Indexed Tree) to store num subsequences
  private static class FenwickTree {
    int[] tree;
    int size;

    FenwickTree(int size) {
      this.size = size;
      this.tree = new int[size + 1];
    }

    // Add value to a specific index
    void update(int index, int value) {
      for (; index <= size; index += index & -index) {
        tree[index] += value;
        if (tree[index] >= MODULO) {
          tree[index] -= MODULO;
        }
      }
    }

    // Query the sum from index 1 up to 'index'
    int query(int index) {
      int sum = 0;
      for (; index > 0; index -= index & -index) {
        sum = (sum + tree[index]) % MODULO;
      }
      return sum;
    }
  }
}

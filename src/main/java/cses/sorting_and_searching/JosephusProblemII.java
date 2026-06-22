package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class JosephusProblemII {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int k = Integer.parseInt(in.nextToken());
    
    String result = solution(n, k);

    System.out.println(result);
  }

  private static class FenwickTree {
    private int[] data;
    int[] tree;

    public FenwickTree(int[] data) {
      this.data = data;
      int n = data.length-1;
      this.tree = new int[data.length];
      
      for (int i = 1; i <= n; i++) {
        tree[i] = data[i];
      }
      
      for (int i = 1; i <= n; i++) {
        int parent = i + (i & -i);
        if (parent <= n) {
          tree[parent] += tree[i];
        }
      }
    }

    public void update(int i, int value) {
      int diff = value-data[i];

      data[i] = value;

      int curr = i;
      while (curr < tree.length) {
        tree[curr] += diff;
        // increment last set bit
        // >-\  -    -        -
        //    -
        curr += (curr & -curr);
      }
    }

    public int query(int i) {
      int result = 0;
      
      int curr = i;
      while (curr > 0) {
        result += tree[curr];
        // decrement last set bit
        // -
        //  \
        //  ^\
        curr -= (curr & -curr);
      }

      return result;
    }
  }

  /**
   * Fenwick tree
   * Initialize O(n)
   * + n * (search O(logn), update O(logn))
   * O(nlogn)
   *
   * at n=2e5, nlogn=3.5e6
   * Looking at .35 seconds
   */
  private static String solution(int n, int k) {
    int[] allPresent = new int[n+1];

    allPresent[0] = 0;
    for (int i = 1; i <= n; i++) {
      allPresent[i] = 1;
    }

    FenwickTree tree = new FenwickTree(allPresent);
    StringBuilder result = new StringBuilder();

    int currentPosition = 0;
    int high = 32 - Integer.numberOfLeadingZeros(n);
    for (int i = n; i > 0; i--) {
      currentPosition = (currentPosition + k) % i;

      // tree is data shifted right 1
      int target = currentPosition+1;
        
      // Perform binary lifting for search O(logn)
      // Purposefully avoid FenwickTree.query() yielding O(logn) for a single call -> O(log^2n)
      // A traditional binary search might head down the tree for query(pivot), then come right back up
      // Binary lifting performed directly on the tree structure strictly hones in in one pass
      int idx = 0;
      int accumulatedSum = 0;

      for (int j = high; j >= 0; j--) {
        // -  -    -        -<
        int nextIdx = idx + (1<<j);
          
        if (nextIdx <= n) {
          if (accumulatedSum + tree.tree[nextIdx] < target) {
            // -  -    -\v     -
            //           -
            idx = nextIdx;
            accumulatedSum += tree.tree[idx];
          }
        }
      }

      // Search positions us directly before the index of the target child
      int selected = idx+1;

      result.append(selected).append(" ");

      tree.update(selected, 0); 
    }

    return result.toString();
  }

  /**
   * Too slow
   * Initialize O(n)
   * + n * (Index selection O(1), Library ArrayList.remove() O(n))
   * O(n^2)
   *
   * Gemini pointed out judge can do ~1e8 operations in the second
   * at n=2e5, n^2=4e10
   * Looking at 400 seconds
   */
  private static String solution2(int n, int k) {
    List<Integer> children = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      children.add(i);
    }

    StringBuilder result = new StringBuilder();

    int last = 0;
    while (!children.isEmpty()) {
      int idx = (last+k) % children.size();

      result.append(children.remove(idx)).append(" ");

      last = idx;
    }

    return result.toString();
  }

  // Too slow now
  private static String solution1(int n, int k) {
    Queue<Integer> queue = new ArrayDeque<>(n);

    for (int i = 1; i <= n; i++) {
      queue.add(i);
    }

    StringBuilder result = new StringBuilder();

    while (!queue.isEmpty()) {
      for (int i = 0; i < k; i++) {
        queue.add(queue.poll());
      }
      
      result.append(queue.poll()).append("\n");
    }

    return result.toString();
  }
}

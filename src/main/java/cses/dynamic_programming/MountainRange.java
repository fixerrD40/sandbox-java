package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class MountainRange {
  private static int n;
  private static int[] mountains;

  /*
   * O(nlogn)
   *
   * This one plain beat me, but I learned two new data structures.
   * The monotonic stack is used to calculate the next mountain that cannot be summited.
   * The segment tree is used as the dp, exposing the next optimal mountain.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    n = Integer.parseInt(br.readLine());
    mountains = new int[n];

    StringTokenizer in = new StringTokenizer(br.readLine());

    int[] sortedIndices = new int[n];
    MonotonicStack monotonicStack = new MonotonicStack();
    int[] lBoundaries = new int[n];

    for (int i = 0; i < n; i++) {
      int mountain = Integer.parseInt(in.nextToken());
      mountains[i] = mountain;
      sortedIndices[i] = i;
      lBoundaries[i] = monotonicStack.process(i, -1);
    }

    monotonicStack.clear();
    int[] rBoundaries = new int[n];

    for (int i = n-1; i >= 0; i--) {
      rBoundaries[i] = monotonicStack.process(i, n);
    }

    dualArraySort(sortedIndices, mountains, 0, n-1);
    
    SegmentTree dp = new SegmentTree();
    int result = 0;

    for (int i : sortedIndices) {
      int left = lBoundaries[i]+1;
      int right = rBoundaries[i]-1;

      int bestPrevPath = dp.query(left, right);
      int flights = 1 + bestPrevPath;

      result = Math.max(result, flights);
      dp.update(i, flights);
    }

    System.out.println(result);
  }

  private static class MonotonicStack {
    private Deque<Integer> state = new ArrayDeque<>();

    // _amortized_ O(1)
    // max is ordinal
    public int process(int i, int max) {
      int result = 0;
      while (!state.isEmpty() && mountains[i] > mountains[state.peek()]) {
        state.pop();
      }

      if (state.isEmpty()) {
        result = max;
      } else {
        result = state.peek();
      }

      state.push(i);

      return result;
    }

    public void clear() {
      state.clear();
    }
  }

  // O(nlogn)
  private static void dualArraySort(int[] indices, int[] heights, int low, int high) {
    if (low >= high) return;
    int pivot = heights[indices[low + (high - low) / 2]];
    int i = low, j = high;
    while (i <= j) {
      while (heights[indices[i]] < pivot) i++;
      while (heights[indices[j]] > pivot) j--;
      if (i <= j) {
        int temp = indices[i];
        indices[i] = indices[j];
        indices[j] = temp;
        i++; j--;
      }
    }
    dualArraySort(indices, heights, low, j);
    dualArraySort(indices, heights, i, high);
  }

  private static class SegmentTree {
    private int[] state = new int[2*n];

    // O(logn)
    public void update(int pos, int value) {
      pos += n;

      state[pos] = value;
      for (int i = pos; i > 1; i >>= 1) {
        state[i>>1] = Math.max(state[i], state[i^1]);
      }
    }

    // O(logn)
    public int query(int start, int end) {
      if (start > end) return 0;
      int maxVal = 0;

      start += n;
      end += n+1; // Exclusive upper bound

      while (start < end) {
        if ((start&1) == 1) maxVal = Math.max(maxVal, state[start++]);
        if ((end&1) == 1) maxVal = Math.max(maxVal, state[--end]);
        start >>= 1;
        end >>= 1;
      }

      return maxVal;
    }
  }
}

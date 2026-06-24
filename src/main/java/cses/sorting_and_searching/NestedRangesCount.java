package cses.sorting_and_searching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
 
public class NestedRangesCount {
 
   // I had to absolutely butcher my original implementation of the correct answer
   // This included using FastScanner and dropping custom Range class and Arrays.sort() over its fields
  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();

    int[] lowers = new int[n];
    int[] uppers = new int[n];
    int[] compressedUppers = new int[n+1];
    int[] ids = new int[n];

    for (int i = 0; i < n; i++) {
      lowers[i] = scanner.nextInt();
      uppers[i] = scanner.nextInt();
      compressedUppers[i] = uppers[i];
      ids[i] = i;
    }

    // Perform Coordinate Compression
    // Memory limit: 512 MB
    // 1E9 (y) ints is ~4GB
    // 2E5 (n) ints is ~800KB
    // Further less sparse data will mean better tree performance
    Arrays.sort(compressedUppers);

    int maxRank = 2;
    int prev = compressedUppers[1];
    for (int i = 2; i <= n; i++) {
      if (compressedUppers[i] != prev) {
        prev = compressedUppers[i];
        compressedUppers[maxRank++] = compressedUppers[i];
      }
    }

    FenwickTree tree = new FenwickTree(maxRank+1);

    quickSort(ids, lowers, uppers, 0, n - 1);

    int[] contains = new int[n];

    for (int i = n-1; i >= 0; i--) {
      int id = ids[i];
      int rankedEnd = Arrays.binarySearch(compressedUppers, 1, maxRank, uppers[id]);

      contains[id] = tree.query(rankedEnd);
      tree.increment(rankedEnd);
    }

    tree = new FenwickTree(maxRank+1);

    int[] contained = new int[n];

    for (int i = 0; i < n; i++) {
      int id = ids[i];
      int rankedEnd = Arrays.binarySearch(compressedUppers, 1, maxRank, uppers[id]);

      // Mind this is the complement: exclude upper bound
      contained[id] = i - tree.query(rankedEnd-1);
      tree.increment(rankedEnd);
    }

    StringBuilder result1 = new StringBuilder();
    StringBuilder result2 = new StringBuilder();
    for (int i = 0; i < n; i++) {
      result1.append(contains[i]).append(" ");
      result2.append(contained[i]).append(" ");
    }

    System.out.println(result1.toString().trim());
    System.out.println(result2.toString().trim());
  }

  // Credit to Gemini, at this point I did not have implementing custom quickSort in me
  private static void quickSort(int[] ids, int[] lowers, int[] uppers, int left, int right) {
    if (left >= right) return;
    int midIdx = ids[left + (right - left) / 2];
    int pivotLower = lowers[midIdx];
    int pivotUpper = uppers[midIdx];
    
    int i = left, j = right;
    while (i <= j) {
      while (lowers[ids[i]] < pivotLower || (lowers[ids[i]] == pivotLower && uppers[ids[i]] > pivotUpper)) i++;
      while (lowers[ids[j]] > pivotLower || (lowers[ids[j]] == pivotLower && uppers[ids[j]] < pivotUpper)) j--;
      if (i <= j) {
        int temp = ids[i];
        ids[i] = ids[j];
        ids[j] = temp;
        i++;
        j--;
      }
    }
    quickSort(ids, lowers, uppers, left, j);
    quickSort(ids, lowers, uppers, i, right);
  }

  private static class FenwickTree {
    private final int[] tree;

    public FenwickTree(int size) {
      this.tree = new int[size];
    }

    public void increment(int i) {
      while (i < tree.length) {
        tree[i]++;
        // increment last set bit
        // >-\  -    -        -
        //    -
        i += (i & -i);
      }
    }

    public int query(int i) {
      int result = 0;
      
      while (i > 0) {
        result += tree[i];
        // decrement last set bit
        // -
        //  \
        //  ^\
        i -= (i & -i);
      }

      return result;
    }
  }

  // High-performance tokenless scanner class
  private static class FastScanner {
    private final InputStream stream;
    private final byte[] buffer = new byte[1 << 16];
    private int head = 0, tail = 0;

    public FastScanner(InputStream stream) { 
      this.stream = stream; 
    }

    private int read() throws IOException {
      if (head >= tail) {
        head = 0;
        tail = stream.read(buffer, 0, buffer.length);
        if (tail <= 0) return -1;
      }
      return buffer[head++];
    }

    public int nextInt() throws IOException {
      int c = read();
      while (c <= 32) {
        if (c == -1) return -1;
        c = read();
      }
      int res = 0;
      while (c > 32) {
        if (c < '0' || c > '9') throw new RuntimeException();
        res = res * 10 + c - '0';
        c = read();
      }
      return res;
    }
  }
}

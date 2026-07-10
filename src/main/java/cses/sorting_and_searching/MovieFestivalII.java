package cses.sorting_and_searching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.TreeMap;

public class MovieFestivalII {

  /*
   * O(nlogn)
   *
   * I tried a min heap first, but there is a disconnect between next movie and watcher to assign.
   * MovieFestival showed movies are to be assigned by ascending _end_ time,
   * but you want to greedily assign the last available watcher before any movie's _start_.
   *
   * TreeMap BST with its floorKey() works perfect.
   */
  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int k = scanner.nextInt();

    int[][] movies = new int[n][2];

    for (int i = 0; i < n; i++) {
      movies[i][0] = scanner.nextInt();
      movies[i][1] = scanner.nextInt();
    }

    Arrays.sort(movies, (a, b) -> Integer.compare(a[1], b[1]));

    TreeMap<Integer, Integer> syrjala = new TreeMap<>();
    syrjala.put(0, k);

    int result = 0;
    for (int i = 0; i < n; i++) {
      int start = movies[i][0];
      int end = movies[i][1];

      Integer available = syrjala.floorKey(start);

      if (available != null) {
        int availableCount = syrjala.get(available);
        if (availableCount == 1) {
          syrjala.remove(available);
        } else {
          syrjala.put(available, availableCount - 1);
        }

        syrjala.put(end, syrjala.getOrDefault(end, 0) + 1);
        result++;
      }
    }

    System.out.println(result);
  }

  // High-performance custom byte-level scanner
  private static class FastScanner {
    private final InputStream stream;
    private final byte[] buffer = new byte[1024];
    private int head = 0;
    private int tail = 0;

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

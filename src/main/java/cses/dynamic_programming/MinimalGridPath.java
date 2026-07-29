package cses.dynamic_programming;

import java.io.InputStream;
import java.io.IOException;

public class MinimalGridPath {

  /*
   * O((2*n-2)*n)
   *
   * The problem advertises itself as a dp,
   * but I couldn't get the dp solution to pass in java at O(n^2logn).
   *
   * Lexicographical is a fatal constraint because String compare is O(n)
   *
   * Instead implemented a greedy bfs that maintains optimal paths at any k
   * where k = i + j
   */
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner(System.in);

    int n = sc.nextInt();
    char[][] grid = new char[n][n];

    for (int i = 0; i < n; i++) {
      String line = sc.next();
      for (int j = 0; j < n; j++) {
        grid[i][j] = line.charAt(j);
      }
    }

    boolean[][] visited = new boolean[n][n];
    
    // List to hold the current diagonal layer of optimal coordinates
    GridCell[] currentLayer = new GridCell[n];
    
    StringBuilder result = new StringBuilder();
    result.append(grid[0][0]);
    
    currentLayer[0] = new GridCell(0, 0);
    visited[0][0] = true;

    int totalMoves = 2 * n - 2;
    
    for (int step = 0; step < totalMoves; step++) {
      char nextMinChar = '[';
      GridCell[] nextLayer = new GridCell[n];

      // Find the minimum character possible in the next step
      for (GridCell cell : currentLayer) {
        if (cell == null) continue;
        int i = cell.row;
        int j = cell.col;

        if (i < n - 1) {
          if (grid[i + 1][j] < nextMinChar) {
            nextMinChar = grid[i + 1][j];
          }
        }

        if (j < n - 1) {
          if (grid[i][j + 1] < nextMinChar) {
            nextMinChar = grid[i][j + 1];
          }
        }
      }

      result.append(nextMinChar);

      // Only collect next coordinates that match this minimal character
      for (GridCell cell : currentLayer) {
        if (cell == null) continue;
        int i = cell.row;
        int j = cell.col;

        if (i < n - 1 && grid[i + 1][j] == nextMinChar && !visited[i + 1][j]) {
          visited[i + 1][j] = true;
          nextLayer[i+1] = new GridCell(i + 1, j);
        }
        if (j < n - 1 && grid[i][j + 1] == nextMinChar && !visited[i][j + 1]) {
          visited[i][j + 1] = true;
          nextLayer[i] = new GridCell(i, j + 1);
        }
      }

      currentLayer = nextLayer;
    }

    System.out.println(result.toString());
  }

  public static class GridCell {
    final int row;
    final int col;

    public GridCell(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  // High-performance byte-level custom stream parser
  public static class FastScanner {
    private final InputStream in;
    private final byte[] buffer = new byte[32768];
    private int head = 0;
    private int tail = 0;

    public FastScanner(InputStream in) {
      this.in = in;
    }

    private int read() throws IOException {
      if (head >= tail) {
        head = 0;
        tail = in.read(buffer, 0, buffer.length);
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
      boolean neg = false;
      if (c == '-') {
        neg = true;
        c = read();
      }
      int resN = 0;
      while (c >= '0' && c <= '9') {
        resN = resN * 10 + c - '0';
        c = read();
      }
      return neg ? -resN : resN;
    }

    public String next() throws IOException {
      int c = read();
      while (c <= 32) {
        if (c == -1) return "";
        c = read();
      }
      StringBuilder sb = new StringBuilder();
      while (c > 32) {
        sb.append((char) c);
        c = read();
      }
      return sb.toString();
    }
  }
}

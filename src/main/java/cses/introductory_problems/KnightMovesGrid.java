package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

public class KnightMovesGrid {
  static int[][] MOVES = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

  // Cool point from ai.
  // with n<1000 we can stuff both x and y into a single int:
  // 1000*1000 < Integer.MAX_VALUE
  // OR 1000 < 1<<16
  private static final class Point {
    public static int pack(int x, int y) {
        return (x << 16) | y;
    }

    public static int getX(int value) {
        return value >> 16;
    }

    public static int getY(int value) {
        return value & 0xFFFF;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    if (n > 65536) {
        throw new IllegalArgumentException("Grid size exceeds 16-bit packing limits.");
    }

    int[][] grid = new int[n][n];
    for (int[] row : grid) {
        Arrays.fill(row, -1);
    }
    
    populateGrid(n, grid);

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
      String line = Arrays.stream(grid[i])
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));

      result.append(line).append("\n");
    }

    System.out.println(result);
  }

  private static void populateGrid(int n, int[][] grid) {
    Deque<Integer> bfs = new ArrayDeque<>();
    grid[0][0] = 0;
    bfs.add(0);
    
    while (!bfs.isEmpty()) {
      int cur = bfs.pollFirst();
      int x = Point.getX(cur);
      int y = Point.getY(cur);

      for (int[] move : MOVES) {
        int nextX = x + move[0];
        int nextY = y + move[1];

        if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < n) {
          if (grid[nextX][nextY] == -1) {
            grid[nextX][nextY] = grid[x][y] + 1;
            bfs.addLast(Point.pack(nextX, nextY));
          }
        }
      }
    }
  }
}

package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class GridColoringI {
  private static final char[] COLORS = {'A', 'B', 'C', 'D'};
  private static final Map<Character, Integer> COLOR_BITMASKS = Map.of(
        'A', 0b0001,
        'B', 0b0010,
        'C', 0b0100,
        'D', 0b1000
  );

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] dimensions = br.readLine().split(" ");
    int rows = Integer.parseInt(dimensions[0]);
    int columns = Integer.parseInt(dimensions[1]);

    char[][] grid = new char[rows][columns];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
          grid[i][j] = (char) br.read();
      }
      br.read(); // drop newline
    }

    colorGrid(grid, rows, columns);
    
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      result.append(grid[i]).append("\n");
    }

    System.out.println(result);
  }

  private static void colorGrid(char[][] grid, int rows, int columns) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        int options = 15;

        if (i > 0) {
          int aboveColorMask = COLOR_BITMASKS.get(grid[i-1][j]);
          options &= ~aboveColorMask;
        }

        if (j > 0) {
          int aftColorMask = COLOR_BITMASKS.get(grid[i][j-1]);
          options &= ~aftColorMask;
        }

        int colorMask = COLOR_BITMASKS.get(grid[i][j]);
        options &= ~colorMask;

        if (options != 0) {
          int index = Integer.numberOfTrailingZeros(options);
          grid[i][j] = COLORS[index]; 
        }
  }
    }

  }
}

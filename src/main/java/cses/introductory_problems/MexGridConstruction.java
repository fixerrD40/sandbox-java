package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MexGridConstruction {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    int size = -1;

    // Find the power of two that can cleanly contain n
    int highestOneBit = Integer.highestOneBit(n);
    if (highestOneBit < n) {
      size = highestOneBit<<1; 
    } else {
      size = highestOneBit;
    }

    int[][] grid = new int[size][size];

    populateGrid(grid, 0, size);

    // extract the subgrid we need
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
      String line = Arrays.stream(grid[i], 0, n)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));

      result.append(line).append("\n");
    }

    System.out.println(result);
  }

  private static void populateGrid(int[][] dst, int base, int size) {
    if (size > 1) {
      int subSize = size/2;
      int sub = base+subSize;
      populateGrid(dst, base, subSize);
      populateGrid(dst, sub, subSize);

      // proliferate sub grids to superSubIndices
      // 0 1    0 1
      //        1 0
      for (int i = 0; i < subSize; i++) {
        int superSubIndex = subSize+i;
        System.arraycopy(dst[i], base, dst[superSubIndex], sub, subSize);
        System.arraycopy(dst[i], sub, dst[superSubIndex], base, subSize);
      }
    } else {
      dst[0][base] = base;
    }
  }
}

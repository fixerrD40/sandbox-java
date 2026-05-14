package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChessboardAndQueens {
  static int ROWS = 8;
  static int COLUMNS = 8;
  static boolean[][] BOARD = new boolean[ROWS][COLUMNS];
  static boolean[] ROW_SELECTIONS = new boolean[ROWS];
  static int[] LAYOUT = new int[ROWS];

  static int SOLUTIONS = 0;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    for (int i = 0; i < ROWS; i++) {
      String line = br.readLine();
      for (int j = 0; j < COLUMNS; j++) {
          BOARD[i][j] = line.charAt(j) == '*'; 
      }
    }
    
    solve(0);

    System.out.println(SOLUTIONS);
  }

  private static void solve(int column) {
    for (int row = 0; row < ROWS; row++) {
      // check reserved
      if (BOARD[row][column]) continue;

      // check for horizontal collisions
      if (ROW_SELECTIONS[row]) continue;

      boolean valid = true;
      for (int queen = 0; queen < column; queen++) {
        // check for left diagonal collisions
        if (column+row == queen+LAYOUT[queen]) valid = false;
        // check for right diagonal collisions
        if (column-row == queen-LAYOUT[queen]) valid = false;
      }
      if (!valid) continue;
      
      LAYOUT[column] = row;
      if (column == COLUMNS-1) {
        SOLUTIONS++;
        continue;
      }

      ROW_SELECTIONS[row] = true;

      solve(column+1);

      ROW_SELECTIONS[row] = false;
    }
  }
}

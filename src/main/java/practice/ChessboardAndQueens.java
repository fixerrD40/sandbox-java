package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChessboardAndQueens {
  private static int N;
  private static int RESULT = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    N = Integer.parseInt(br.readLine());

    int[] board = new int[N];

    for (int i = 0; i < N; i++) {
      String row = br.readLine();

      for (int j = 0; j < N; j++) {
        if (row.charAt(j) == 'Q') {
          board[j] = i;
        }
      }
    }

    solve(board, new int[N], 0);

    System.out.println(RESULT);
  }

  private static void solve(int[] original, int[] current, int column) {
    if (column == N) {
        RESULT = Math.min(RESULT, calculateCost(original, current));
        return;
    }

    for (int row = 0; row < N; row++) {
      current[column] = row;
      
      if (isValid(current, column)) {
        solve(original, current, column+1);
      }
    }
  }

  private static boolean isValid(int[] current, int column) {
    for (int i = 0; i < column; i++) {
      if (current[i] == current[column]) return false;

        if (current[i] + i == current[column] + column) return false;
        if (current[i] - i == current[column] - column) return false;
    }

    return true;
  }

  private static int calculateCost(int[] original, int[] solution) {
    int cost = 0;
    
    for (int i = 0; i < N; i++) {
      cost += Math.abs(original[i] - solution[i]);
    }

    return cost;
  }
}

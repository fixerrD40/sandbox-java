package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GridPathDescription {
  private static int SIDE = 7;
  private static boolean[] SEEN = new boolean[1 << 6];
  private static char[] MOVES = {'R', 'D', 'L', 'U'};
  // Impressively lazy primitive map
  private static final int[] MOVE_INDEX = new int[128];
  private static int[][] TRANSLATIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

  static {
      MOVE_INDEX['R'] = 0;
      MOVE_INDEX['D'] = 1;
      MOVE_INDEX['L'] = 2;
      MOVE_INDEX['U'] = 3;
  }

  private static int RESULT = 0;

  // Pack two coordinates x, y into the first two trios of bits of an int
  // x and y are > 0 and <= $SIDE
  private static final class Point {
    public static int pack(int x, int y) {
        return (x << 3) | y;
    }

    public static int getX(int value) {
        return value >> 3;
    }

    public static int getY(int value) {
        return value & 0b111;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    char[] input = br.readLine().toCharArray();

    SEEN[0] = true;
    if (input[0] == '?') {
      solve(input, 1, 8, 'R');
      solve(input, 1, 1, 'D');
    } else if (input[0] == 'R') {
      solve(input, 1, 8, 'R');
    } else if (input[0] == 'D') {
      solve(input, 1, 1, 'D');
    } else {
      System.out.println(0);
      return;
    }

    System.out.println(RESULT);
  }

  private static void solve(char[] input, int seenCount, int point, char lastDirection) {
    if (SEEN[point]) {
      return;
    }

    int x = Point.getX(point);
    int y = Point.getY(point);

    if (x == 0 && y == 6) {
      if (seenCount == SIDE * SIDE - 1) {
        RESULT++;
      }
      return;
    }

    // closing off an open space--fail fast!
    // adjacent
    boolean left = x-1 >= 0 && !SEEN[Point.pack(x-1, y)];
    boolean right = x+1 < SIDE && !SEEN[Point.pack(x+1, y)];
    boolean up = y-1 >= 0 && !SEEN[Point.pack(x, y-1)];
    boolean down = y+1 < SIDE && !SEEN[Point.pack(x, y+1)];

    if (up && down && !left && !right) return;
    if (left && right && !up && !down) return;

    // corner
    boolean upLeft = x-1 >= 0 && y-1 >= 0 && !SEEN[Point.pack(x-1, y-1)];
    boolean upRight = x+1 < SIDE && y-1 >= 0 && !SEEN[Point.pack(x+1, y-1)];
    boolean lowLeft = x-1 >= 0 && y+1 < SIDE && !SEEN[Point.pack(x-1, y+1)];
    boolean lowRight = x+1 < SIDE && y+1 < SIDE && !SEEN[Point.pack(x+1, y+1)];

    if (!up && !left && upLeft) return;
    if (!up && !right && upRight) return;
    if (!down && !left && lowLeft) return;
    if (!down && !right && lowRight) return;

    SEEN[point] = true;

    for (int i = 0; i < MOVES.length; i++) {
      char move = MOVES[i];
      if (input[seenCount] != '?' && input[seenCount] != move) continue;
 
      char inverseMove = MOVES[(i+2)%MOVES.length];
      if (inverseMove == lastDirection) continue;
 
      int[] translation = TRANSLATIONS[i];
      int nextX = x + translation[0];
      int nextY = y + translation[1];
 
      if (nextX >= 0 && nextX < SIDE && nextY >= 0 && nextY < SIDE) {
        solve(input, seenCount+1, Point.pack(nextX, nextY), move);
      }
    }

    SEEN[point] = false;
  }
}

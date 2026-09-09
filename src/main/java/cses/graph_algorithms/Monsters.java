import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Monsters {

  private static int MOVES = 4;
  // Up, down, left, right
  private static int[] dr = {-1, 1, 0, 0};
  private static int[] dc = {0, 0, -1, 1};

  private static char[] unwrapMoves = {'D', 'U', 'R', 'L'};

  /*
   * Time Complexity: O(n+m)
   * Space Complexity: O(n+m)
   *
   * New concept of multi-source bfs
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    char[][] labyrinth = new char[n][m];
    Point start = null;
    ArrayList<Point> monsters = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      String row = br.readLine();
      for (int j = 0; j < m; j++) {
        char tile = row.charAt(j);

        if (tile == 'M') {
          monsters.add(new Point(i, j));
          labyrinth[i][j] = '.';
        } else if (tile == 'A') {
          start = new Point(i, j);
          labyrinth[i][j] = '.';
        } else {
          labyrinth[i][j] = row.charAt(j);
        }
      }
    }

    int[][] monsterPathing = new int[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        monsterPathing[i][j] = Integer.MAX_VALUE;
      }
    }


    ArrayDeque<Point> bfs = new ArrayDeque<>();

    for (Point monsterStart : monsters) {
      monsterPathing[monsterStart.r][monsterStart.c] = 0;
      bfs.add(monsterStart);
    }

    while (!bfs.isEmpty()) {
      Point current = bfs.poll();

      for (int i = 0; i < MOVES; i++) {
        int r = current.r + dr[i];
        int c = current.c + dc[i];

        if (r >= 0 && r < n && c >= 0 && c < m && labyrinth[r][c] != '#') {
          if (monsterPathing[current.r][current.c] + 1 < monsterPathing[r][c]) {
            monsterPathing[r][c] = monsterPathing[current.r][current.c] + 1;
            bfs.add(new Point(r, c));
          }
        }
      }
    }

    int[][] pathing = new int[n][m];
    Point end = null;
    int pathLength = -1;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        pathing[i][j] = Integer.MAX_VALUE;
      }
    }

    pathing[start.r][start.c] = 0;
    bfs.add(start);

    while (!bfs.isEmpty()) {
      Point current = bfs.poll();
      
      if ((current.r == 0 || current.r == n-1 || current.c == 0 || current.c == m-1)) {
        pathLength = pathing[current.r][current.c];
        end = current;
        break;
      }

      for (int i = 0; i < MOVES; i++) {
        int r = current.r + dr[i];
        int c = current.c + dc[i];

        if (r >= 0 && r < n && c >= 0 && c < m && labyrinth[r][c] != '#') {
          if (pathing[current.r][current.c] + 1 < monsterPathing[r][c] && pathing[current.r][current.c] + 1 < pathing[r][c]) {
            pathing[r][c] = pathing[current.r][current.c] + 1;
            bfs.add(new Point(r, c));
          }
        }
      }
    }

    if (end == null) {
      System.out.println("NO");
      return;
    }

    StringBuilder result = new StringBuilder();
    result.append("YES").append("\n");
    result.append(pathLength).append("\n");

    StringBuilder pathResult = new StringBuilder();
    for (int i = pathLength; i >= 0; i--) {
      for (int j = 0; j < MOVES; j++) {
        int r = end.r + dr[j];
        int c = end.c + dc[j];

        if (r >= 0 && r < n && c >= 0 && c < m) {
          if (pathing[r][c] == i) {
            pathResult.append(unwrapMoves[j]);
            end = new Point(r, c);
            break;
          }
        }
      }
    }

    result.append(pathResult.reverse());

    System.out.println(result);
  }

  private static class Point {
    int r, c;

    Point(int r, int c) {
      this.r = r;
      this.c = c;
    }
  }
}

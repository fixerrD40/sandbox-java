package cses.graph_algorithms;

import java.util.*;

public class Labyrinth {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine(); 

        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            board.add(sc.nextLine());
        }

        Cell start = null, end = null;
        for (int y = 0; y < n; y++) {
            String row = board.get(y);
            int idxA = row.indexOf('A');
            int idxB = row.indexOf('B');
            if (idxA != -1) start = new Cell(idxA, y);
            if (idxB != -1) end = new Cell(idxB, y);
        }

        if (start != null && end != null) {
            String path = bfs(board, n, m, start, end);
            if (path != null) {
                System.out.println("YES");
                System.out.println(path.length());
                System.out.println(path);
            } else {
                System.out.println("NO");
            }
        } else {
            System.out.println("NO");
        }
    }

    static String bfs(List<String> board, int n, int m, Cell start, Cell end) {
        boolean[][] visited = new boolean[n][m];
        Cell[][] parent = new Cell[n][m];
        char[][] move = new char[n][m];

        Deque<Cell> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start.y][start.x] = true;

        while (!queue.isEmpty()) {
            Cell current = queue.removeFirst();

            if (current.x == end.x && current.y == end.y) {
                StringBuilder path = new StringBuilder();
                Cell pos = end;
                while (pos.x != start.x || pos.y != start.y) {
                    char dir = move[pos.y][pos.x];
                    path.append(dir);
                    pos = parent[pos.y][pos.x];
                }
                return path.reverse().toString();
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x + Cell.dx[i];
                int ny = current.y + Cell.dy[i];
                
                if (ny >= 0 && ny < n && nx >= 0 && nx < m &&
                        board.get(ny).charAt(nx) != '#' &&
                        !visited[ny][nx]) {
                    visited[ny][nx] = true;
                    parent[ny][nx] = current;
                    move[ny][nx] = Cell.dirs[i];
                    queue.add(new Cell(nx, ny));
                }
            }
        }
        return null;
    }

    static class Cell {
        static final int[] dx = {0, 0, -1, 1};
        static final int[] dy = {-1, 1, 0, 0};
        static final char[] dirs = {'U', 'D', 'L', 'R'};
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Cell)) return false;
            Cell other = (Cell) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}

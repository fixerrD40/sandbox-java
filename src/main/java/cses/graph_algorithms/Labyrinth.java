package graph_algorithms;

import java.util.*;

public class Labyrinth {

    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine(); // consume newline

        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            board.add(sc.nextLine());
        }

        Cell start = null, end = null;

        for (int y = 0; y < n; y++) {
            String row = board.get(y);
            if (row.contains("A")) start = new Cell(row.indexOf('A'), y);
            if (row.contains("B")) end = new Cell(row.indexOf('B'), y);
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

            if (current.equals(end)) {
                StringBuilder path = new StringBuilder();
                Cell pos = end;
                while (!pos.equals(start)) {
                    char dir = move[pos.y][pos.x];
                    path.append(dir);
                    pos = parent[pos.y][pos.x];
                }
                return path.reverse().toString();
            }

            for (int i = 0; i < 4; i++) {
                Cell neighbor = current.move(i);
                if (neighbor.isInBounds(n, m) &&
                        board.get(neighbor.y).charAt(neighbor.x) != '#' &&
                        !visited[neighbor.y][neighbor.x]) {
                    visited[neighbor.y][neighbor.x] = true;
                    parent[neighbor.y][neighbor.x] = current;
                    move[neighbor.y][neighbor.x] = Cell.dirs[i];
                    queue.add(neighbor);
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

        Cell move(int dirIndex) {
            return new Cell(x + dx[dirIndex], y + dy[dirIndex]);
        }

        boolean isInBounds(int n, int m) {
            return y >= 0 && y < n && x >= 0 && x < m;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Cell other)) return false;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
package graph_algorithms;

import java.io.*;
import java.util.*;

public class CountingRooms {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(reader.readLine());
        int rows = Integer.parseInt(st.nextToken());
        int columns = Integer.parseInt(st.nextToken());

        List<String> blueprint = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            blueprint.add(reader.readLine());
        }

        int answer = solution(rows, columns, blueprint);

        writer.write(answer + "\n");
        writer.flush();
    }

    static int solution(int rows, int columns, List<String> blueprint) {
        int result = 0;
        boolean[][] visited = new boolean[rows][columns];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (!visited[y][x] && blueprint.get(y).charAt(x) == '.') {
                    result++;
                    dfsIterative(rows, columns, blueprint, x, y, visited);
                }
            }
        }

        return result;
    }

    static void dfsIterative(int rows, int columns, List<String> blueprint, int startX, int startY, boolean[][] visited) {
        Deque<Point> stack = new ArrayDeque<>();
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            int x = current.x;
            int y = current.y;

            if (x < 0 || x >= columns || y < 0 || y >= rows) continue;
            if (visited[y][x]) continue;
            if (blueprint.get(y).charAt(x) != '.') continue;

            visited[y][x] = true;

            stack.push(new Point(x + 1, y));
            stack.push(new Point(x - 1, y));
            stack.push(new Point(x, y + 1));
            stack.push(new Point(x, y - 1));
        }
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
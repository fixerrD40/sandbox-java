package cses.tree_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeDiameter {
    static ArrayList<Integer>[] adj;
    static int n;

    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        int endpoint = bfs(1)[0];
        int diameter = bfs(endpoint)[1];

        System.out.println(diameter);
    }

    // Returns {farthestNode, distance}
    static int[] bfs(int start) {
        boolean[] visited = new boolean[n + 1];
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start, 0});
        visited[start] = true;

        int farthest = start;
        int maxDist = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int u = cur[0];
            int dist = cur[1];

            if (dist > maxDist) {
                maxDist = dist;
                farthest = u;
            }

            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(new int[]{v, dist + 1});
                }
            }
        }

        return new int[]{farthest, maxDist};
    }
}

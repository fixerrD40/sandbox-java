package tree_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TreeMatching {
    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        int[][] dp = new int[n + 1][2];
        boolean[] visited = new boolean[n + 1];
        List<int[]> postOrder = new ArrayList<>();

        // Iterative DFS for post-order
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{1, 0});
        visited[1] = true;

        while (!stack.isEmpty()) {
            int[] curr = stack.pop();
            int u = curr[0], parent = curr[1];
            postOrder.add(curr);
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    stack.push(new int[]{v, u});
                }
            }
        }

        // Process in post-order (children first)
        Collections.reverse(postOrder);
        for (int[] p : postOrder) {
            int u = p[0], parent = p[1];
            int sumWithout = 0;
            for (int v : adj[u]) {
                if (v == parent) continue;
                sumWithout += Math.max(dp[v][0], dp[v][1]);
            }
            dp[u][0] = sumWithout;

            int bestWith = 0;
            for (int v : adj[u]) {
                if (v == parent) continue;
                int value = 1 + dp[v][0] + (sumWithout - Math.max(dp[v][0], dp[v][1]));
                bestWith = Math.max(bestWith, value);
            }
            dp[u][1] = bestWith;
        }

        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }
}
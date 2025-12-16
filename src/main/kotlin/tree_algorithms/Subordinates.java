package tree_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Subordinates {
    static List<Integer>[] tree;
    static int[] subCount;

    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        tree = new ArrayList[n + 1];
        subCount = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 2; i <= n; i++) {
            int manager = Integer.parseInt(st.nextToken());
            tree[manager].add(i);
        }

        iterativeDFS(n);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(subCount[i]).append(' ');
        }
        System.out.println(sb.toString().trim());
    }

    /**
     * This is kinda neat. Chatgpt knew the answer was correct so reasoned TLE was due to StackOverflow
     * Replaces recursive dfs with iterative, simplifying a call stack with the Deques
     *
     * @param n
     */
    private static void iterativeDFS(int n) {
        boolean[] visited = new boolean[n + 1];
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> postOrder = new ArrayDeque<>();

        stack.push(1);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            postOrder.push(node);
            for (int child : tree[node]) {
                if (!visited[child]) {
                    stack.push(child);
                    visited[child] = true;
                }
            }
        }

        while (!postOrder.isEmpty()) {
            int current = postOrder.pop();
            for (int child : tree[current]) {
                subCount[current] += 1 + subCount[child];
            }
        }
    }
}
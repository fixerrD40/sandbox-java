package graph_algorithms;

import java.io.*;
import java.util.*;

public class BuildingRoads {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            uf.union(u, v);
        }

        boolean[] seen = new boolean[n + 1];
        List<Integer> reps = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int root = uf.find(i);
            if (!seen[root]) {
                seen[root] = true;
                reps.add(root);
            }
        }

        System.out.println(reps.size() - 1);
        for (int i = 0; i < reps.size() - 1; i++) {
            System.out.println(reps.get(i) + " " + reps.get(i + 1));
        }
    }

    static class UnionFind {
        private final int[] parent;

        public UnionFind(int size) {
            parent = new int[size + 1];
            for (int i = 0; i <= size; i++) parent[i] = i;
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa != pb) parent[pa] = pb;
        }
    }
}
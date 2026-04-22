package cses.range_queries;

import java.io.*;
import java.util.*;

public class DynamicRangeMinimum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] numbers = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        int[] tree = new int[2 * n];
        Arrays.fill(tree, Integer.MAX_VALUE);

        // Build segment tree: copy leaves
        for (int i = 0; i < n; i++) tree[n + i] = numbers[i];

        // Build internal nodes
        for (int i = n - 1; i >= 1; i--) {
            tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
        }

        // Process queries
        for (int qi = 0; qi < q; qi++) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (op == 1) {
                // Update a-th element (1-based)
                int index = a - 1 + n;
                tree[index] = b;
                while (index > 1) {
                    index >>= 1;
                    tree[index] = Math.min(tree[index << 1], tree[index << 1 | 1]);
                }
            } else if (op == 2) {
                // Range minimum query [a, b] inclusive (1-based)
                int res = Integer.MAX_VALUE;
                int left = a - 1 + n;
                int right = b - 1 + n;
                while (left <= right) {
                    if ((left & 1) == 1) res = Math.min(res, tree[left++]);
                    if ((right & 1) == 0) res = Math.min(res, tree[right--]);
                    left >>= 1;
                    right >>= 1;
                }
                out.println(res);
            }
        }

        out.flush();
        out.close();
    }
}

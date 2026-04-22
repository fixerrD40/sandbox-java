package cses.range_queries;

import java.io.*;
import java.util.*;

public class DynamicRangeSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        long[] numbers = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            numbers[i] = Long.parseLong(st.nextToken());
        }

        FenwickTree fenwick = new FenwickTree(n);
        for (int i = 1; i <= n; i++) {
            fenwick.update(i, numbers[i]);
        }

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int idx = Integer.parseInt(st.nextToken());
                long newVal = Long.parseLong(st.nextToken());
                long delta = newVal - numbers[idx];
                numbers[idx] = newVal;
                fenwick.update(idx, delta);
            } else {
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                out.println(fenwick.rangeQuery(left, right));
            }
        }

        out.flush();
    }

    static class FenwickTree {
        long[] tree;

        FenwickTree(int size) {
            tree = new long[size + 1];
        }

        void update(int i, long delta) {
            while (i < tree.length) {
                tree[i] += delta;
                i += i & (-i);
            }
        }

        long query(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & (-i);
            }
            return sum;
        }

        long rangeQuery(int left, int right) {
            return query(right) - query(left - 1);
        }
    }
}

package range_queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class StaticRangeMinimum {
    static void main(String[] args) throws IOException {
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

        int[] froms = new int[q];
        int[] tos = new int[q];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            froms[i] = Integer.parseInt(st.nextToken()) - 1;
            tos[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        int[] log2 = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log2[i] = log2[i / 2] + 1;
        }

        int maxK = log2[n] + 1;
        int[][] minimums = new int[maxK][n];

        System.arraycopy(numbers, 0, minimums[0], 0, n);

        for (int k = 1; k < maxK; k++) {
            for (int i = 0; i + (1 << k) <= n; i++) {
                minimums[k][i] = Math.min(minimums[k - 1][i], minimums[k - 1][i + (1 << (k - 1))]);
            }
        }

        for (int i = 0; i < q; i++) {
            int l = froms[i];
            int r = tos[i];
            int len = r - l + 1;
            int k = log2[len];
            int answer = Math.min(minimums[k][l], minimums[k][r - (1 << k) + 1]);
            out.println(answer);
        }

        out.flush();
    }
}
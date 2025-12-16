package range_queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class StaticRangeSum {
    static void main(String[] args) throws IOException {
        // Fast input reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        int n = Integer.parseInt(tokenizer.nextToken());
        int q = Integer.parseInt(tokenizer.nextToken());

        int[] numbers = new int[n];
        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(tokenizer.nextToken());
        }

        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + numbers[i - 1];
        }

        // Prepare for fast output
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < q; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken()); // 1-based
            int b = Integer.parseInt(tokenizer.nextToken());

            long sum = prefix[b] - prefix[a - 1];
            output.append(sum).append('\n');
        }

        System.out.print(output);
    }
}
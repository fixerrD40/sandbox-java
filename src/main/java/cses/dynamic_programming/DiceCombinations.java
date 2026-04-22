package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DiceCombinations {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int answer = solution(n);

        System.out.println(answer);
    }

    private static int solution(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i - j >= 0) {
                    dp[i] = (dp[i] + dp[i - j]) % MOD;
                } else {
                    break;
                }
            }
        }

        return dp[n];
    }
}

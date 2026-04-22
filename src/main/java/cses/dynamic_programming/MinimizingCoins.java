package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MinimizingCoins {
    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] denoms = new int[n];
        for (int i = 0; i < n; i++) {
            denoms[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solution(x, denoms));
    }

    private static int solution(int x, int[] denoms) {
        int INF = Integer.MAX_VALUE / 2;
        int[] dp = new int[x + 1];
        for (int i = 1; i <= x; i++) dp[i] = INF;
        dp[0] = 0;

        for (int coin : denoms) {
            for (int i = coin; i <= x; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        return dp[x] >= INF ? -1 : dp[x];
    }
}

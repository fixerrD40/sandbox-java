package introductory_problems;

public class TwoKnights {
    static void main(String[] args) {
        int n = new java.util.Scanner(System.in).nextInt();

        for (long k = 1; k <= n; k++) {
            long totalPositions = k * k;
            long totalWays = totalPositions * (totalPositions - 1) / 2;
            long attackWays = 0;

            if (k > 2) {
                attackWays = 4 * (k - 1) * (k - 2);
            }

            System.out.println(totalWays - attackWays);
        }
    }
}
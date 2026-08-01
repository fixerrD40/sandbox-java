import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TwoSetsII {
  public static int MODULO = 1_000_000_007;

  /*
   * O(n^3)
   *
   * Another really tricky one.
   *
   * The dp was ways to create sums with an incrementing range of elements (1, 1..n).
   *
   * You had to identify the task centered around the triangular number of n.
   * You are finding all complementary sets of elements that split the triangle evenly (target).
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());
    int triangle = n*(n+1)/2;

    if (triangle % 2 != 0) {
      System.out.println(0);
      return;
    }

    int target = triangle/2;

    int[] dp = new int[target+1];
    dp[0] = 1;

    // By omitting n, we count only a complement for each pair of sets
    // Our Modulo operation required this lest we be off MODULO/2 by halving the final result.
    for (int i = 1; i < n; i++) {
      for (int j = target-i; j >= 0; j--) {
        dp[j + i] = (int) (((long) dp[j + i] + dp[j]) % MODULO);
      }
    }

    System.out.println(dp[target]);
  }
}

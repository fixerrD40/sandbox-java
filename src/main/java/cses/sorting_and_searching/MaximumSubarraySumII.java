import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MaximumSubarraySumII {

  /*
   * Naively I computed trailing subarray sums in a nested loop: O(n^2)
   * O(n-a+1) traverse subarrays of length a for max(subarrayA + maxTrailing)
   * O(b-a+1) traverse trailing subarrays for max(trailing)
   *
   * Precomputing [prefixes] exchanges cumulative values on a range for relative sums at certain points.
   * Using a TreeMap, we can store and remove these points (logn) and later retrieve a comparative minimum O(1)
   * for O(nlogn) here.
   *
   * Finally, it is possible to hack a Deque to achieve storage, removal, and retrieval in amortized O(1)
   * for O(n) theoretically.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int a = Integer.parseInt(in.nextToken());
    int b = Integer.parseInt(in.nextToken());

    long[] prefixes = new long[n + 1];
    
    in = new StringTokenizer(br.readLine());
    for (int i = 1; i <= n; i++) {
      int value = Integer.parseInt(in.nextToken());
      prefixes[i] = prefixes[i - 1] + value;
    }

    long result = Long.MIN_VALUE;
    TreeMap<Long, Integer> prefixesFreqs = new TreeMap<>();

    for (int i = a; i <= n; i++) {
      prefixesFreqs.compute(prefixes[i-a], (k,v) -> (v == null) ? 1 : ++v);

      if (i-b-1 >= 0) {
        prefixesFreqs.compute(prefixes[i-b-1], (k,v) -> (v == 1) ? null : --v);
      }

      long minPrefix = prefixesFreqs.firstKey();

      result = Math.max(result, prefixes[i] - minPrefix);
    }

    System.out.println(result);
  }
}

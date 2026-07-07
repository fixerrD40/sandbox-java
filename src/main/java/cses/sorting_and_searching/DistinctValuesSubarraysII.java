import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DistinctValuesSubarraysII {

  // O(n)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(in.nextToken());
    int k = Integer.parseInt(in.nextToken());

    in = new StringTokenizer(br.readLine());

    int[] values = new int[n];
    Map<Integer, Integer> termValuesFrequencies = new HashMap<>();
    int termDistinct = 0;
    int tail = 0;

    long result = 0;
    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      values[i] = value;

      if (termValuesFrequencies.compute(value, (key, v) -> (v == null) ? 1 : ++v) == 1) {
        termDistinct++;
      }

      while (termDistinct > k) {
        int priorValue = values[tail];
        if (termValuesFrequencies.compute(priorValue, (key, v) -> (v == 1) ? null : --v) == null) {
          termDistinct--;
        }
        tail++;
      }

      // increment by term size
      result += i-tail+1;
    }

    System.out.println(result);
  }
}

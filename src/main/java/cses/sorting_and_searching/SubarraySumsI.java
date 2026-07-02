import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SubarraySumsI {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());
    
    in = new StringTokenizer(br.readLine());

    int[] values = new int[n];
    int sum = 0;
    int lower = 0;
    
    int result = 0;
    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      values[i] = value;

      sum += value;
      if (sum > x) {
        while (sum > x && lower < n) {
          sum -= values[lower];
          lower++;
        }
      }

      if (sum == x) {
        result++;
      }

    }
    
    System.out.println(result);
  }
}

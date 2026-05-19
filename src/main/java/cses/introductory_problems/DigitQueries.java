package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitQueries {
  private static long MAX_QUERY = 1000000000000000000L;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int q = Integer.parseInt(br.readLine());

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < q; i++) {
      result.append(solve(Long.parseLong(br.readLine()))).append("\n");
    }

    System.out.println(result);
  }

  private static char solve(long query) {
    // My longs break at the top end
    if (query == MAX_QUERY) {
        return '2';
    }

    // 1->18
    int scale = 1;
    // 1->10^18
    long magnitude = 1;
    // 0->something < 10^18
    long acc = 0;

    while (true) {
      // 9->9*10^17
      long range = magnitude*10 - magnitude;

      if (query <= acc + range*scale)
        break;

      acc += range*scale;
      scale++;
      magnitude *= 10;
    }
    
    // 0->10^18-acc == 10^17 < something < 10^18
    // query is 1 based. Subtract 1 so it may serve as our index values
    long remainder = query-acc-1;
    // 1->10^17 < something < 10^18
    long numberIndex = remainder/scale;
    // 0->17
    int digitIndex = (int) (remainder%scale);

    String number = Long.toString(magnitude + numberIndex);

    return number.charAt(digitIndex);
  }
}

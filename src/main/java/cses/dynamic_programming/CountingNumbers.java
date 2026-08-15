package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CountingNumbers {
  private static final int DIGITS = 18;
  private static final int DIGIT_VALUES = 10;
  private static final long[] dp = new long[DIGITS * DIGIT_VALUES];

  // O(DIGITS*DIGIT_VALUES) -> O(log(b)), but vexingly O(1) due to fixed constraints
  static {
    // Base case: First digit is last digit (position = DIGITS-1),
    // all values are themself valid
    for (int i = 0; i < DIGIT_VALUES; i++) {
      dp[(DIGITS-1) * 10 + i] = 1;
    }

    // Build the rest of the dp working backward through positions
    for (int pos = DIGITS-2; pos >= 0; pos--) {
      for (int prevDigit = 0; prevDigit <= 9; prevDigit++) {
        long combinations = 0;
        for (int nextDigit = 0; nextDigit <= 9; nextDigit++) {
          if (nextDigit != prevDigit) {
            combinations += dp[(pos + 1) * 10 + nextDigit];
          }
        }
        dp[pos * 10 + prevDigit] = combinations;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer in = new StringTokenizer(br.readLine());
    long a = Long.parseLong(in.nextToken());
    String lower = String.valueOf(--a);
    String b = in.nextToken();

    System.out.println(query(b) - query(lower));
  }

  // O(2*DIGITS) -> O(log(value)) asymptotically, but O(1) due to fixed constraints
  private static long query(String value) {
    if ("-1".equals(value)) return 0;

    long count = 1;

    if ("0".equals(value)) return count;

    // Pad with spaces, then map spaces to 'X'
    // Example: 300 becomes "XXXXXXXXXXXXXXX300"
    String s = String.format("%" + DIGITS + "s", value).replace(' ', 'X');
    
    int previous = -1;
    boolean isBorderValid = true;

    // Exact Prefix State Machine Loop
    for (int i = 0; i < DIGITS; i++) {
      char ch = s.charAt(i);
      if (ch == 'X') continue;

      int current = ch - '0';

      // Prefix state machine handles subsequent bands of values only after first non-zero digit value
      // see 0-99 has a count 90, but 100-199 has 80
      int startDigit = (previous == -1) ? 1 : 0;

      for (int j = startDigit; j < current; j++) {
        if (j != previous) {
          count += dp[i * 10 + j];
        }
      }

      // Circuit Breaker: all subsequent values up to [value] will incorporate this invalid sequence
      // see 55000-55432 has a count 0
      if (previous != -1 && current == previous) {
        isBorderValid = false;
        break;
      }
      
      previous = current;
    }

    if (isBorderValid) {
      count++;
    }

    // Handle remaining bands of values strictly shorter than [value]
    for (int len = 1; len < value.length(); len++) {
      int startPos = DIGITS - len;
      for (int k = 1; k <= 9; k++) {
        count += dp[startPos * 10 + k];
      }
    }

    return count;
  }
}

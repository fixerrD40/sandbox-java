package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NumberSpiral {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      long y = Long.parseLong(st.nextToken());
      long x = Long.parseLong(st.nextToken());
      
      sb.append(calculate(y, x)).append("\n");
    }
    
    System.out.print(sb);
  }

  public static long calculate(long x, long y) {
    long result = 0;
    if (x > y) {
      if (x%2 == 0) {
        result = x*x - (y - 1);
      } else {
        result = (x-1)*(x-1) + y;
      }
    } else {
      if (y%2 == 1) {
        result = y*y - (x - 1);
      } else {
        result = (y-1)*(y-1) + x;
      }
    }

    return result;
  }

  public static long geminiCalculate(long x, long y) {
    long max = Math.max(y, x);
    long square = (max - 1) * (max - 1);
    long ans;

    if (max % 2 == 0) {
        if (max == y) ans = max * max - x + 1;
        else ans = square + y;
    } else {
        if (max == x) ans = max * max - y + 1;
        else ans = square + x;
    }
    return ans;
  }
}

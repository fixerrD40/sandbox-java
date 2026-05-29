package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SumOfTwoValues {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int x = Integer.parseInt(st.nextToken());

    st = new StringTokenizer(br.readLine());

    Map<Integer, Integer> seen = new HashMap<>();
    int a = -1;
    int b = -1;
    for (int i = 0; i < n; i++) {
      int cur = Integer.parseInt(st.nextToken());
      int target = x-cur;
      if (seen.containsKey(target)) {
        a = seen.get(target) + 1;
        b = i + 1;
        break;
      }
    
      seen.putIfAbsent(cur, i);
    }


    if (a == -1) {
      System.out.println("IMPOSSIBLE");
      return;
    }

    System.out.println(a + " " + b);
  }
}

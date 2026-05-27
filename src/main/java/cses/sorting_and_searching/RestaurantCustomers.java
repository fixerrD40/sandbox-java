package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class RestaurantCustomers {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    TreeMap<Integer, Boolean> events = new TreeMap<>();
    
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      events.put(Integer.parseInt(st.nextToken()), true);
      events.put(Integer.parseInt(st.nextToken()), false);
    }
    
    int cur = 0;
    int best = -1;
    for (Map.Entry<Integer, Boolean> entry : events.entrySet()) {
      if (entry.getValue()) {
        cur++;
        best = Math.max(best, cur);
      }
      else cur--;
    }

    System.out.println(best);
  }
}

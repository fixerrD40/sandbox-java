package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Towers {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    StringTokenizer in = new StringTokenizer(br.readLine());

    List<Integer> towers = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      int block = Integer.parseInt(in.nextToken());
      int j = upperBound(towers, block);

      if (j < towers.size()) {
        towers.set(j, block);
      } else {
        towers.add(block);
      }
    } 

    // My naive approach. I used a lot of gymnatics to get TreeMap's higherKey()
    // when I should've just implemented custom binary search
    //
    // TreeMap<Integer, Integer> towers = new TreeMap<>();
    // int result = 0;
    // for (int i = 0; i < n; i++) {
    //   int block = Integer.parseInt(in.nextToken());
    //   Integer stack = towers.higherKey(block);
    //
    //   if (stack != null) {
    //     towers.compute(stack, (k, v) -> (v == 1) ? null : v - 1);
    //   } else {
    //     result++;
    //   }
    //
    //   towers.compute(block, (k, v) -> (v == null) ? 1 : v + 1);
    // }
    
    System.out.println(towers.size());
  }

  // Implement upper bound with binary search
  private static int upperBound(List<Integer> list, int target) {
    int low = 0;
    int high = list.size();
    
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (list.get(mid) <= target) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return low;
  }
}

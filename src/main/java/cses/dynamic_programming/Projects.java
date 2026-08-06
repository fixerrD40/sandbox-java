package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Projects {

  // O(nlogn)
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    
    int[] sortedMovies = new int[n];
    int[] starts = new int[n];
    int[] ends = new int[n];
    int[] rewards = new int[n];
    
    for (int i = 0; i < n; i++) {
      StringTokenizer in = new StringTokenizer(br.readLine());
      sortedMovies[i] = i;
      starts[i] = Integer.parseInt(in.nextToken());
      ends[i] = Integer.parseInt(in.nextToken());
      rewards[i] = Integer.parseInt(in.nextToken());
    }

    dualArraySort(sortedMovies, ends, 0, n-1);

    TreeMap<Integer, Long> dp = new TreeMap<>();
    long result = 0;
    
    dp.put(0, 0L);

    for (int i = 0; i < n; i++) {
      int movie = sortedMovies[i];

      Map.Entry<Integer, Long> entry = dp.lowerEntry(starts[movie]);
      long profitBefore = (entry != null) ? entry.getValue() : 0;

      result = Math.max(result, profitBefore+rewards[movie]);
      
      dp.put(ends[movie], result);
    }

    System.out.println(result);
  }

  // O(nlogn)
  private static void dualArraySort(int[] indices, int[] heights, int low, int high) {
    if (low >= high) return;
    int pivot = heights[indices[low + (high - low) / 2]];
    int i = low, j = high;
    while (i <= j) {
      while (heights[indices[i]] < pivot) i++;
      while (heights[indices[j]] > pivot) j--;
      if (i <= j) {
        int temp = indices[i];
        indices[i] = indices[j];
        indices[j] = temp;
        i++; j--;
      }
    }
    dualArraySort(indices, heights, low, j);
    dualArraySort(indices, heights, i, high);
  }
}

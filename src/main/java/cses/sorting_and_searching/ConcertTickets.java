package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ConcertTickets {
  private static int[] parent;

  // Disjoint Set Union (DSU) Find with path compression
  static int find(int i) {
    if (parent[i] == i) return i;
    return parent[i] = find(parent[i]);
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
      
    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());

    st = new StringTokenizer(br.readLine());

    int[] tickets = new int[n];
    for (int i = 0; i < n; i++) {
      tickets[i] = Integer.parseInt(st.nextToken());
    }

    Arrays.sort(tickets);

    // init DSU
    parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    st = new StringTokenizer(br.readLine());

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < m; i++) {
      int maxBudget = Integer.parseInt(st.nextToken());

      int idx = search(tickets, maxBudget);

      int availableIdx = find(idx);

      if (availableIdx >= 0) {
        sb.append(tickets[availableIdx]);
        parent[availableIdx] = availableIdx - 1;
      } else {
        sb.append(availableIdx);
      }
            
      sb.append("\n");
    }

    System.out.println(sb);
  }

  private static int search(int[] input, int target) {
    int start = 0;
    int end = input.length-1;
 
    int result = -1;
    while (start <= end) {
      int mid = start + (end-start)/2;
 
      if (input[mid] <= target) {
        result = mid;
        start = mid+1;
      } else {
        end = mid-1;
      }
    }
 
    return result;
  }
}

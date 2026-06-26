package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FactoryMachines {
  static final int MAX_MACHINE = 1000000000;

  /**
   * This one was weird.
   * My naive approach was to iterate result up using steps hoping to stumble into some O(nlogn)
   *
   * The function workMachines is highly non-linear, making sub-problem 
   * of counting nested ranges no easier than original problem.
   *
   * The answer was just to poll and binary search the answer space (1,M) for a shocking O(nlogM)
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int t = Integer.parseInt(in.nextToken());

    int[] machines = new int[n];
    int minMachine = MAX_MACHINE;
    in = new StringTokenizer(br.readLine());

    for (int i = 0; i < n; i++) {
      int machine = Integer.parseInt(in.nextToken());
      if (machine < minMachine) minMachine = machine;
      machines[i] = machine;
    }

    // worst case, only our best machine makes all
    long high = (long) t*minMachine;
    long low = 0;

    // binary search M (logM)
    while (high >= low) {
      long mid = low + (high - low) / 2;
      
      if (workMachines(machines, mid) >= t) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }

    System.out.println(high+1);
  }

  // O(n)
  private static long workMachines(int[] machines, long time) {
    long result = 0;
    for (int i = machines.length-1; i >= 0; i--) {
      result += time / machines[i];
    }

    return result;
  }
}

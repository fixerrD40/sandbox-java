package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class JosephusProblemI {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    String result = solution(n);
    
    System.out.println(result);
  }

  // Naive
  public static String solution(int n) {
    boolean[] children = new boolean[n];
    boolean skip = true;
    int next = 0;
    int count = 0;

    StringBuilder result = new StringBuilder();
    while (true) {
      if (count == n) break;
      for (int i = next; i < n+next; i++) {
        int idx = i%n;

        if (!children[idx]) {
          if (skip) {
            skip = false;
            next = idx+1;
            break;
          }

          children[idx] = true;
          result.append(idx+1).append("\n");

          skip = true;
          idx = next+1;
          count++;
          break;
        }
      }
    }

    return result.toString();
  }
  
  // Gemini
  public static String solution2(int n) {
    Queue<Integer> queue = new ArrayDeque<>(n);

    for (int i = 1; i <= n; i++) {
      queue.add(i);
    }

    StringBuilder result = new StringBuilder();

    while (!queue.isEmpty()) {
      queue.add(queue.poll());
      
      result.append(queue.poll()).append("\n");
    }

    return result.toString();
  }
}

package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class AppleDivision {
  static int N;
  static Integer[] APPLES;
  static long TOTAL_SUM;
  static long GOAL;
  
  static long SOLUTION = Long.MAX_VALUE;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    N = Integer.parseInt(br.readLine());
    APPLES = Arrays.stream(br.readLine().split(" "))
      .map(Integer::valueOf)
      .toArray(Integer[]::new);

    APPLES = mergeSort(APPLES, Integer.class);

    TOTAL_SUM = Arrays.stream(APPLES)
      .mapToLong(Integer::intValue)
      .sum();

    GOAL = TOTAL_SUM/2;
    
    solve(1, 0L);
    System.out.println(SOLUTION);
  }

  private static void solve(int depth, long last) {
    if (depth > N) {
      long local = TOTAL_SUM-last-last;
      if (local < SOLUTION) SOLUTION = local;
      return;
    }

    solve(depth+1, last);

    long cur = last + APPLES[N-depth];
    if (cur > GOAL) return;
    solve(depth+1, cur);
  }

  public static <T extends Comparable<? super T>> T[] mergeSort(T[] input, Class<T> clazz) {
    int n = input.length;
    if (n <= 1) return input;
    
    int mid = n/2;
    T[] left = mergeSort(Arrays.copyOfRange(input, 0, mid), clazz);
    T[] right = mergeSort(Arrays.copyOfRange(input, mid, n), clazz);

    T[] result = createArray(n, clazz);
    int i = 0;
    int j = 0;
    int k = 0;
    while (i < mid && j < n - mid) {
      if (left[i].compareTo(right[j]) <= 0) {
        result[k] = left[i];
        i++;
      } else {
        result[k] = right[j];
        j++;
      }
      k++;
    }

    while (i < mid) {
        result[k] = left[i];
        i++;
        k++;
    }

    while (j < n-mid) {
        result[k] = right[j];
        j++;
        k++;
    }

    return result;
  }

  // Thanks Gemini
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<? super T>> T[] createArray(int size, Class<T> type) {
    return (T[]) Array.newInstance(type, size);
  }
}

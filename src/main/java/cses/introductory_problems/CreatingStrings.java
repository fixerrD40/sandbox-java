package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class CreatingStrings {
  static int count = 0;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String input = br.readLine();
    
    char[] sortedChars = mergeSort(input).toCharArray();
    
    StringBuilder result = new StringBuilder();
    solve(new char[sortedChars.length], 0, sortedChars, new boolean[sortedChars.length], result);
    
    System.out.println(count);
    System.out.println(result);
  }

  public static String mergeSort(String input) {
    if (input.length() == 1) {
      return input;
    }

    return new String(innerSort(input.toCharArray()));
  }

  private static char[] innerSort(char[] input) {
    int n = input.length;
    if (n == 1) return input;

    int mid = n/2;
    char[] left = innerSort(Arrays.copyOfRange(input, 0, mid));
    char[] right = innerSort(Arrays.copyOfRange(input, mid, n));
    
    char[] result = new char[n];
    int i = 0;
    int j = 0;
    int k = 0;
    while (i < mid && j < n - mid) {
      if (left[i] >= right[j]) {
        result[k] = right[j];
        j++;
      } else {
        result[k] = left[i];
        i++;
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

  private static void solve(char[] path, int depth, char[] chars, boolean[] used, StringBuilder results) {
    if (depth == chars.length) {
      results.append(path).append('\n');
      count++;
      return;
    }

    char last = '\0'; 
    for (int i = 0; i < chars.length; i++) {
      if (used[i] || chars[i] == last) continue;

      last = chars[i];
      used[i] = true;
      path[depth] = chars[i];
        
      solve(path, depth + 1, chars, used, results);
        
      used[i] = false;
    }
  }
}

package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringReorder {
  private static int SIZE_CHARSET = 26;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String input = br.readLine();
    int n = input.length();
    int[] frequencies = new int[SIZE_CHARSET];
    
    for (char c: input.toCharArray()) {
      frequencies[c - 'A']++;
    }

    StringBuilder result = new StringBuilder();

    // lexicographical handling
    int superceding = -1;
    int last = -1;
    for (int i = n; i > 0; i--) {
      int selection = last;
      for (int j = 0; j < SIZE_CHARSET; j++) {
        int freq = frequencies[j];

        if ((selection == last) && freq > 0) {
          if (last != j) {
            selection = j;
          }
        }

        if (freq*2 > i+1) {
          System.out.println("-1");
          return;
        }

        if (freq*2 == i+1) {
          superceding = j;
        }
      }

      if (superceding != -1) break;

      result.append((char) (selection + 'A'));
      frequencies[selection]--;
      last = selection;
    }

    // Superceding no repeats handling
    int remaining = n-result.length();
    for (int i = 0; i < remaining; i++) {
      if (i%2 == 1) {
        for (int j = 0; j < SIZE_CHARSET; j++) {
          if (frequencies[j] > 0 && j != superceding) {
            result.append((char) (j + 'A'));
            frequencies[j]--;
            break;
          }
        }
      } else {
        result.append((char) (superceding + 'A'));
      }
    }

    System.out.println(result);
  }
}

package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PalindromeReorder {
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String input = br.readLine();

    boolean[] trackOrphans = new boolean[27];
    StringBuilder result = new StringBuilder();
    
    for (char c : input.toCharArray()) {
      int position = toPosition(c);

      if (position == -1) {
        System.out.println("NO SOLUTION");
        return;
      }

      boolean isOrphan = (trackOrphans[position] = !trackOrphans[position]);
      if (!isOrphan) {
        result.append(c);
      }
    }
    
    int middleIndex = -1;
    int oddCount = 0;
    for (int i = 1; i <= 26; i++) {
      if (trackOrphans[i]) {
        oddCount++;
        middleIndex = i;
      }
    }

    if (oddCount > 1) {
      System.out.println("NO SOLUTION");
    } else {
      String right = new StringBuilder(result).reverse().toString();
      result.append((middleIndex == -1) ? "" : String.valueOf((char)(middleIndex + 'A' - 1)));
      result.append(right);
        
      System.out.println(result);
    }
  }

  public static int toPosition(char c) {
    char lower = Character.toLowerCase(c);
    if (lower >= 'a' && lower <= 'z') {
      return lower - 'a' + 1;
    }
    return -1;
  }
}

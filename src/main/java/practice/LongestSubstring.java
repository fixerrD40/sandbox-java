package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LongestSubstring {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String s = br.readLine();

    boolean[] used = new boolean[26];

    int start = -1;
    int longest = 0;
    int cur = 0;

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);

      if (used[c - 'a']) {
        if (cur > longest) {
          longest = cur;
          start = i;
        }

        Arrays.fill(used, false);
        cur = 0;
      }

      used[c - 'a'] = true;
      cur++;
    }

    System.out.println(s.substring(start, start+longest));
  }
}

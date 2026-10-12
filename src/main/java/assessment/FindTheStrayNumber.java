package assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class FindTheStrayNumber {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    // establish true value
    int a = Integer.parseInt(in.nextToken());
    int b = Integer.parseInt(in.nextToken());
    int c = Integer.parseInt(in.nextToken());
    
    if (a != b) {
      if (a != c) {
        System.out.println(a);
        return;
      }
      System.out.println(b);
      return;
    } else if (a != c) {
      System.out.println(c);
      return;
    }

    // a, b, and c are all the true value, keep searching
    while (in.hasMoreTokens()) {
      int next = Integer.parseInt(in.nextToken());
      if (next != a) {
        System.out.println(next);
        return;
      }
    }
  }
}

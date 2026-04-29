package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class TrailingZeros {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    long n = Long.parseLong(br.readLine());

    long solution = legendresFormula(5, n);

    System.out.println(solution);
  }
  
  private static long legendresFormula(long p, long n) {
    long i = n;
    long result = 0;

    while (i >= p) {
      i /= p;
      result += i;
    }

    return result;
  }
}

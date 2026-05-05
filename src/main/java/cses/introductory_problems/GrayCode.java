package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class GrayCode {
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    int[] grayCode = new int[(int) Math.pow(2, n)];
    grayCode[0] = 0;
    grayCode[1] = 1;

    for (int i = 1; i < n; i++) {
      int term = (int) Math.pow(2, i);
      for (int j = term; j < 2*term; j++) {
        // reverse result heretofore
        grayCode[j] = term | grayCode[2 * term - j - 1];
      }
    }

    StringBuilder result = new StringBuilder();

    for (int val : grayCode) {
    String binary = Integer.toBinaryString(val);
    
    // Pad with leading zeros to reach length 'n'
    while (binary.length() < n) {
        binary = "0" + binary;
    }
        
    result.append(binary).append("\n");
    }

    System.out.print(result.toString());
  }
}

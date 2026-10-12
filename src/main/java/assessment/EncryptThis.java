package assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class EncryptThis {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String input = br.readLine();

    StringBuilder result = new StringBuilder();

    // Encryption applied to tokens
    String[] tokens = input.split("\\s+");

    for (int i = 0; i < tokens.length; i++) {
      // Space delimit enciphered tokens
      if (i != 0) {
        result.append(" ");
      }

      String token = tokens[i];
      // In Java the int cast on char implicitly utf-8 which is Ascii-aligned
      int ascii = token.charAt(0);

      result.append(ascii);

      // Swap last char...
      result.append(token.charAt(token.length()-1));

      // All chars between the same
      result.append(token.substring(2, token.length()-1));

      // ...with second char
      result.append(token.charAt(1));
    }

    System.out.print(result);
  }
}

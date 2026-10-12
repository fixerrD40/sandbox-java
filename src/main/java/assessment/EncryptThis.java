package assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class EncryptThis {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String input = br.readLine();

    StringBuilder result = new StringBuilder();

    String[] tokens = input.split("\\s+");

    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i];
      int ascii = token.charAt(0);

      result.append(ascii);

      result.append(token.charAt(token.length()-1));

      result.append(token.substring(2, token.length()-1));
      result.append(token.charAt(1));
      result.append(" ");
    }

    System.out.print(result);
  }
}

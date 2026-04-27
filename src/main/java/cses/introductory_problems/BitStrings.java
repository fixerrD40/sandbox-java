import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BitStrings {
  public static final long MODULO = 1_000_000_007L;
  
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    long n = Long.parseLong(br.readLine());

    long result = moduloPow(2, n);

    System.out.println(result);
  }
  
  private static long moduloPow(long base, long pow) {
    long result = 1;
    for (int i = 0; i < pow; i++) {
      result = (result * base) % MODULO;
    }

    return result;
  }
}

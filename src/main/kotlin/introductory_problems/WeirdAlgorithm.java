package introductory_problems;

import java.util.Scanner;

public class WeirdAlgorithm {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        StringBuilder result = new StringBuilder();
        result.append(n);

        while (n != 1) {
            if (n % 2 == 0)
                n /= 2;
            else
                n = 3 * n + 1;
            result.append(" ").append(n);
        }

        System.out.println(result);
    }
}
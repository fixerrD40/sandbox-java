package cses.dynamic_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RemovingDigits {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        List<Integer> answer = solution(n);
        System.out.println(answer.size());
    }

    public static List<Integer> solution(int n) {
        int curN = n;
        List<Integer> result = new ArrayList<>();

        while (curN > 0) {
            List<Integer> digits = getDigits(10, curN);
            int maxDigit = Collections.max(digits);
            curN -= maxDigit;
            result.add(curN);
        }

        return result;
    }

    public static List<Integer> getDigits(int base, int number) {
        List<Integer> digits = new ArrayList<>();
        int prevPlaceValue = 1;
        int placeValue = base;
        int prev = 0;

        while (true) {
            int mod = number % placeValue;
            int digit = (mod - prev) / prevPlaceValue;
            digits.add(digit);
            if (placeValue > number) break;
            prev = mod;
            prevPlaceValue = placeValue;
            placeValue *= base;
        }

        Collections.reverse(digits);
        return digits;
    }
}

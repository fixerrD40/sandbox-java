package introductory_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MissingNumber {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        List<Integer> numbers = new ArrayList<>();

        String[] input = scanner.nextLine().split(" ");
        for (String s : input) {
            numbers.add(Integer.parseInt(s));
        }

        int answer = solution(n, numbers);
        System.out.println(answer);
    }

    public static int solution(int n, List<Integer> numbers) {
        int expected;

        if (n % 2 == 1) {
            int median = (int) Math.ceil(n / 2.0);
            expected = median * (n + 1) - median;
        } else {
            expected = (n / 2) * (n + 1);
        }

        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }

        return expected - sum;
    }
}
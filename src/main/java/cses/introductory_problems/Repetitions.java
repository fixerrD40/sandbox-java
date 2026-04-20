package introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Repetitions {
    static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String dnaStrand = br.readLine().trim();

        int answer = solution(dnaStrand);
        System.out.println(answer);
    }

    private static int solution(String dnaStrand) {
        char previous = 0;
        int current = 0;
        int max = 0;

        for (char block : dnaStrand.toCharArray()) {
            if (previous != 0 && previous == block) {
                current++;
            } else {
                current = 1;
            }

            if (current > max) {
                max = current;
            }

            previous = block;
        }

        return max;
    }
}
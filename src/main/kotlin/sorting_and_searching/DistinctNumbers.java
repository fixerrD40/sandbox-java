package sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class DistinctNumbers {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int count = 0;
        Set<Integer> distinctNumbers = new HashSet<>();

        reader.readLine();
        String[] parts = reader.readLine().split(" ");
        for (String part : parts) {
            int num = Integer.parseInt(part);
            if (distinctNumbers.add(num)) {
                count++;
            }
        }

        System.out.println(count);
    }
}

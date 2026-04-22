package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class FerrisWheel {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        List<Integer> weights = new ArrayList<>(n);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            weights.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(weights); // uses TimSort in Java 8

        int i = 0;
        int j = n - 1;
        int result = 0;

        while (i <= j) {
            if (weights.get(i) + weights.get(j) <= x) {
                i++;
            }
            j--;
            result++;
        }

        System.out.println(result);
    }
}

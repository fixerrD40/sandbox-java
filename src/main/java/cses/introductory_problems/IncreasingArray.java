package cses.introductory_problems;

import java.io.*;
import java.util.StringTokenizer;

public class IncreasingArray {
    static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        long previous = Long.parseLong(st.nextToken());
        long result = 0;

        for (int i = 1; i < n; i++) {
            long number = Long.parseLong(st.nextToken());
            if (number < previous) {
                result += previous - number;
            } else {
                previous = number;
            }
        }

        out.println(result);
        out.flush();
    }
}

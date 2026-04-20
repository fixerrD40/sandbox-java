package sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.TreeMap;

public class NestedRangesCount {
    static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        Range[] ranges = new Range[n];

        for (int i = 0; i < n; i++) {
            String[] parts = reader.readLine().split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            ranges[i] = new Range(a, b, i);
        }

        Arrays.sort(ranges, (r1, r2) -> {
            if (r1.start != r2.start)
                return Integer.compare(r1.start, r2.start);
            return -Integer.compare(r1.end, r2.end);
        });

        int[] contained = new int[n];
        TreeMap<Integer, Integer> seenEnds = new TreeMap<>();

        for (int i = n - 1; i >= 0; i--) {
            Range r = ranges[i];
            // Count how many ends are <= r.end
            contained[r.index] = seenEnds.tailMap(r.end).size();

            seenEnds.put(r.end, seenEnds.getOrDefault(r.end, 0) + 1);
        }

        Arrays.sort(ranges, (r1, r2) -> {
            if (r1.start != r2.start)
                return -Integer.compare(r1.start, r2.start);
            return Integer.compare(r1.end, r2.end);
        });

        int[] contains = new int[n];
        seenEnds.clear();

        for (int i = n - 1; i >= 0; i--) {
            Range r = ranges[i];
            // Count how many ends are >= r.end
            contains[r.index] = seenEnds.headMap(r.end, true).size();

            seenEnds.put(r.end, seenEnds.getOrDefault(r.end, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int x : contains)
            sb.append(x).append(" ");
        sb.append("\n");
        for (int x : contained)
            sb.append(x).append(" ");
        System.out.println(sb.toString().trim());
    }

    static class Range {
        int start, end, index;

        Range(int start, int end, int index) {
            this.start = start;
            this.end = end;
            this.index = index;
        }
    }
}
package cses.introductory_problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TwoSets {

    static class PartitionResult {
        List<Integer> a;
        List<Integer> b;
        PartitionResult(List<Integer> a, List<Integer> b) {
            this.a = a;
            this.b = b;
        }
    }
  
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null) return;
        int n = Integer.parseInt(line);

        int scenario = n % 4;
        PartitionResult result = null;
        StringBuilder sb = new StringBuilder();

        switch(scenario) {
            case 0:
                sb.append("YES\n");
                result = partitionEvenSeries(n);
                break;
            case 3:
                sb.append("YES\n");
                result = partitionEvenSeries(n - 1);
                result.b.add(n);
                break;
            default:
                sb.append("NO");
        }

        if (result != null) {
            appendSet(sb, result.a);
            appendSet(sb, result.b);
        }
        System.out.print(sb);
    }

    private static PartitionResult partitionEvenSeries(int n) {
        int elements = n / 2;
        List<Integer> a = new ArrayList<>(elements + 2);
        List<Integer> b = new ArrayList<>(elements);

        for (int i = 0; i < elements; i++) {
            if (i % 2 == 0) {
                a.add(i + 1);
                a.add(n - i);
            } else {
                b.add(i + 1);
                b.add(n - i);
            }
        }
        return new PartitionResult(a, b);
    }

    private static void appendSet(StringBuilder sb, List<Integer> list) {
        sb.append(list.size()).append("\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(" ");
        }
        sb.append("\n");
    }
}

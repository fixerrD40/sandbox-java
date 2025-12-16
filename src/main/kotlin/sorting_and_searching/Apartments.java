package sorting_and_searching;

import java.io.*;
import java.util.*;

public class Apartments {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        List<Integer> desiredApartments = new ArrayList<>(n);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            desiredApartments.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(desiredApartments);

        List<Integer> apartments = new ArrayList<>(m);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            apartments.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(apartments);

        int answer = solutionTwoPointers(n, m, k, desiredApartments, apartments);

        System.out.println(answer);
    }

    private static int solutionTwoPointers(int numTenants, int numApartments, int tolerance,
                                           List<Integer> desiredSizes, List<Integer> apartmentSizes) {
        int result = 0;
        int i = 0;
        int k = 0;

        while (i < numTenants && k < numApartments) {
            int desired = desiredSizes.get(i);
            int apartment = apartmentSizes.get(k);

            if (apartment >= desired - tolerance && apartment <= desired + tolerance) {
                result++;
                i++;
                k++;
            } else if (apartment > desired - tolerance) {
                i++;
            } else {
                k++;
            }
        }

        return result;
    }
}
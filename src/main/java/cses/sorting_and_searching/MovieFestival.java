package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MovieFestival {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());

    int[][] movies = new int[n][2];

    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());

      movies[i][0] = Integer.parseInt(st.nextToken());
      movies[i][1] = Integer.parseInt(st.nextToken());
    }

    Arrays.sort(movies, (a, b) -> Integer.compare(a[1], b[1]));

    int time = -1;
    int result = 0;
    for (int i = 0; i < n; i++) {
      if (movies[i][0] >= time) {
        time = movies[i][1];
        result++;
      }
    }

    System.out.println(result);
  }
}

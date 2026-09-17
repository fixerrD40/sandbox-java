package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ShortestRoutesII {
  // MAX_FLIGHT_WEIGHT (1E9) * MAX_CITIES (500)  -> 5E12
  private static long MAX_WEIGHT = 5_000_000_000_000L;

  /*
   * Time Complexity: O(n^3+m+q)
   * Space Complexity: O((n+1)^2)
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());
    int q = Integer.parseInt(in.nextToken());

    long[][] distances = new long[n+1][n+1];

    for (int i = 0; i <= n; i++) {
      Arrays.fill(distances[i], MAX_WEIGHT);
      distances[i][i] = 0;
    }
    
    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());
      
      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      int c = Integer.parseInt(in.nextToken());

      distances[a][b] = Math.min(distances[a][b], c);
      distances[b][a] = Math.min(distances[b][a], c);
    }
    
    // Floyd-Warshall
    for (int j = 1; j <= n; j++) {
      for (int i = 1; i <= n; i++) {
        for (int k = 1; k <= n; k++) {
          if (distances[i][j] != MAX_WEIGHT && distances[j][k] != MAX_WEIGHT) {
            distances[i][k] = Math.min(distances[i][k], distances[i][j] + distances[j][k]);
          }
        }
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < q; i++) {
      in = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());

      if (distances[a][b] == MAX_WEIGHT) {
        result.append("-1");
      } else {
        result.append(distances[a][b]);
      }
      result.append("\n");
    }

    System.out.println(result);
  }
}

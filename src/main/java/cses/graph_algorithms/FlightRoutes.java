package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class FlightRoutes {
  private static int SYRJALA = 1;

  /*
   * Time Complexity: O((n+mk)log(n+mk)) -> O(mklogmk)
   * Space Complexity: O(n+mk)
   *
   * Dijkstra's algorithm but we stripped off the [distances] optimization
   * and we circuit break if we ever make more visits to a node than paths we were seeking
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());
    int k = Integer.parseInt(in.nextToken());

    City[] cities = new City[n + 1];

    for (int i = 1; i <= n; i++) {
      cities[i] = new City();
    }
    
    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      long c = Long.parseLong(in.nextToken());

      cities[a].flights.add(new Flight(b, c));
    }

    int[] visitCount = new int[n + 1];
    long[] routes = new long[k];
    int routeCount = 0;

    PriorityQueue<Flight> queue = new PriorityQueue<>((a, b) -> Long.compare(a.weight, b.weight));
    queue.add(new Flight(SYRJALA, 0));

    while (!queue.isEmpty() && visitCount[n] < k) {
      Flight current = queue.poll();

      if (visitCount[current.destination] >= k) {
        continue;
      }

      visitCount[current.destination]++;

      if (current.destination == n) {
        routes[routeCount++] = current.weight;
      }

      for (Flight edge : cities[current.destination].flights) {
        if (visitCount[edge.destination] < k) {
          queue.add(new Flight(edge.destination, current.weight + edge.weight));
        }
      }
    }

    StringBuilder result = new StringBuilder();

    if (routeCount > 0) {
      result.append(routes[0]);
      for (int i = 1; i < routeCount; i++) {
        result.append(" ").append(routes[i]);
      }
    }

    System.out.println(result);
  }

  private static class Flight {
    int destination;
    long weight;

    public Flight(int destination, long weight) {
      this.destination = destination;
      this.weight = weight;
    }
  }

  private static class City {
    ArrayList<Flight> flights = new ArrayList<>();
  }
}

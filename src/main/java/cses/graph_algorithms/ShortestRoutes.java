package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestRoutes {
  private static int SYRJALA = 1;
  // MAX_FLIGHT_WEIGHT (1E9) * MAX_CITIES (1E5)  -> 1E14
  private static long MAX_WEIGHT = 100_000_000_000_000L;

  /*
   * Time Complexity: O((n+m)logn) -> O(mlogn)
   * Space Complexity: O(n+m)
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    City[] cities = new City[n+1];

    for (int i = 1; i <= n; i++) {
      cities[i] = new City();
    }
    
    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());
      
      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      int c = Integer.parseInt(in.nextToken());

      cities[a].flights.add(new Connection(b, c));
    }

    // Dijkstra's algorithm
    long[] distances = new long[n+1];
    PriorityQueue<Connection> queue = new PriorityQueue<>((a, b) -> Long.compare(a.weight, b.weight));

    Arrays.fill(distances, MAX_WEIGHT);

    distances[SYRJALA] = 0;
    queue.add(new Connection(SYRJALA, 0));

    while (!queue.isEmpty()) {
      Connection current = queue.poll();

      if (current.weight > distances[current.destination]) {
        continue;
      }

      for (Connection flight : cities[current.destination].flights) {
        long distance = flight.weight + current.weight;

        if (distance < distances[flight.destination]) {
          distances[flight.destination] = distance;
          queue.add(new Connection(flight.destination, distance));
        }
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 1; i <= n; i++) {
      result.append(distances[i]).append(" ");
    }

    System.out.println(result);
  }

  private static class Connection {
    int destination;
    long weight;

    public Connection(int target, long weight) {
      this.destination = target;
      this.weight = weight;
    }
  }

  private static class City {
    ArrayList<Connection> flights;

    public City() {
      this.flights = new ArrayList<>();
    }
  }
}

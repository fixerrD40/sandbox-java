package cses.graph_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class HighScore {
  private static int START = 1;
  private static long MIN_SCORE = -2_500_000_000_000L;

  /*
   * Time Complexity: O(nm)
   * Space Complexity: O(n+m)
   *
   * Enter Bellman-Ford processing of flat edge list
   * The bfs had a hard time distinguishing between a loop and distinct path involving a node
   * whereas Bellman-Ford is predicated on exploring no deeper than n
   *
   * Most of the algorithm is determining whether highScores[n] is the Bellman-Ford result,
   * or an arbitrarily large number
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    Edge[] tunnels = new Edge[m];

    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());
      int x = Integer.parseInt(in.nextToken());

      tunnels[i] = new Edge(a, b, x);
    }

    long[] highScores = new long[n+1];
    Arrays.fill(highScores, MIN_SCORE);

    highScores[START] = 0;

    // Bellman-Ford -> bfs with a n-1 ttl
    for (int iter = 1; iter < n; iter++) {
      for (Edge tunnel : tunnels) {
        if (highScores[tunnel.source] == MIN_SCORE) continue;

        long highScore = highScores[tunnel.source] + tunnel.weight;
        if (highScore > highScores[tunnel.destination]) {
          highScores[tunnel.destination] = highScore;
        }
      }
    }

    // Branch once more, if a score still improves it will continue to do so forever
    // We will perform true multi-source bfs from these rooms next
    ArrayDeque<Integer> bfs = new ArrayDeque<>();
    boolean[] infiniteScore = new boolean[n + 1];

    for (Edge tunnel : tunnels) {
      if (highScores[tunnel.source] == MIN_SCORE) continue;

      long newScore = highScores[tunnel.source] + tunnel.weight;
      if (newScore > highScores[tunnel.destination]) {
        if (!infiniteScore[tunnel.destination]) {
          infiniteScore[tunnel.destination] = true;
          bfs.add(tunnel.destination);
        }
      }
    }

    // Construct and use adjacency list for the bfs
    boolean resultInfinite = false;
    Room[] rooms = new Room[n+1];

    for (int i = 1; i <= n; i++) {
      rooms[i] = new Room();
    }

    for (Edge tunnel : tunnels) {
      // We aren't even concerned with particular score anymore, just whether infinite or not
      rooms[tunnel.source].tunnels.add(new Tunnel(tunnel.destination));
    }

    while (!bfs.isEmpty()) {
      int current = bfs.poll();

      if (current == n) {
        resultInfinite = true;
        break;
      }

      for (Tunnel tunnel : rooms[current].tunnels) {
        if (!infiniteScore[tunnel.destination]) {
          infiniteScore[tunnel.destination] = true;
          bfs.add(tunnel.destination);
        }
      }
    }

    if (resultInfinite) {
      System.out.println("-1");
    } else {
      System.out.println(highScores[n]);
    }
  }

  private static class Room {
    ArrayList<Tunnel> tunnels;

    public Room() {
      this.tunnels = new ArrayList<>();
    }
  }

  private static class Tunnel {
    int destination;

    public Tunnel(int destination) {
      this.destination = destination;
    }
  }

  private static class Edge {
    int source, destination;
    long weight;

    public Edge(int source, int destination, long weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
    }
  }
}

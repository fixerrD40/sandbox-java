package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Playlist {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());

    Map<Integer, Integer> positions = new HashMap<>();

    StringTokenizer in = new StringTokenizer(br.readLine());
    int start = 0;
    int best = -1;
    for (int i = 0; i < n; i++) {
      int song = Integer.parseInt(in.nextToken());

      if (positions.getOrDefault(song, -1) != -1 && positions.get(song) >= start) {
        int set = i-start;
        if (set > best) best = set;
        start = positions.get(song) + 1;
      }

      positions.put(song, i);
    }

    int lastSet = n-start;
    if (lastSet > best) best = lastSet;
    
    System.out.println(best);
  }
}

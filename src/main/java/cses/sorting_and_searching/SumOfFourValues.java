package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SumOfFourValues {

  /*
   * O(n^2)
   *
   * (k -> i,j -> n) -> n O(n(n/2 + n/2))
   *
   * i and j serve to explore every pair value forward
   * k and i serve to tabulate every novel pair value backward
   * i and j are complements of n
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());
    Element[] elements = new Element[n];

    in = new StringTokenizer(br.readLine());
    for (int i = 0; i < n; i++) {
      int value = Integer.parseInt(in.nextToken());
      elements[i] = new Element(i+1, value);
    }
    
    Map<Integer, Pair> seenPairs = new HashMap<>();
    for (int i = 0; i < n-1; i++) {

      for (int j = i+1; j < n; j++) {
        int target = x - (elements[i].value + elements[j].value);

        if (seenPairs.containsKey(target)) {
          Pair seenPair = seenPairs.get(target);
          System.out.println(seenPair.id1 + " " + seenPair.id2 + " " + elements[i].id + " " + elements[j].id);
          return;
        }
      }

      for (int k = 0; k < i; k++) {
        int sum = elements[k].value + elements[i].value;
        if (!seenPairs.containsKey(sum)) {
          seenPairs.put(sum, new Pair(elements[k].id, elements[i].id));
        }
      }
    }

    System.out.println("IMPOSSIBLE");
  }

  private static class Element {
    public final int id, value;

    public Element(int id, int value) {
      this.id = id;
      this.value = value;
    }
  }

  private static class Pair {
    public final int id1, id2;

    public Pair(int id1, int id2) {
      this.id1 = id1;
      this.id2 = id2;
    }
  }
}

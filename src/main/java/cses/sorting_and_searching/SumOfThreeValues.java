package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SumOfThreeValues {

  /*
   * O(n^2/2) -> O(n^2)
   * sort O(nlogn)
   * (i, j -> <- k) -> n O(n*n/2)
   *
   * I got neither of these on my own, and rather ashamed of this one.
   * This is SumOfTwoValues O(n/2) loop,
   * but with an outer loop that fixes a prepending third value
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
    
    Arrays.sort(elements, Comparator.comparing(element -> element.value));
    
    for (int i = 0; i < n-2; i++) {
      int target = x - elements[i].value;
      int j = i+1;
      int k = n-1;

      while (j < k) {
        if (elements[j].value + elements[k].value == target) {
          System.out.println(elements[i].id + " " + elements[j].id + " " + elements[k].id);
          return;
        } else if (elements[j].value + elements[k].value > target) {
          k--;
        } else {
          j++;
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
}

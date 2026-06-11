package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CollectingNumbersII {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int m = Integer.parseInt(in.nextToken());

    in = new StringTokenizer(br.readLine());

    int[] positions = new int[n+1];
    int[] numbers = new int[n+1];
    int rounds = 0;
    for (int i = 1; i <= n; i++) {
      int number = Integer.parseInt(in.nextToken());
      positions[number] = i;
      numbers[i] = number;

      if (number == n) {
        rounds++;
      } else if (positions[number+1] != 0) {
        rounds++;
      }
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < m; i++) {
      in = new StringTokenizer(br.readLine());

      int a = Integer.parseInt(in.nextToken());
      int b = Integer.parseInt(in.nextToken());

      // Make [a] specifically the lower position
      if (a > b) {
        int tmp = a;
        a = b;
        b = tmp;
      }

      int firstNumber = numbers[a];
      // [firstNumber] advances above _or swaps with_ preceding number
      if (firstNumber > 1) {
        if (positions[firstNumber-1] > a) {
          if (positions[firstNumber-1] < b) {
            rounds--;
          } else if (positions[firstNumber-1] == b) {
            rounds--;
          }
        }
      }

      // [firstNumber] advances above _or swaps with_ subsequent number
      if (firstNumber < n) {
        if (positions[firstNumber+1] > a) {
          if (positions[firstNumber+1] < b) {
            rounds++;
          } else if (positions[firstNumber+1] == b) {
            rounds++;
          }
        }
      }

      int secondNumber = numbers[b];
      // [secondNumber] retreats below preceding number
      if (secondNumber > 1) {
        if (positions[secondNumber-1] < b && positions[secondNumber-1] > a) {
          rounds++;
        }
      }

      // [secondNumber] retreats below subsequent number
      if (secondNumber < n) {
        if (positions[secondNumber+1] < b && positions[secondNumber+1] > a) {
          rounds--;
        }
      }

      // persist swap
      positions[firstNumber] = b;
      positions[secondNumber] = a;
      numbers[a] = secondNumber;
      numbers[b] = firstNumber;

      result.append(rounds).append("\n");
    }

    System.out.println(result);
  }
}

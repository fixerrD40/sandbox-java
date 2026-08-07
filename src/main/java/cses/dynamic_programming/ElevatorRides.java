package cses.dynamic_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ElevatorRides {

  /*
   * O(n*2^n)
   *
   * This one got me too. 
   *
   * I had a hard time conceptualizing it, but our dp int[2^n]
   * allows us to examine incorporating the next passenger (Integer.highestOneBit(i))
   * into all configurations of prior passengers and particularly their last car.
   *
   * In this way our dp is able to progress towards the optimal configuration.
   */
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    StringTokenizer in = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(in.nextToken());
    int x = Integer.parseInt(in.nextToken());

    in = new StringTokenizer(br.readLine());
    int[] people = new int[n];

    for (int i = 0; i < n; i++) {
      people[i] = Integer.parseInt(in.nextToken());
    }

    int[] ridesDp = new int[1<<n];
    ridesDp[0] = 1;
    int[] remainingDp = new int[1<<n];
    remainingDp[0] = x;

    for (int i = 1; i < 1<<n; i++) {
      ridesDp[i] = n;
      remainingDp[i] = 0;

      for (int j = 0; j < n; j++) {
        if ((i & (1<<j)) != 0) {
          int prior = i ^ (1<<j);
          int weight = people[j];

          int candidateRides = ridesDp[prior];
          int candidateRemaining = remainingDp[prior];

          if (weight <= candidateRemaining) {
            candidateRemaining -= weight;
          } else {
            candidateRides += 1;
            candidateRemaining = x-weight;
          }

          if (candidateRides < ridesDp[i]) {
            ridesDp[i] = candidateRides;
            remainingDp[i] = candidateRemaining;
          } else if (candidateRides == ridesDp[i] && candidateRemaining > remainingDp[i]) {
            remainingDp[i] = candidateRemaining;
          }
        }
      }
    }

    System.out.println(ridesDp[(1<<n)-1]);
  }
}

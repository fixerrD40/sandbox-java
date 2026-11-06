package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CountZeroRequestServers {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());

    int l = Integer.parseInt(br.readLine());
    int[][] logs = new int[l][2];

    for (int i = 0; i < l; i++) {
      StringTokenizer logIn = new StringTokenizer(br.readLine());
      logs[i][0] = Integer.parseInt(logIn.nextToken());
      logs[i][1] = Integer.parseInt(logIn.nextToken());
    }
    
    Arrays.sort(logs, (a,b) -> {
      if (a[1] == b[1]) {
        return Integer.compare(a[0], b[0]);
      }

      return Integer.compare(a[1], b[1]);
    });

    int window = Integer.parseInt(br.readLine());

    int q = Integer.parseInt(br.readLine());
    int[][] queries = new int[q][2];

    for (int i = 0; i < q; i++) {
      queries[i][0] = Integer.parseInt(br.readLine());
      queries[i][1] = i;
    }

    Arrays.sort(queries, (a,b) -> Integer.compare(a[0], b[0]));

    int[] serversLogs = new int[n+1];
    int activeServers = 0;

    int left = 0;
    int right = 0;

    int[] results = new int[q];

    for (int i = 0; i < q; i++) {
      int end = queries[i][0];
      int start = end-window;

      while (right < l && logs[right][1] <= end) {
        int serverId = logs[right][0];
        if (serversLogs[serverId] == 0) {
          activeServers++;
        }
        serversLogs[serverId]++;
        right++;
      }

      while (left < l && logs[left][1] < start) {
        int serverId = logs[left][0];
        if (serversLogs[serverId] == 1) {
          activeServers--;
        }
        serversLogs[serverId]--;
        left++;
      }

      results[queries[i][1]] = n-activeServers;
    }

    StringBuilder result = new StringBuilder();

    for (int i = 0; i < q-1; i++) {
      result.append(results[i]).append(" ");
    }

    result.append(results[q-1]);

    System.out.println(result);
  }
}

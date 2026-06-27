package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class TasksAndDeadlines {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    Task[] tasks = new Task[n];

    StringTokenizer in;
    for (int i = 0; i < n; i++) {
      in = new StringTokenizer(br.readLine());

      int duration = Integer.parseInt(in.nextToken());
      int deadline = Integer.parseInt(in.nextToken());
      
      tasks[i] = new Task(i, duration, deadline);
    }

    Arrays.sort(tasks, Comparator.comparing(task -> task.duration));
    
    long time = 0;
    long result = 0;

    for (Task task : tasks) {
      time += task.duration;
      result += task.deadline-time;
    }

    System.out.println(result);
  }

  private static class Task {
    private final int id, duration, deadline;

    public Task(int id, int duration, int deadline) {
      this.id = id;
      this.duration = duration;
      this.deadline = deadline;
    }
  }
}

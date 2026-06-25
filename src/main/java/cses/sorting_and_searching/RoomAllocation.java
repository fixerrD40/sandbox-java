package cses.sorting_and_searching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class RoomAllocation {

  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();

    Event[] events = new Event[2 * n];
    int j = 0;

    for (int i = 0; i < n; i++) {
      int arrival = scanner.nextInt();
      int departure = scanner.nextInt();

      events[j++] = new Event(i, arrival, Event.EventType.ARRIVAL);
      events[j++] = new Event(i, departure, Event.EventType.DEPARTURE);
    }

    Arrays.sort(events);

    Pool rooms = new Pool(n);
    int[] assignedRooms = new int[n];

    for (Event event : events) {
      if (event.type == Event.EventType.ARRIVAL) {
        int room = rooms.acquire();
        assignedRooms[event.guest] = room;
      } else {
        int room = assignedRooms[event.guest];
        rooms.release(room);
      }
    }
    
    StringBuilder result = new StringBuilder();
    result.append(rooms.size).append("\n");
    for (int room : assignedRooms) {
      result.append(room).append(" ");
    }

    System.out.println(result);
  }

  private static class Event implements Comparable<Event> {
    final int guest, day;
    final EventType type;

    enum EventType {
      ARRIVAL,
      DEPARTURE;
    }

    public Event(int guest, int day, EventType type) {
      this.guest = guest;
      this.day = day;
      this.type = type;
    }

    @Override
    public int compareTo(Event other) {
      if (this.day != other.day) {
        return Integer.compare(this.day, other.day);
      }

      // Pretty neat, the enum is ordinal on its definition
      return this.type.compareTo(other.type);
    }
  }

  private static class Pool {
    private int size;
    private final int[] available;
    private int top = -1;

    public Pool(int maxCapacity) {
      this.size = 0;
      this.available = new int[maxCapacity + 1];
    }

    public int acquire() {
      if (top >= 0) {
        return available[top--];
      }
      size++;
      return size;
    }

    public void release(int id) {
      available[++top] = id;
    }
  }

  // High-performance tokenless scanner class
  private static class FastScanner {
    private final InputStream stream;
    private final byte[] buffer = new byte[1 << 16];
    private int head = 0, tail = 0;

    public FastScanner(InputStream stream) { 
      this.stream = stream; 
    }

    private int read() throws IOException {
      if (head >= tail) {
        head = 0;
        tail = stream.read(buffer, 0, buffer.length);
        if (tail <= 0) return -1;
      }
      return buffer[head++];
    }

    public int nextInt() throws IOException {
      int c = read();
      while (c <= 32) {
        if (c == -1) return -1;
        c = read();
      }
      int res = 0;
      while (c > 32) {
        if (c < '0' || c > '9') throw new RuntimeException();
        res = res * 10 + c - '0';
        c = read();
      }
      return res;
    }
  }
}

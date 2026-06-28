package cses.sorting_and_searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ReadingBooks {
  private static int n;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    n = Integer.parseInt(br.readLine());
    int[] books = new int[n];
    
    StringTokenizer in = new StringTokenizer(br.readLine());
    for (int i = 0; i < n; i++) {
      books[i] = Integer.parseInt(in.nextToken());
    }

    long result = simulationSolution(n, books);

    System.out.println(result);
  }

  // Or, if you spent longer analyzing the problem
  // O(n)
  private static long greedySolution(int n, int[] books) {
    long sumAll = 0;
    long maxBook = 0;

    for (int i = 0; i < n; i++) {
      sumAll += books[i];
      if (books[i] > maxBook) {
        maxBook = books[i];
      }
    }

    return Math.max(sumAll, 2 * maxBook);
  }

  // O(nlogn)
  private static long simulationSolution(int n, int[] books) {
    // O(nlogn)
    Arrays.sort(books);
    
    Library library = new Library(books);
    Reader kotivalo = new Reader(books.length, 0);
    Reader justiina = new Reader(books.length, n-1);

    kotivaloSelect(library, kotivalo);
    justiinaSelect(library, justiina);

    long result = 0;
    // O(n)
    while (!kotivalo.isDone || !justiina.isDone) {
      if (!kotivalo.isDone && !justiina.isDone) {
        if (kotivalo.isReading() && justiina.isReading()) {
          if (kotivalo.remainingTime == justiina.remainingTime) {
            int time = kotivaloFinish(library, kotivalo);
            justiinaFinish(library, justiina);
            result += time;
            kotivaloSelect(library, kotivalo);
            justiinaSelect(library, justiina);
          } else if (kotivalo.remainingTime > justiina.remainingTime) {
            int time = justiinaFinish(library, justiina);
            kotivalo.read(time);
            result += time;
            justiinaSelect(library, justiina);
          } else {
            int time = kotivaloFinish(library, kotivalo);
            justiina.read(time);
            result += time;
            kotivaloSelect(library, kotivalo);
          }
        // neither are done, but only one was reading.
        } else if (kotivalo.isReading()) {
          int time = kotivaloFinish(library, kotivalo);
          result += time;
          kotivaloSelect(library, kotivalo);
          justiinaSelect(library, justiina);
        } else if (justiina.isReading()) {
          int time = justiinaFinish(library, justiina);
          result += time;
          kotivaloSelect(library, kotivalo);
          justiinaSelect(library, justiina);
        }
      } else {
        while (!kotivalo.isDone) {
          int time = kotivaloFinish(library, kotivalo);
          result += time;
          kotivaloSelect(library, kotivalo);
        }

        while (!justiina.isDone) {
          int time = justiinaFinish(library, justiina);
          result += time;
          justiinaSelect(library, justiina);
        }
      }
    }

    return result;
  }

  // Notably uses Reader.marker to keep this o(1)
  private static void kotivaloSelect(Library library, Reader kotivalo) {
    boolean contested = false;
    for (int book = kotivalo.marker; book < n; book++) {
      if (!kotivalo.readBooks[book]) {
        int length = library.checkout(book);
        if (length != -1) {
          if (!contested) kotivalo.marker = book;
          kotivalo.start(book, length);
          return;
        }
        contested = true;
      }
    }
    
    if (!contested) {
      kotivalo.isDone = true;
    }
  }

  private static int kotivaloFinish(Library library, Reader kotivalo) {
    if (!kotivalo.isReading()) return 0;

    int time = kotivalo.remainingTime;
    int book = kotivalo.currentBook;

    kotivalo.read(time);
    library.release(book);

    return time;
  }

  private static void justiinaSelect(Library library, Reader justiina) {
    boolean contested = false;
    for (int book = justiina.marker; book >= 0; book--) {
      if (!justiina.readBooks[book]) {
        int length = library.checkout(book);
        if (length != -1) {
          if (!contested) justiina.marker = book;
          justiina.start(book, length);
          return;
        }
        contested = true;
      }
    }

    if (!contested) {
      justiina.isDone = true;
    }
  }

  private static int justiinaFinish(Library library, Reader justiina) {
    if (!justiina.isReading()) return 0;

    int time = justiina.remainingTime;
    int book = justiina.currentBook;

    justiina.read(time);
    library.release(book);

    return time;
  }

  // Decapsulated, it's my class
  private static class Reader {
    public int remainingTime = -1;
    private boolean[] readBooks;

    public int currentBook = -1;
    public boolean isDone = false;
    public int marker;

    public Reader(int books, int marker) {
      this.readBooks = new boolean[books];
      this.marker = marker;
    }

    public void start(int book, int length) {
      currentBook = book;
      remainingTime = length;
    }

    public void read(int time) {
      remainingTime -= time;
      if (remainingTime < 1) {
        readBooks[currentBook] = true;
        currentBook = -1;
      }
    }

    public boolean isReading() {
      return remainingTime > 0;
    }
  }

  private static class Library {
    public int[] books;
    private boolean[] checkedBooks;

    public Library(int[] books) {
      this.books = books;
      this.checkedBooks = new boolean[books.length];
    }

    public int checkout(int book) {
      if (!checkedBooks[book]) {
        checkedBooks[book] = true;
        return books[book];
      }
      return -1;
    }

    public void release(int book) {
      checkedBooks[book] = false;
    }
  }
}

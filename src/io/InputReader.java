package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class InputReader {

  private BufferedReader br;
  private StringTokenizer in;

  public InputReader(String fileName) {
    try {
      br = new BufferedReader(new FileReader(fileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public InputReader(InputStream inputStream) {
    br = new BufferedReader(new InputStreamReader(inputStream));
  }

  private boolean hasMoreTokens() {
    while (in == null || !in.hasMoreTokens()) {
      String s = nextLine();
      if (s == null) {
        return false;
      }
      in = new StringTokenizer(s);
    }
    return true;
  }

  public String nextString() {
    return hasMoreTokens() ? in.nextToken() : null;
  }

  public String nextLine() {
    try {
      in = null;
      return br.readLine();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public int nextInt() {
    return Integer.parseInt(nextString());
  }

  public long nextLong() {
    return Long.parseLong(nextString());
  }

  public double nextDouble() {
    return Double.parseDouble(nextString());
  }

  public int[] nextIntArray(int n) {
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = nextInt();
    }
    return a;
  }

  public int[][] nextIntIntArray(int n, int m) {
    int[][] a = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        a[i][j] = nextInt();
      }
    }
    return a;
  }

  public long[] nextLongArray(int n) {
    long[] a = new long[n];
    for (int i = 0; i < n; i++) {
      a[i] = nextLong();
    }
    return a;
  }

  public double[] nextDoubleArray(int n) {
    double[] a = new double[n];
    for (int i = 0; i < n; i++) {
      a[i] = nextDouble();
    }
    return a;
  }

  public String[] nextStringArray(int n) {
    String[] a = new String[n];
    for (int i = 0; i < n; i++) {
      a[i] = nextString();
    }
    return a;
  }

  public String[] nextLineArray(int n) {
    String[] a = new String[n];
    for (int i = 0; i < n; i++) {
      a[i] = nextLine();
    }
    return a;
  }

  @Deprecated
  public String next() {
    return nextString();
  }
}

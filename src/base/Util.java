package base;

public class Util {
  public static double min(double... a) {
    double res = a[0];
    for (double i : a) {
      res = Math.min(res, i);
    }
    return res;
  }

  public static double max(double... a) {
    double res = a[0];
    for (double i : a) {
      res = Math.max(res, i);
    }
    return res;
  }

  public static int min(int... a) {
    int res = a[0];
    for (int i : a) {
      res = Math.min(res, i);
    }
    return res;
  }

  public static int max(int... a) {
    int res = a[0];
    for (int i : a) {
      res = Math.max(res, i);
    }
    return res;
  }

  public static void main(String[] args) {
    int[] a = new int[1];
    test(a);
    System.out.println(a[0]);
  }

  public static void test(int[] a) {
    a = null;
  }
}

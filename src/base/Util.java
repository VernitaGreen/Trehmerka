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
}

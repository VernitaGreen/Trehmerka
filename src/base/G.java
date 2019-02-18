package base;

public class G {
  static final double EPS = 1e-6;


  public static boolean doubleEquality(double a, double b) {
    return Math.abs(a - b) < EPS;
  }

  public static double dotProduct(Vector a, Vector b) {
    return a.x * b.x + a.y * b.y + a.z * b.z;
  }

  public static Vector add(Vector a, Vector b) {
    return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
  }

  public static Vector scale(Vector v, double alpha) {
    return new Vector(v.x * alpha, v.y * alpha, v.z * alpha);
  }

  public static Vector subtract(Vector a, Vector b) {
    return add(a, scale(b, -1));
  }

  public static double norm(Vector v) {
    return Math.sqrt(dotProduct(v, v));
  }

  public static Vector normalize(Vector v) {
    if (doubleEquality(norm(v), 0)) {
      throw new IllegalArgumentException("Trying to normalize a zero vector");
    }

    return scale(v, 1.0 / norm(v));
  }

  public static Vector intersect(Plane plane, Ray ray) {
    double num = dotProduct(plane.n, subtract(plane.r0, ray.p0));
    double den = dotProduct(plane.n, ray.e);

    if (doubleEquality(num, 0)) { // in the same plane
      return ray.p0;
    }

    if (doubleEquality(den, 0)) { // in a parallel plane
      return null;
    }

    double t = num / den;
    if (t >= 0) { // intersects in the positive direction
      return add(ray.p0, scale(ray.e, t));
    } else { // negative direction
      return null;
    }
  }
}

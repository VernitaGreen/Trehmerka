package base;

import java.util.Objects;

public class Vector {
  static final double EPS = 1e-6;

  public static boolean doubleEquality(double a, double b) {
    return Math.abs(a - b) < EPS;
  }

  public static final Vector zero = new Vector(0, 0, 0);

  public final double x;
  public final double y;
  public final double z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector add(Vector o) {
    return new Vector(x + o.x, y + o.y, z + o.z);
  }

  public Vector subtract(Vector o) {
    return add(o.scale(-1));
  }

  public double norm() {
    return Math.sqrt(dotProduct(this));
  }

  public Vector scale(double alpha) {
    return new Vector(x * alpha, y * alpha, z * alpha);
  }

  public Vector normalize() {
    if (doubleEquality(norm(), 0)) {
      throw new IllegalArgumentException("Trying to normalize a zero vector");
    }

    return scale(1.0 / norm());
  }

  public double dotProduct(Vector o) {
    return x * o.x + y * o.y + z * o.z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector vector = (Vector) o;
    return doubleEquality(x, vector.x)
        && doubleEquality(y, vector.y)
        && doubleEquality(z, vector.z);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return String.format("{%+.2f,%+.2f,%+.2f}", x, y, z);
  }
}

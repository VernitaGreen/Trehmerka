package base;

import java.util.Objects;

public class Plane {
  public final Vector r0;
  public final Vector n;

  public Plane(Vector r0, Vector n) {
    this.r0 = r0;
    this.n = n;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Plane plane = (Plane) o;
    return Objects.equals(r0, plane.r0) &&
        Objects.equals(n, plane.n);
  }

  @Override
  public int hashCode() {
    return Objects.hash(r0, n);
  }

  @Override
  public String toString() {
    return "Plane{" +
        "p0=" + r0 +
        ", n=" + n +
        '}';
  }
}

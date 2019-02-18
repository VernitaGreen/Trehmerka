package base;

import java.util.Objects;

public class Ray {
  public final Vector p0;
  public final Vector e;

  public Ray(Vector r0, Vector e) {
    this.p0 = r0;
    this.e = e.normalize();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ray ray = (Ray) o;
    return Objects.equals(p0, ray.p0) &&
        Objects.equals(e, ray.e);
  }

  @Override
  public int hashCode() {
    return Objects.hash(p0, e);
  }

  @Override
  public String toString() {
    return "Ray{" +
        "p0=" + p0 +
        ", e=" + e +
        '}';
  }

  public Vector intersect(Plane plane) {
    double a = plane.n.dotProduct(plane.r0.subtract(p0));
    double b = plane.n.dotProduct(e);

    if (Vector.doubleEquality(a, 0)) { // in the same plane
      return p0;
    }

    if (Vector.doubleEquality(b, 0)) { // in a parallel plane
      return null;
    }

    double t = a / b;
    if (t >= 0) { // intersects in the positive direction
      return p0.add(e.scale(t));
    } else { // negative direction
      return null;
    }
  }
}

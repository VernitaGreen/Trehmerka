package base;

import java.util.Objects;

public class Ray {
  public final Vector p0;
  public final Vector e;

  public Ray(Vector r0, Vector e) {
    this.p0 = r0;
    this.e = G.normalize(e);
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
}

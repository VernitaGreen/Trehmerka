package base;

import java.util.Objects;

public class Point3D {
  public static final Point3D origin = new Point3D(0, 0, 0);

  public final Vector rv;

  public Point3D(double x, double y, double z) {
    this.rv = new Vector(x, y, z);
  }

  public Point3D(Vector rv) {
    if (rv == null) {
      throw new IllegalArgumentException();
    }
    this.rv = rv;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point3D point3D = (Point3D) o;
    return rv.equals(point3D.rv);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rv);
  }
}

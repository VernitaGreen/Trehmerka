package base;

import java.util.Objects;

public class Point3D {
  static final double EPS = 1e-6;

  public final double x;
  public final double y;
  public final double z;

  public Point3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Point3D add(Point3D o) {
    return new Point3D(x + o.x, y + o.y, z + o.z);
  }

  public double norm() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Point3D scale(double alpha) {
    return new Point3D(x * alpha, y * alpha, z * alpha);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point3D point2DInt = (Point3D) o;
    return Math.abs(x - point2DInt.x) < EPS
        && Math.abs(y - point2DInt.y) < EPS
        && Math.abs(z - point2DInt.z) < EPS;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return "Point3D{" +
        "x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }
}

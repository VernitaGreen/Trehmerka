package base;

import java.util.Objects;

public class Point2D {
  public final double x;
  public final double y;

  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point2D add(Point2D o) {
    return new Point2D(x + o.x, y + o.y);
  }

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  public Point2D scale(double alpha) {
    return new Point2D(x * alpha, y * alpha);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point2D point2DInt = (Point2D) o;
    return x == point2DInt.x &&
        y == point2DInt.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Point2D{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
